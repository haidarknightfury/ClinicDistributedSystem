package ViewModel;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import Communications.PatientClient;
import GUI.ObtainAppointmentForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;


public class ObtainAppointmentFormImpl {


	private Scene scene;
	private ObtainAppointmentForm obtainAppointmentForm;

	private String txtDate;
	private String txtStartTime;
	private String txtEndTime;
	private String txtReason;

	private PatientClient patientClient;
	private Doctor SelectedDoctor;

	public ObtainAppointmentFormImpl(){
		Init();
	}

	public ObtainAppointmentFormImpl(Doctor doc){
		this.SelectedDoctor=doc;
		System.out.println(doc.toString());
		Init();
	}

	public void Init(){

		obtainAppointmentForm=new ObtainAppointmentForm();
		this.scene=obtainAppointmentForm.createScene();
		try {
			patientClient=new PatientClient();
			obtainAppointmentForm.getTxtFirstName().setText(SelectedDoctor.getFirstName());
			obtainAppointmentForm.getTxtSurname().setText(SelectedDoctor.getOtherNames());
			obtainAppointmentForm.getTxtPhone().setText(SelectedDoctor.getContactNumber());
			obtainAppointmentForm.getTxtAddress().setText(SelectedDoctor.getAddress());
			obtainAppointmentForm.getTxtEmail().setText(SelectedDoctor.getEmail());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		obtainAppointmentForm.getBtnSave().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if(SelectedDoctor!=null){
					
					String DocID= SelectedDoctor.getID();
					String PatientID= Main.Main.ID;
					
					txtDate=obtainAppointmentForm.getTxtDate().getAccessibleText();
					txtReason=obtainAppointmentForm.getTxtReason().getText();
					txtStartTime=obtainAppointmentForm.getTxtstartTime().getText();
					txtEndTime=obtainAppointmentForm.getTxtendTime().toString();

					try {
						
						/*String responseCode=patientClient.RequestAppointment(PatientID,DocID,txtDate,txtReason);
						System.out.println("ServerResponse: "+ responseCode);*/
						Main.Main.getClinicClient().getClinicInterface().InsertPendingAppointment(PatientID, DocID, txtDate, txtReason);
					    //Main.Main.getClinicClient().getClinicInterface().InsertAppointment(new Appointment(txtDate, "", "", DocID, PatientID, "", ""));
					    

					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println("SAVING APPOINTMENT");
				}
				else{
					System.out.println("NOT SELECTED ANY DOCTOR");
				}

			}
		});

	}

	public Scene CreateScene(){
		return scene;
	}


}
