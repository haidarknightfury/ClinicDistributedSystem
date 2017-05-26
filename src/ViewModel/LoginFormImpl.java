package ViewModel;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import Communications.PatientClient;
import GUI.AppointmentListForm;
import GUI.DoctorMenuForm;
import GUI.LoginForm;
import GUI.PatientMenuForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class LoginFormImpl {

	private Scene scene;
	public PatientClient patientClient;

	public LoginFormImpl(){
		LoginForm LoginForm = new LoginForm();
		scene = LoginForm.createScene();
		
		try {
			patientClient=new PatientClient();
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}

		LoginForm.getLoginButton().setOnAction(new EventHandler<ActionEvent>() { 

			@Override
			public void handle(ActionEvent e) {
				String username = LoginForm.getUsername();
				String password = LoginForm.getPassword();
				if(username.equalsIgnoreCase("doctor"))
				    Main.Main.NavigateTo(new DoctorMenuFormImpl().CreateScene());
				else if(username.equalsIgnoreCase("patient"))
					  Main.Main.NavigateTo(new PatientMenuFormImpl().CreateScene());
				else{
					try {
						System.out.println("LOGIN :"+ username+" , "+password);
						//String Resp=(patientClient.Login(username, password)).trim();
						
						String Resp="";
						int code=Main.Main.clinicClient.getClinicInterface().Login(username, password);
						
						if(code==1){
							Resp="Patient";
						}
						else if(code==2){
							Resp="Doctor";
						}
						System.out.println(Resp+ "LOGIN");
						
						if(Resp.equalsIgnoreCase("Patient")){
							  Main.Main.ID=username;
							  Main.Main.ClientType="Patient";
							  Main.Main.NavigateTo(new PatientMenuFormImpl().CreateScene());
						}
						else if(Resp.equalsIgnoreCase("Doctor")){
							Main.Main.ID=username;
							Main.Main.ClientType="Doctor";
							Main.Main.NavigateTo(new DoctorMenuFormImpl().CreateScene());
						}
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		
		LoginForm.getBtnRegisterPatientBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				 Main.Main.NavigateTo(new PatientRegistrationFormImpl().createScene());
				
			}
		});
		
		LoginForm.getBtnRegisterDoctorBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.NavigateTo(new DoctorRegistrationFormImpl().createScene());
				
			}
		});
		
	}

	public Scene createScene() {return scene;}
}
