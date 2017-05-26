package GUI;

import java.time.LocalDate;
import java.time.LocalTime;

import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;

public class ViewDoctorForm {
	public static Scene scene;

	private Rectangle2D boxBounds = new Rectangle2D(100, 100, 300, 180);
	private double ACTION_BOX_HGT = 30;
	private SimpleBooleanProperty isExpanded = new SimpleBooleanProperty();
	private VBox searchPane;
	private TextField searchField;
	private Rectangle clipRect;
	private Timeline timelineUp;
	private Timeline timelineDown;
	private TextField txtTotal;

	private StackPane downArrow;
	private StackPane upArrow;
	private Label searchLbl;
	private Button searchBtn;
	private Button resetBtn;

	private ObservableList<Doctor> DoctorList = FXCollections.observableArrayList();

	private TableView<Doctor> table = new TableView<Doctor>();
	private Button btnAdd;
	private Button btnExit;
	
	
	private TableColumn actionCol=new TableColumn<>();
	private TableColumn<Doctor, String> DocID = new TableColumn<Doctor,String>("ID");
	private TableColumn<Doctor, String> DocSurname = new TableColumn<Doctor,String>("FirstName");
	private TableColumn<Doctor,String> DocOtherNames = new TableColumn<Doctor,String>("OtherNames");
	private TableColumn<Doctor, String> DocSpecialization = new TableColumn<Doctor,String>("Specialization");
	private TableColumn<Doctor,String> DocContact = new TableColumn<Doctor,String>("ContactNumber");
	

	public Scene CreateViewDoctorScene() {

		downArrow=new StackPane();
		downArrow.setStyle("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 0 L1 0 L.5 1 Z\";");
		downArrow.setMaxHeight(10);
		downArrow.setMaxWidth(15);

		upArrow=new StackPane();
		upArrow.setStyle("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 0 L1 0 L.5 1 Z\";");
		upArrow.setMaxHeight(10);
		upArrow.setMaxWidth(15);

		searchLbl=new Label("Search");
		searchLbl.setGraphic(downArrow);
		searchLbl.setContentDisplay(ContentDisplay.RIGHT);

		HBox H=new HBox(10);
		H.setPadding(new Insets(10,10,10,10));

		H.alignmentProperty().set(Pos.CENTER);

		Image AddImg= new Image(getClass().getResourceAsStream("/Assets/AddNew.png"));
		btnAdd=new Button(" NEW ",new ImageView(AddImg));

		Image ExitImg= new Image(getClass().getResourceAsStream("/Assets/exit.png"));
		btnExit=new Button(" EXIT ",new ImageView(ExitImg));
		btnExit.setPrefWidth(btnAdd.getPrefWidth());

		H.getChildren().addAll(btnAdd,btnExit);

		HBox BPanel=new HBox(50);
		Label lblTotal=new Label("Doctor List");

		TextField txtTotal=new TextField();
		BPanel.getChildren().addAll(lblTotal,txtTotal);

		StackPane root = new StackPane();
		root.setPadding(new Insets(5, 10, 5, 40));
		root.setAlignment(Pos.TOP_RIGHT);

		root.autosize();

		GridPane grid=new GridPane();
		grid.add(root, 1, 0);
		grid.add(H, 1, 1);

		scene = new Scene(grid,Color.web("#C4C09F"));

		configureTable(root);
		configureSearch(root);
		setAnimation();
		int sum=0;
		
		for (Doctor doctor : DoctorList) {
			sum++;
		}
		txtTotal.setText(sum+"");
		table.setPrefSize(1500, 500);
		table.prefWidthProperty().bind(scene.widthProperty().multiply(0.94));
		table.prefHeightProperty().bind(scene.heightProperty().multiply(0.94));

		isExpanded.addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					timelineDown.play();
					searchLbl.setGraphic(upArrow);
					searchField.requestFocus();
				}else{
					timelineUp.play();
					searchLbl.setGraphic(downArrow);
				}
			}
		});

		table.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					timelineUp.play();
					searchLbl.setGraphic(downArrow);
				}
			}
		});
		return scene;
	}

	private void setAnimation(){
		clipRect = new Rectangle();
		clipRect.setWidth(boxBounds.getWidth());
		clipRect.setHeight(ACTION_BOX_HGT);
		clipRect.translateYProperty().set(boxBounds.getHeight()-ACTION_BOX_HGT);
		searchPane.setClip(clipRect);
		searchPane.translateYProperty().set(-(boxBounds.getHeight()-ACTION_BOX_HGT));

		final Timeline timelineDown1 = new Timeline();
		timelineDown1.setCycleCount(2);
		timelineDown1.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (boxBounds.getHeight()-15));
		final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
		final KeyValue kv3 = new KeyValue(searchPane.translateYProperty(), -15);
		final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
		timelineDown1.getKeyFrames().add(kf1);

		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				timelineDown1.play();

			}
		};

		timelineDown = new Timeline();
		timelineUp = new Timeline();

		timelineDown.setCycleCount(1);
		timelineDown.setAutoReverse(true);
		final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getHeight());
		final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
		final KeyValue kvDwn3 = new KeyValue(searchPane.translateYProperty(), 0);
		final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), onFinished, kvDwn1, kvDwn2, kvDwn3);
		timelineDown.getKeyFrames().add(kfDwn);

		timelineUp.setCycleCount(1); 
		timelineUp.setAutoReverse(true);
		final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), ACTION_BOX_HGT);
		final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight()-ACTION_BOX_HGT);
		final KeyValue kvUp3 = new KeyValue(searchPane.translateYProperty(), -(boxBounds.getHeight()-ACTION_BOX_HGT));
		final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2, kvUp3);
		timelineUp.getKeyFrames().add(kfUp);
	}

	private void configureTable(StackPane root) {
		VBox vb = new VBox();
		vb.setSpacing(8);
		table.setEditable(true);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(DocID,DocSurname,DocOtherNames,DocSpecialization,DocContact,actionCol);
		vb.getChildren().addAll(TextBuilder.create().text("").font(Font.font("Arial", 22)).build(),table);
		root.getChildren().add(vb);
	}

	private void configureSearch(StackPane root) {
		searchPane = new VBox();
		searchPane.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight());
		searchPane.setAlignment(Pos.TOP_RIGHT);
		StackPane sp1 = new StackPane();
		sp1.setPadding(new Insets(10));
		sp1.setAlignment(Pos.TOP_LEFT);
		sp1.setStyle("-fx-background-color:#669999;-fx-background-insets:0,1.5;-fx-opacity:1;-fx-background-radius:0px 0px 0px 5px;");
		sp1.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight()-ACTION_BOX_HGT);

		Label searchTitle = LabelBuilder.create().text("SEARCH SPECS").font(Font.font("Arial", 20)).build();
		searchField = new TextField();
		HBox hb = HBoxBuilder.create().children(LabelBuilder.create().text("ENTER SPECS: ").font(Font.font("Arial", 14)).build(), searchField).maxHeight(24).translateY(45).build();

		searchBtn = new Button("Search");
		resetBtn = new Button("Reset");


		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				String str = searchField.getText();
				table.getItems().clear();
				if(str!=null && str.length()>0){
					for (Doctor doc : DoctorList) {
											
					}
				}else{
					table.getItems().addAll(DoctorList);
				}
			}
		});

		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				table.getItems().clear();
				table.getItems().addAll(DoctorList);
			}
		});

		HBox hb2 = HBoxBuilder.create().children(searchBtn, resetBtn).maxHeight(24).spacing(10).translateY(100).build();
		sp1.getChildren().addAll(searchTitle,hb,hb2);

		StackPane sp2 = new StackPane();
		sp2.setPrefSize(100, ACTION_BOX_HGT);
		sp2.getChildren().add(searchLbl);
		sp2.setStyle("-fx-cursor:hand;-fx-background-color:#669999;-fx-border-width:0px 1px 1px 1px;-fx-border-color:#333333;-fx-opacity:1;-fx-border-radius:0px 0px 5px 5px;-fx-background-radius:0px 0px 5px 5px;");
		sp2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				togglePaneVisibility();
			}
		});
		searchPane.getChildren().addAll(GroupBuilder.create().children(sp1).build(),GroupBuilder.create().children(sp2).build());
		root.getChildren().add(GroupBuilder.create().children(searchPane).build());
	}

	private void togglePaneVisibility(){
		if(isExpanded.get()){
			isExpanded.set(false);
		}else{
			isExpanded.set(true);
		}
	}

	public static Scene ReturnScene(){
		return scene;
	}

	public TextField getSearchField(){
		return searchField;
	}

	public TextField getTxtTotal() {
		return txtTotal;
	}

	public StackPane getUpArrow() {
		return upArrow;
	}

	public Button getBtnAdd() {
		return btnAdd;
	}

	public Button getBtnExit() {
		return btnExit;
	}

	public TableColumn getActionCol() {
		return actionCol;
	}

	public Button getSearchButton(){
		return searchBtn;
	}

	public Button getResetButton(){
		return resetBtn;
	}
	
	public void setActionCol(TableColumn actionCol) {
		this.actionCol = actionCol;
	}
	
	
	public TableView<Doctor> getDoctorTable(){
		return table;
	}
	
	public ObservableList<Doctor> getDoctorList(){
		return DoctorList;
	}

	public TableColumn<Doctor, String> getDocID() {
		return DocID;
	}

	public void setDocID(TableColumn<Doctor, String> docID) {
		DocID = docID;
	}

	public TableColumn<Doctor, String> getDocSurname() {
		return DocSurname;
	}

	public void setDocSurname(TableColumn<Doctor, String> docSurname) {
		DocSurname = docSurname;
	}

	public TableColumn<Doctor, String> getDocOtherNames() {
		return DocOtherNames;
	}

	public void setDocOtherNames(TableColumn<Doctor, String> docOtherNames) {
		DocOtherNames = docOtherNames;
	}

	public TableColumn<Doctor, String> getDocSpecialization() {
		return DocSpecialization;
	}

	public void setDocSpecialization(TableColumn<Doctor, String> docSpecialization) {
		DocSpecialization = docSpecialization;
	}

	public TableColumn<Doctor, String> getDocContact() {
		return DocContact;
	}

	public void setDocContact(TableColumn<Doctor, String> docContact) {
		DocContact = docContact;
	}
	


}
