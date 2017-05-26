package ViewModel;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import Communications.PatientClient;
import GUI.PatientRegistrationForm;
import GUI.ProfileForm;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

public class ProfileFormImpl {
	private Scene scene;

	private ProfileForm profileForm;

	public ProfileFormImpl() {
		profileForm=new ProfileForm();
		scene=profileForm.createScene();
		try {
			LoadBoxes();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		profileForm.getBtnSave().setOnAction(e->{

			String FirstName = profileForm.getTxtFirstName().getText();
			String Surname = profileForm.getTxtSurname().getText();
			String Sex = profileForm.getSex().getText();
			String Phone = profileForm.getTxtPhone().getText();
			java.sql.Date DOB = java.sql.Date.valueOf((profileForm.getTxtDOB().getValue()));
			String Address = profileForm.getTxtAddress().getText();
			String Login = profileForm.getTxtLogin().getText();
			String Password = profileForm.getTxtPassword().getText();
			String Email=profileForm.getTxtEmail().getText();
		});


		profileForm.getBtnExit().setOnAction(e->{

		});
	}

	public void LoadBoxes() throws IOException{
		PatientClient p=new PatientClient();
		String UID=Main.Main.ID;
		if(Main.Main.ClientType.equalsIgnoreCase("Patient")){
			
			/*
			String ServerResponse= p.ObtainProfile(UID);
			String [] data= ServerResponse.split(",");
			 */
			
			Patient patient=Main.Main.getClinicClient().getClinicInterface().getPatient(UID);
			String FirstName=patient.getFirstName();
			String OtherNames=patient.getOtherNames();
			String Address=patient.getAddress();
			String ContactNo=patient.getContactNo();
			String Email=patient.getEmail();
			String Allergies=patient.getAllergies();
			String BloodGroup=patient.getBloodGroup();
			String OtherDetails=patient.getOtherDetails();
			String Username=patient.getUsername();
			String Password=patient.getPassword();

			profileForm.getTxtFirstName().setText(FirstName);
			profileForm.getTxtSurname().setText(OtherNames);
			profileForm.getTxtPhone().setText(ContactNo);
			profileForm.getTxtAddress().setText(Address);
			profileForm.getTxtLogin().setText(Username);
			profileForm.getTxtPassword().setText(Password);
			profileForm.getTxtEmail().setText(Email);
		}
		else if(Main.Main.ClientType.equals("Doctor")){
			
			
			/*String doctorSeverResponse= p.ObtainDoctorProfile(UID);
			System.out.println(doctorSeverResponse);
			String [] data= doctorSeverResponse.split(",");*/
			
			Doctor doc=Main.Main.clinicClient.getClinicInterface().getDoctor(UID);
			String Fname=doc.getFirstName();
			String Sname=doc.getOtherNames();
			String Specs=doc.getSpecialization();
			String Contact=doc.getContactNumber();
			String addr=doc.getAddress();
			String email=doc.getEmail();
			String uname=doc.getUsername();
			String passwd=doc.getPassword();
			
			profileForm.getTxtFirstName().setText(Fname);
			profileForm.getTxtSurname().setText(Sname);
			profileForm.getTxtPhone().setText(Contact);
			profileForm.getTxtAddress().setText(addr);
			profileForm.getTxtLogin().setText(uname);
			profileForm.getTxtPassword().setText(passwd);
			profileForm.getTxtEmail().setText(email);
			
		}

	}

	public Scene createScene() {
		return scene;
	}

}
