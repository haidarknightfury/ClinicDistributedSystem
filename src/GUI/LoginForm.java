package GUI;
import static javafx.geometry.HPos.RIGHT;

import ViewModel.TopMenuImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginForm {

	private Scene scene;
	private Button loginBtn = new Button("Sign in");
	private Button btnRegisterDoctorBtn=new Button("New Doctor");
	private Button btnRegisterPatientBtn=new Button("New Patient");
	
	private TextField userTextField;
	private PasswordField pwBox;

	public LoginForm () {}

	@SuppressWarnings("static-access")
	public Scene createScene() {
		
		BorderPane borderPane=new BorderPane();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Image MedicalImage = new Image(getClass().getResourceAsStream("/Assets/medic.jpg"));
		ImageView imageView = new ImageView();
		imageView.setImage(MedicalImage);
		imageView.setFitHeight(200);
		imageView.setFitWidth(200);
		imageView.setOpacity(0.7);

		DigitalClock clock=new DigitalClock();
		clock.setPrefSize(250, 50);
		
		VBox box = new VBox();		
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(imageView);
		grid.add(box, 0, 0, 10, 1);

		grid.add(clock,1 , 3); 

		Label userName = new Label("USERNAME:");
		grid.add(userName, 0, 1);

		userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("PASSWORD:");
		grid.add(pw, 0, 2);

		pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		HBox SignUpBox=new HBox(10);
		SignUpBox.setAlignment(Pos.BOTTOM_LEFT);
		SignUpBox.getChildren().add(btnRegisterDoctorBtn);
		SignUpBox.getChildren().add(btnRegisterPatientBtn);
		btnRegisterDoctorBtn.setId("SignUp");
		btnRegisterPatientBtn.setId("SignUp");

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	    loginBtn.setId("loginbtn");
		hbBtn.getChildren().add(loginBtn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 0, 6);
		grid.setColumnSpan(actiontarget, 2);
		grid.setHalignment(actiontarget, RIGHT);
		actiontarget.setId("actiontarget");
		
		borderPane.setCenter(grid);
		borderPane.setBottom(SignUpBox);

		scene = new Scene(borderPane,750, 750);
		scene.getStylesheets().add(LoginForm.class.getResource("/CSS/Login.css").toExternalForm());

		return scene;
	}

	public Scene ReturnScene() {return scene;}

	public Button getLoginButton(){return this.loginBtn;}

	public String getUsername(){return this.userTextField.getText();}

	public String getPassword(){return this.pwBox.getText();}

	public Button getBtnRegisterPatientBtn() {
		return btnRegisterPatientBtn;
	}

	public Button getBtnRegisterDoctorBtn() {
		return btnRegisterDoctorBtn;
	}

}
