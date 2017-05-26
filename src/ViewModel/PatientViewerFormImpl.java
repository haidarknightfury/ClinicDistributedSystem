package ViewModel;

import java.io.IOException;
import java.util.ArrayList;
import Communications.PatientClient;
import GUI.PatientViewerForm;
import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class PatientViewerFormImpl {
	public PatientViewerForm patientViewerForm;
	public PatientClient patientClient;
	public Scene scene;

	public PatientViewerFormImpl(){
		patientViewerForm=new PatientViewerForm();
		scene=patientViewerForm.CreateScene();
		try {
			patientClient=new PatientClient();
		} catch (Exception e) {
			e.printStackTrace();
		} 

		LoadPatients();
	}

	public void LoadPatients(){
		try {
			//String DID=patientClient.getID(Main.Main.ID, 1);
			//ArrayList<Patient> PList= patientClient.getMyPatientsDetails(DID);
			String DID= Main.Main.getClinicClient().getClinicInterface().GetID(Main.Main.ID, 1);
		    ArrayList<Patient>PList=Main.Main.getClinicClient().getClinicInterface().ObtainMyPatients(DID);
			patientViewerForm.PatientList=FXCollections.observableArrayList(PList);
			patientViewerForm.names.clear();
			for(Patient patient:PList){
				patientViewerForm.getNames().add(patient.getFirstName()+" "+ patient.getOtherNames());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Scene CreateScene(){
		return scene;
	}


}
