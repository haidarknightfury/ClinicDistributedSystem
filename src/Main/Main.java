package Main;

import java.util.Stack;

import Communications.ClinicClient;
import ViewModel.LoginFormImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static Stack<Scene> NavigationStack;
	public static Stage primaryStage;
	public static String ID="";
	public static String ClientType="";
	public static ClinicClient clinicClient;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.NavigationStack=new Stack<>();
		LoginFormImpl loginFormImpl= new LoginFormImpl();
		primaryStage.setScene(loginFormImpl.createScene());
		primaryStage.setTitle("Patient System");
		primaryStage.show();
		Main.primaryStage=primaryStage;
		this.clinicClient=new ClinicClient();
	}
	public static void main(String [] args){
		launch(args);
	}
	
	public static void NavigateTo(Scene scene){
		NavigationStack.push(scene);
		primaryStage.setScene(scene);
	}

	public static void Back(){
		if(!NavigationStack.isEmpty())
		     primaryStage.setScene(NavigationStack.pop());
		else
			System.out.println("NAVIGATION EMPTY");
	}
	
	public static ClinicClient getClinicClient(){
		return clinicClient;
	}
}
