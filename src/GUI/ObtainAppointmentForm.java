package GUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ViewModel.TopMenuImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class ObtainAppointmentForm {

	private static Scene scene;
	private static GridPane grid;
	private Button btnSave;
	private Button btnExit;
	private Button newPos;

	private GridPane grid2;

	private TextField   txtLastName;
	private TextField   txtFirstName;
	private RadioButton btnMale;
	private RadioButton btnFemale;
	private TextField   txtAddress;
	private TextField   txtEmail;
	private TextField   txtPhone;

	private TextField   txtReason;
	private DatePicker  txtDate;
	private TextField 	txtstartTime;
	private TextField 	txtendTime;


	private ObservableList<String> positions =  FXCollections.observableArrayList();

	public Scene createScene(){

		BorderPane borderPane=new BorderPane();
		TopMenuImpl topMenu=new TopMenuImpl();
		borderPane.setTop(topMenu.topMenu);

		grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(200);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 200, 50, 50));

		HBox BtnBox=new HBox(10);
		Image imgExit = new Image(getClass().getResourceAsStream("/Assets/exit.png"));
		btnExit=new Button("EXIT",new ImageView(imgExit));

		Image imgSave = new Image(getClass().getResourceAsStream("/Assets/save.png"));
		btnSave=new Button("SAVE",new ImageView(imgSave));

		BtnBox.setAlignment(Pos.CENTER);
		BtnBox.getChildren().addAll(btnSave,btnExit);

		GridPane grid1=new GridPane();
		final String cssBorder = "-fx-border-color: #99D4CA;\n"
				+ "-fx-border-insets: 5;\n"
				+ "-fx-border-width: 2;\n";

		grid1.setStyle(cssBorder);
		grid1.setHgap(10);
		grid1.setVgap(10);
		grid1.setPadding(new Insets(25, 25, 25, 25));

		Label Heading1 =new Label("DOCTOR'S DETAILS");
		grid1.add(Heading1, 0, 0);

		Label lblLastName = new Label("Last Name");
		txtLastName = new TextField();
		grid1.add(lblLastName, 0, 1);
		grid1.add(txtLastName, 1, 1);


		Label lblFirstName = new Label("First Name");
		txtFirstName = new TextField();
		grid1.add(lblFirstName, 0, 2);
		grid1.add(txtFirstName, 1, 2);


		Label lblAddress = new Label("Address");
		txtAddress = new TextField();
		grid1.add(lblAddress, 0, 3);
		grid1.add(txtAddress, 1, 3);

		Label lblEmail=new Label("Email");
		txtEmail=new TextField();
		grid1.add(lblEmail, 0, 4);
		grid1.add(txtEmail, 1, 4);

		Label lblPhone=new Label("Phone");
		txtPhone=new TextField();
		grid1.add(lblPhone, 0, 5);
		grid1.add(txtPhone, 1, 5);

		grid2=new GridPane();
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setStyle(cssBorder);
		grid2.setPadding(new Insets(25, 25, 25, 25));

		Label Heading2=new Label("APPOINTMENT DETAILS");
		grid2.add(Heading2, 0, 0);

		Label lblReason = new Label("Brief Symptoms");
		txtReason = new TextField();
		grid2.add(lblReason, 0, 3);
		grid2.add(txtReason, 1, 3);

		Label lblDate= new Label("Date of Appointment");
		txtDate=new DatePicker();
		String pattern = "yyyy-MM-dd";
		txtDate.setEditable(true);
		txtDate.setPromptText(pattern.toLowerCase());
		txtDate.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override 
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override 
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}});

		grid2.add(lblDate,0, 4);
		grid2.add(txtDate, 1, 4);
		
		
		Label lblStartTime = new Label("StartTime");
		txtstartTime = new TextField();
		grid2.add(lblStartTime, 0, 5);
		grid2.add(txtstartTime, 1, 5);
		
		
		Label lblEndTiem = new Label("EndTime");
		txtendTime = new TextField();
		grid2.add(lblEndTiem, 0, 6);
		grid2.add(txtendTime, 1, 6);
		

		grid.add(grid1, 1, 0,4,3);
		grid.add(grid2, 1,5,4,3);

		BtnBox.setAlignment(Pos.CENTER);
		grid.add(BtnBox, 1, 10,4,3);

		borderPane.setCenter(grid);
		scene = new Scene(borderPane, 1200, 900);

		return scene;
	}

	public static GridPane returnGrid(){
		return grid;
	}

	public static Scene ReturnScene(){
		return scene;
	}

	public Button getBtnSave(){
		return btnSave;
	}

	public Button getBtnExit(){
		return btnExit;
	}

	public Button getBtnNewPos()
	{
		return newPos;
	}

	public TextField getTxtSurname(){
		return txtLastName;
	}

	public TextField getTxtFirstName(){
		return txtFirstName;
	}

	public RadioButton getSex(){
		if(btnMale.isSelected())
			return btnMale;
		else 
			return btnFemale;
	}

	public TextField getTxtPhone(){
		return txtPhone;
	}

	public GridPane getWorkDetailGrid()
	{
		return grid2;
	}


	public ObservableList<String> getListPosition() {
		return positions;
	}


	public TextField getTxtEmail(){
		return txtEmail;
	}

	public TextField getTxtAddress()
	{
		return txtAddress;
	}

	public TextField getTxtstartTime() {
		return txtstartTime;
	}

	public void setTxtstartTime(TextField txtstartTime) {
		this.txtstartTime = txtstartTime;
	}

	public TextField getTxtendTime() {
		return txtendTime;
	}

	public void setTxtendTime(TextField txtendTime) {
		this.txtendTime = txtendTime;
	}

	public TextField getTxtReason() {
		return txtReason;
	}

	public void setTxtReason(TextField txtReason) {
		this.txtReason = txtReason;
	}

	public DatePicker getTxtDate() {
		return txtDate;
	}

	public void setTxtDate(DatePicker txtDate) {
		this.txtDate = txtDate;
	}

}
