package ViewModel;

import java.sql.Connection;

import GUI.DoctorRegistrationForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DoctorRegistrationFormImpl {

	private Scene scene;
	private Connection con;

	private DoctorRegistrationForm doctorRegistrationForm;

	private ObservableList<String> positions = FXCollections.observableArrayList();;

	private boolean checkNewPosition = false;

	public DoctorRegistrationFormImpl() {
		doctorRegistrationForm=new DoctorRegistrationForm();
		scene=doctorRegistrationForm.createScene();

		doctorRegistrationForm.getBtnSave().setOnAction(e->{

			String FirstName = doctorRegistrationForm.getTxtFirstName().getText();
			String Surname = doctorRegistrationForm.getTxtSurname().getText();
			String Sex = doctorRegistrationForm.getSex().getText();
			String Phone = doctorRegistrationForm.getTxtPhone().getText();
			java.sql.Date DOB = java.sql.Date.valueOf((doctorRegistrationForm.getTxtDOB().getValue()));
			String Address = doctorRegistrationForm.getTxtAddress().getText();
			String Login = doctorRegistrationForm.getTxtLogin().getText();
			String Password = doctorRegistrationForm.getTxtPassword().getText();
			String Email=doctorRegistrationForm.getTxtEmail().getText();
		});
		
		
		doctorRegistrationForm.getBtnExit().setOnAction(e->{
			
		});
	}

	public Scene createScene() {
		return scene;
	}
}
