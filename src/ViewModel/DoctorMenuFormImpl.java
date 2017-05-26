package ViewModel;

import java.io.IOException;

import Communications.PatientClient;
import GUI.DoctorMenuForm;
import GUI.PatientViewerForm;
import GUI.ProfileForm;
import clinicinterface.Doctor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class DoctorMenuFormImpl {
	private Scene scene;
	private DoctorMenuForm doctorMenuForm;

	public DoctorMenuFormImpl(){
		this.doctorMenuForm=new DoctorMenuForm();
		this.scene=doctorMenuForm.createScene();
		doctorMenuForm.getViewAppointments().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new AppointmentListFormImpl().createScene());
			}
		});

		doctorMenuForm.getExit().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new LoginFormImpl().createScene());
			}
		});

		doctorMenuForm.getMyProfileBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new ProfileFormImpl().createScene());
			}
		});
		
		doctorMenuForm.getViewPatientBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.Main.NavigateTo(new PatientViewerFormImpl().CreateScene());
			}
		});
		

		doctorMenuForm.getCalendarBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		});
		
		try {
			LoadNames();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void LoadNames() throws IOException{
		String UID=Main.Main.ID;
		
		/*PatientClient patientClient=new PatientClient();
		String ServerResponse= patientClient.ObtainDoctorProfile(UID);
		String [] data= ServerResponse.split(",");
		String OtherNames=data[1];*/
		
		Doctor doc=Main.Main.clinicClient.getClinicInterface().getDoctor(UID);
		String OtherNames=doc.getOtherNames();
		doctorMenuForm.getDashboardLabel().setText("Dr. "+OtherNames.toUpperCase()+" DASHBOARD");
	}


	public Scene CreateScene(){
		return this.scene;
	}




}
