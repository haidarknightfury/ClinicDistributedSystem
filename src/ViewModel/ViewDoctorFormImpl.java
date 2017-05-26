package ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import Communications.PatientClient;
import GUI.ViewDoctorForm;
import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.util.Callback;

public class ViewDoctorFormImpl {
	private Scene scene;
	private ViewDoctorForm viewDoctorForm;
	private PatientClient patientClient;
	
	public ViewDoctorFormImpl(){
		this.viewDoctorForm=new ViewDoctorForm();
		this.scene=viewDoctorForm.CreateViewDoctorScene();
		try {
			patientClient=new PatientClient();
			InitTable();
			viewDoctorForm.getDoctorTable().setItems(viewDoctorForm.getDoctorList());
			UpdateDoctorList();
			//TODO: RETRIEVE DOCTOR LIST AND POPULATE 

		} catch (Exception e) {
			e.printStackTrace();
		}

		viewDoctorForm.getBtnExit().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new PatientMenuFormImpl().CreateScene());
			}
		});
	}


	public void UpdateDoctorList(){
		try{
			viewDoctorForm.getDoctorList().clear();
			//ArrayList<Doctor> docList= patientClient.getAllDoctorsList();
			ArrayList<Doctor> docList= Main.Main.getClinicClient().getClinicInterface().GetAllDoctors();
			for(Doctor D:docList){
				System.out.println("IMPL : "+D.toString());
			}
			viewDoctorForm.getDoctorList().addAll(docList);

		}
		catch(Exception e){
			System.out.println(e.toString());
		}

	}

	public Scene createScene(){
		return scene;
	}


	public void InitTable(){

		viewDoctorForm.getDocID().setCellValueFactory(new PropertyValueFactory<Doctor,String>("ID"));
		viewDoctorForm.getDocContact().setCellValueFactory(new PropertyValueFactory<Doctor,String>("ContactNumber"));
		viewDoctorForm.getDocOtherNames().setCellValueFactory(new PropertyValueFactory<Doctor,String>("OtherNames"));
		viewDoctorForm.getDocSurname().setCellValueFactory(new PropertyValueFactory<Doctor,String>("FirstName"));
		viewDoctorForm.getDocSpecialization().setCellValueFactory(new PropertyValueFactory<Doctor,String>("Specialization"));

		Callback<TableColumn<Doctor, String>, TableCell<Doctor, String>> cellFactory = 
				new Callback<TableColumn<Doctor, String>, TableCell<Doctor, String>>()
		{
			@Override
			public TableCell call( final TableColumn<Doctor, String> param ){
				final TableCell<Doctor, String> cell = new TableCell<Doctor, String>()
				{

					private Button btn = new Button( "Contact" );
					@Override
					public void updateItem( String item, boolean empty )
					{
						super.updateItem( item, empty );
						if ( empty )
						{
							setGraphic( null );
							setText( null );
						}
						else
						{
							btn.setOnAction( ( ActionEvent event ) ->
							{                

								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.initModality(Modality.APPLICATION_MODAL);
								alert.setTitle("Contact");
								alert.setContentText("Contact Doctor");
								Optional<ButtonType> result = alert.showAndWait();

								if(result.get() == ButtonType.OK)
								{	 
									String DID=(viewDoctorForm.getDoctorList().get(getIndex()).getID());
									System.out.println("DOCTOR CONTACTED "+ DID);
									Doctor SelectedDoc;
									try {
										//SelectedDoc = patientClient.getDoctorById(DID);
										SelectedDoc=Main.Main.getClinicClient().getClinicInterface().getDoctorDetails(DID);
										System.out.println("SELECTED  "+SelectedDoc.toString());
										Main.Main.NavigateTo(new ObtainAppointmentFormImpl(SelectedDoc).CreateScene());
									
									} catch (IOException e) {
										e.printStackTrace();
									}
									
								}

							} );
							setGraphic( btn );
							setText( null );
						}
					}
				};
				cell.setAlignment(Pos.TOP_CENTER); 
				return cell;
			}
		};
		viewDoctorForm.getActionCol().setCellFactory(cellFactory);
	}

}
