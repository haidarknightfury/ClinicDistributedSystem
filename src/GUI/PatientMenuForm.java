package GUI;

import static javafx.geometry.HPos.RIGHT;

import ViewModel.TopMenuImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PatientMenuForm {
	private Scene scene;
	private Button MyProfileBtn;
	private Button ViewPatientBtn;
	private Button ViewAppointments;
	private Button Exit;
	private Button CalendarBtn;
	private Label DashboardLabel;

	public PatientMenuForm(){}

	public Scene createScene() {

		BorderPane borderPane=new BorderPane();
		TopMenuImpl topMenu=new TopMenuImpl();
		borderPane.setTop(topMenu.topMenu);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Label label1 = new Label("MY DASHBOARD");
		label1.setFont(new Font(25));
		Image image = new Image(getClass().getResourceAsStream("/Assets/configurations.png"));
		label1.setGraphic(new ImageView(image));
		label1.setTextFill(Color.web("#0076a3"));
		label1.setAlignment(Pos.CENTER);
		
		this.DashboardLabel=label1;
		
		Blend blend = new Blend();
		blend.setMode(BlendMode.MULTIPLY);

		DropShadow ds = new DropShadow();
		ds.setColor(Color.rgb(254, 235, 66, 0.3));
		ds.setOffsetX(5);
		ds.setOffsetY(5);
		ds.setRadius(5);
		ds.setSpread(0.2);

		blend.setBottomInput(ds);

		DropShadow ds1 = new DropShadow();
		ds1.setColor(Color.web("#f13a00"));
		ds1.setRadius(20);
		ds1.setSpread(0.2);

		Blend blend2 = new Blend();
		blend2.setMode(BlendMode.MULTIPLY);

		InnerShadow is = new InnerShadow();
		is.setColor(Color.web("#feeb42"));
		is.setRadius(9);
		is.setChoke(0.8);
		blend2.setBottomInput(is);

		InnerShadow is1 = new InnerShadow();
		is1.setColor(Color.web("#f13a00"));
		is1.setRadius(5);
		is1.setChoke(0.4);
		blend2.setTopInput(is1);

		Blend blend1 = new Blend();
		blend1.setMode(BlendMode.MULTIPLY);
		blend1.setBottomInput(ds1);
		blend1.setTopInput(blend2);

		blend.setTopInput(blend1);

		label1.setEffect(blend);
		
		grid.add(label1,1,0,3,1);

		Image imgProfile = new Image(getClass().getResourceAsStream("/Assets/user.png"));
		MyProfileBtn = new Button("Profile",new ImageView(imgProfile));
		MyProfileBtn.setPrefHeight(100);
		MyProfileBtn.setPrefWidth(200);
		grid.add(MyProfileBtn, 0, 1);

		Image imgViewPatient = new Image(getClass().getResourceAsStream("/Assets/search.png"));
		ViewPatientBtn=new Button("View Doctors",new ImageView(imgViewPatient));
		ViewPatientBtn.setPrefHeight(100);
		ViewPatientBtn.setPrefWidth(200);
		grid.add(ViewPatientBtn, 0, 2);



		Image imgViewAppointments = new Image(getClass().getResourceAsStream("/Assets/appointment.png"));
		ViewAppointments=new Button("Get Appointment",new ImageView(imgViewAppointments));
		ViewAppointments.setPrefHeight(100);
		ViewAppointments.setPrefWidth(200);
		grid.add(ViewAppointments, 1, 1);
		
		
		Image imgCalendar = new Image(getClass().getResourceAsStream("/Assets/calendar.png"));
		CalendarBtn=new Button("Calendar",new ImageView(imgCalendar));
		CalendarBtn.setPrefHeight(100);
		CalendarBtn.setPrefWidth(200);
		grid.add(CalendarBtn, 1, 2);

		Image imgExit = new Image(getClass().getResourceAsStream("/Assets/Exit1.png"));
		Exit=new Button("Exit to Main Menu",new ImageView(imgExit));
		Exit.setPrefHeight(100);
		Exit.setPrefWidth(200);
		grid.add(Exit, 2, 1);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 0, 6);
		grid.setColumnSpan(actiontarget, 2);
		grid.setHalignment(actiontarget, RIGHT);
		actiontarget.setId("actiontarget");

		borderPane.setCenter(grid);

		scene = new Scene(borderPane,750, 750);
		scene.getStylesheets().addAll(this.getClass().getResource("/CSS/PatientMenu.css").toExternalForm());

		return scene;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Button getMyProfileBtn() {
		return MyProfileBtn;
	}

	public void setMyProfileBtn(Button myProfileBtn) {
		MyProfileBtn = myProfileBtn;
	}

	public Button getViewPatientBtn() {
		return ViewPatientBtn;
	}

	public void setViewPatientBtn(Button viewPatientBtn) {
		ViewPatientBtn = viewPatientBtn;
	}

	public Button getViewAppointments() {
		return ViewAppointments;
	}

	public void setViewAppointments(Button viewAppointments) {
		ViewAppointments = viewAppointments;
	}

	public Button getExit() {
		return Exit;
	}

	public void setExit(Button exit) {
		Exit = exit;
	}

	public Button getCalendarBtn() {
		return CalendarBtn;
	}

	public void setCalendarBtn(Button calendarBtn) {
		CalendarBtn = calendarBtn;
	}

	public Label getDashboardLabel() {
		return DashboardLabel;
	}

	public void setDashboardLabel(Label dashboardLabel) {
		DashboardLabel = dashboardLabel;
	}

}
