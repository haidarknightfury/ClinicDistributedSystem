package ViewModel;

import java.util.ArrayList;
import java.util.Optional;

import Communications.PatientClient;
import GUI.AppointmentListForm;
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

import clinicinterface.Appointment;
import clinicinterface.AppointmentPatient;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;


public class AppointmentListFormImpl {
	
	private Scene scene;
	private static AppointmentListForm appointmentListForm;
	private PatientClient patientClient;
	
	
	public AppointmentListFormImpl(){
		   appointmentListForm=new AppointmentListForm();
		   scene=appointmentListForm.CreateAppointmetListScene();
		   try{
			   patientClient=new PatientClient();
			   InitTable();
			   appointmentListForm.getAppointmentPatientTable().setItems(appointmentListForm.getAppointments());
			   UpdateAppointmentsList();
		   }
		   
		   catch(Exception e){
			   System.out.println(e.toString());
		   }
		   
		   
		   appointmentListForm.getBtnExit().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new DoctorMenuFormImpl().CreateScene());
				
			}
		});
	}
	
	public Scene createScene(){
		return scene;
	}
	
	
	public void UpdateAppointmentsList(){
		try{
			appointmentListForm.getAppointments().clear();
			
			//String UserID=patientClient.getID(Main.Main.ID, 1);//O for patient
			//ArrayList<Appointment> appList=patientClient.getAppointmentList(UserID);
			
			String UserID=Main.Main.getClinicClient().getClinicInterface().GetID(Main.Main.ID, 1);
			ArrayList<Appointment>appList=Main.Main.getClinicClient().getClinicInterface().GetAppointments(UserID);
			for(Appointment App:appList){
				
			   /*String patientStr=patientClient.ObtainPatientUsingID(App.getPatientId());
				String [] data= patientStr.split(",");
				String OtherNames=data[1];
				String Surname=data[0];*/
				
				System.out.println("APPOINTMENT OBTAINED "+ App.toString());
				Patient patient=Main.Main.getClinicClient().getClinicInterface().getPatientUsingID(App.getPatientId());
				appointmentListForm.getAppointments().add(new AppointmentPatient(App.getDocId(), App.getDocId(), App.getAppointmentDate()
						  , App.getAppointmentStartTime(), App.getAppointmentEndTime(), App.getHistory(),
						    App.getPrescription(), patient.getFirstName(), patient.getOtherNames()));
			}

		}
		catch(Exception E){
			System.out.println("EXCEPTION"+E.toString());
		}		
	}

	
	public void InitTable(){
		
		appointmentListForm.getDateCol().setCellValueFactory(new PropertyValueFactory<AppointmentPatient,String>("appointmentDate"));
		appointmentListForm.getETimeCol().setCellValueFactory(new PropertyValueFactory<AppointmentPatient,String>("appointmentEndTime"));
		appointmentListForm.getSTimeCol().setCellValueFactory(new PropertyValueFactory<AppointmentPatient,String>("appointmentStartTime"));
		appointmentListForm.getFnameCol().setCellValueFactory(new PropertyValueFactory<AppointmentPatient,String>("patientOtherNames"));
		appointmentListForm.getSnameCol().setCellValueFactory(new PropertyValueFactory<AppointmentPatient,String>("patientSurname"));
		
		Callback<TableColumn<AppointmentPatient, String>, TableCell<AppointmentPatient, String>> cellFactory = 
				new Callback<TableColumn<AppointmentPatient, String>, TableCell<AppointmentPatient, String>>()
		{
			@Override
			public TableCell call( final TableColumn<AppointmentPatient, String> param ){
				final TableCell<Doctor, String> cell = new TableCell<Doctor, String>()
				{

					private Button btn = new Button( "Delete" );
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
									String DID=(appointmentListForm.getAppointments().get(getIndex()).getPatientId());
									System.out.println("Patient Selected "+ DID);
									Doctor SelectedDoc;
									try {
										//SelectedDoc = patientClient.getDoctorById(DID);
										//Main.Main.NavigateTo(new ObtainAppointmentFormImpl(SelectedDoc).CreateScene());
										
									} catch (Exception e) {
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
		appointmentListForm.getActionCol().setCellFactory(cellFactory);
		
	}
	
	
	
	
	
}
