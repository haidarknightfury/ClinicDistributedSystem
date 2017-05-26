package ViewModel;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;

import Communications.PatientClient;
import GUI.PatientRegistrationForm;
import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PatientRegistrationFormImpl {

	private Scene scene;

	private PatientRegistrationForm patientRegistrationForm;

	private ObservableList<String> positions = FXCollections.observableArrayList();;

	private boolean checkNewPosition = false;
	public PatientClient patientClient;

	public PatientRegistrationFormImpl() {
		patientRegistrationForm=new PatientRegistrationForm();
		
		try {
			patientClient=new PatientClient();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		scene=patientRegistrationForm.createScene();

		patientRegistrationForm.getBtnSave().setOnAction(e->{

			String FirstName = patientRegistrationForm.getTxtFirstName().getText();
			String Surname = patientRegistrationForm.getTxtSurname().getText();
			String Sex = patientRegistrationForm.getSex().getText();
			String Phone = patientRegistrationForm.getTxtPhone().getText();
			java.sql.Date DOB = java.sql.Date.valueOf((patientRegistrationForm.getTxtDOB().getValue()));
			String Address = patientRegistrationForm.getTxtAddress().getText();
			String Login = patientRegistrationForm.getTxtLogin().getText();
			String Password = patientRegistrationForm.getTxtPassword().getText().trim();
			String Email=patientRegistrationForm.getTxtEmail().getText();
			
			try {
				/*patientClient.Register(new Patient(FirstName, Surname, Address, Phone, Email, 
						"NONE", "NONE", "NONE", Login, Password));*/
				//TODO:INSERT PATIENT
				Main.Main.getClinicClient().getClinicInterface().InsertPatient(new Patient(FirstName, Surname, Address, Phone, Email, 
						"NONE", "NONE", "NONE", Login, Password));
				
				System.out.println(FirstName+" REGISTERED");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
		});
		
		patientRegistrationForm.getBtnExit().setOnAction(e->{
			
		});
	}

	public Scene createScene() {
		return scene;
	}
}
