package GUI;

import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;

import ViewModel.TopMenuImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class PatientViewerForm {
	private BorderPane layout;

	private Scene scene;
	private TextField txtFirstName, txtLastName,txtAge,txtDiseases,txtHistory;
	private Button submitButton;

	public ObservableList<Patient>PatientList=FXCollections.observableArrayList();
	public ListView<Patient>PatientListView;


	public ListView listView;	
	public  ObservableList<String> names = FXCollections.observableArrayList();

	public PatientViewerForm(){
		layout = new BorderPane(); 
		layout.setId("appContainer");
		setSceneProperties();

		buildLeft();
		buildTop();

	}


	public Scene CreateScene(){
		return scene;
	}


	private void buildLeft() {

		BorderPane leftLayout = new BorderPane();

		// Create a faux border-right effect using a Label.
		Label divider = new Label();
		divider.setId("divider1");
		divider.setPrefWidth(1);
		divider.setMinHeight(Screen.getPrimary().getBounds().getHeight());
		leftLayout.setRight(divider);


		VBox buttonBox = new VBox();
		buttonBox.setAlignment(Pos.TOP_CENTER);
		buttonBox.setId("buttonMenuContainer");
		buttonBox.setSpacing(10);

		Button btnLabel= new Button("PATIENTS");
		btnLabel.setMaxWidth(Double.MAX_VALUE);
		btnLabel.setFont(new Font(20));
		btnLabel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				layout.setCenter(PatientView());
			}
		});

		listView = new ListView(names);	
		listView.setPrefSize(200,390);
		listView.setEditable(true);

		listView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				layout.setCenter(PatientView());
				int index= listView.getSelectionModel().getSelectedIndex();
				Patient selectedPatient=PatientList.get(index);
				txtFirstName.setText(selectedPatient.getFirstName());
				txtLastName.setText(selectedPatient.getOtherNames());
				txtAge.setText("25");
				txtHistory.setText("Allergies : "+selectedPatient.getAllergies());

			}

		});

		listView.setItems(names);
		buttonBox.getChildren().addAll(btnLabel,listView);
		leftLayout.setCenter(buttonBox);
		//leftLayout.setPrefWidth(Screen.getPrimary().getBounds().getWidth()*0.4);
		leftLayout.setPrefWidth(300);
		layout.setLeft(leftLayout);

	}

	/**
	 * buildTop. Create a Title Bar.
	 */
	private void buildTop() {

		BorderPane topLayout = new BorderPane();

		//Add CSS Style ID.
		topLayout.setId("topLayoutContainer");
		Label divider = new Label();
		divider.setId("divider2");
		divider.setMaxHeight(1);
		divider.setMinHeight(1);
		divider.setMinWidth(Screen.getPrimary().getBounds().getWidth()*0.5);
		topLayout.setBottom(divider);

		//Create an HBox to hold title.
		//We use the HBox to set text alignment to TOP LEFT
		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setId("titleBox");//Create title.
		Label title = new Label();
		title.setText("Patient List");
		title.setId("appTitle");//Add Title label to titleBox
		titleBox.getChildren().add(title);

		//Add Title Box (with label) to topLayout
		topLayout.setCenter(new TopMenuImpl().topMenu);

		//Add topLayout (a BorderPane Manager) to App Layout.
		layout.setTop(topLayout);

	}

	/**
	 * setSceneProperties. This simply sets app to almost full size.
	 * It also is where the css stylesheet is attached to app.
	 */

	private void setSceneProperties()
	{
		//The percentage values are used as multipliers for screen width/height.
		double percentageWidth = 0.50;
		double percentageHeight = 0.50;

		//Calculate the width / height of screen.
		Rectangle2D screenSize = Screen.getPrimary().getBounds();
		percentageWidth *= screenSize.getWidth();
		percentageHeight *= screenSize.getHeight();

		//Create a scene object. Pass in the layout and set
		//the dimensions to 98% of screen width & 90% screen height.
		this.scene = new Scene(layout, percentageWidth, percentageHeight);

		//Add CSS Style Sheet (located in same package as this class).
		String css = this.getClass().getResource("/CSS/PatientViewer.css").toExternalForm();
		scene.getStylesheets().add(css);

	}

	/**
	 * example1. This method just creates a simple GridPane with 2
	 * rows and 2 columns. This example demonstrates the use of
	 * showing gridLines.
	 * @return
	 */
	private VBox PatientView() {

		//Create a container to fill 100% space in Center Region of
		//App BorderPane (layout).
		VBox exContainer = new VBox();
		exContainer.setId("exContainer");        //Create a new GridPane.
		GridPane gridPane = new GridPane();

		//Turn on GridLines so we can see what is going on.
		//gridPane.setGridLinesVisible(true);

		//Give the GridPane an ID for CSS Styles.
		gridPane.setId("PATIENT'S DETAILS");       //Add some spacing between each control.
		//Comment the next 2 lines out to see what happens when this is
		//not explicitly set. It will remove the padding you specified.
		gridPane.setHgap(70);
		gridPane.setVgap(10);


		gridPane.add(new Label("First Name"), 0, 1);
		txtFirstName = new TextField();
		txtFirstName.setId("txtFirstName");
		gridPane.add(txtFirstName, 1,1);

		//Add Last Name label in Col 0, Row 2
		gridPane.add(new Label("Last Name"), 0,2);

		//Add Last Name Text Field in Col 1, Row 2.
		txtLastName = new TextField();
		txtLastName.setId("txtLastName");
		gridPane.add(txtLastName, 1,2);


		gridPane.add(new Label("Age"), 0,3);
		txtAge = new TextField();
		txtAge.setId("txtAge");
		gridPane.add(txtAge, 1,3);


		gridPane.add(new Label("Diseases"), 0,4);
		txtDiseases = new TextField();
		txtDiseases.setId("txtDiseases");
		gridPane.add(txtDiseases, 1,4);


		gridPane.add(new Label("History"), 0,5);
		txtHistory = new TextField();
		txtHistory.setId("txtHistory");
		gridPane.add(txtHistory, 1,5);


		//Add a Submit Button.
		submitButton = new Button("Submit");
		gridPane.add(submitButton, 1,6);

		//Align the Submit Button to Right.
		gridPane.setHalignment(submitButton, HPos.RIGHT);

		//Add GridPane to container.
		exContainer.getChildren().add(gridPane);

		//Return Container
		return exContainer;
	}


	public Scene getScene() {
		return scene;
	}


	public TextField getTxtFirstName() {
		return txtFirstName;
	}


	public TextField getTxtLastName() {
		return txtLastName;
	}


	public TextField getTxtAge() {
		return txtAge;
	}


	public TextField getTxtDiseases() {
		return txtDiseases;
	}


	public TextField getTxtHistory() {
		return txtHistory;
	}


	public ObservableList<String> getNames() {
		return names;
	}


	public Button getSubmitButton() {
		return submitButton;
	}


	public ObservableList<Patient> getPatientList() {
		return PatientList;
	}


	public void setPatientList(ObservableList<Patient> patientList) {
		PatientList = patientList;
	}


	public ListView<Patient> getPatientListView() {
		return PatientListView;
	}


	public void setPatientListView(ListView<Patient> patientListView) {
		PatientListView = patientListView;
	}


	public ListView getNamesListView() {
		return listView;
	}


	public void setListView(ListView listView) {
		this.listView = listView;
	}


	public void setNames(ObservableList<String> names) {
		this.names = names;
	}





}

