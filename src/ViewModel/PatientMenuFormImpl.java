package ViewModel;


import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import Communications.PatientClient;
import GUI.CalendarView;
import GUI.ObtainAppointmentForm;
import GUI.PatientMenuForm;
import GUI.ProfileForm;
import GUI.ViewDoctorForm;
import clinicinterface.Patient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class PatientMenuFormImpl {
	private Scene scene;
	private PatientMenuForm patientMenuForm;

	public PatientMenuFormImpl(){
		this.patientMenuForm=new PatientMenuForm();
		this.scene=patientMenuForm.createScene();
		
		try {
			LoadNames();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		patientMenuForm.getViewAppointments().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new ObtainAppointmentFormImpl().CreateScene());
			}
		});
		
		patientMenuForm.getViewPatientBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//Main.Main.NavigateTo(new ViewDoctorForm().CreateViewDoctorScene());
				Main.Main.NavigateTo(new ViewDoctorFormImpl().createScene());
				
			}
		});

		patientMenuForm.getExit().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new LoginFormImpl().createScene());
			}
		});

		patientMenuForm.getMyProfileBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new ProfileFormImpl().createScene());

			}
		});

		patientMenuForm.getCalendarBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
                Main.Main.NavigateTo(new CalendarView().CreateScene());
			}
		});
	}
	
	public void LoadNames() throws IOException{
		String UID=Main.Main.ID;
		/*PatientClient p=new PatientClient();
		String ServerResponse= p.ObtainProfile(UID);
		String [] data= ServerResponse.split(",");
		String OtherNames=data[1];*/
		Patient pat=Main.Main.clinicClient.getClinicInterface().getPatient(UID);
		String OtherNames=pat.getOtherNames();
		patientMenuForm.getDashboardLabel().setText(OtherNames.toUpperCase()+" DASHBOARD");
		
	}

	public Scene CreateScene(){
		return this.scene;
	}




}
