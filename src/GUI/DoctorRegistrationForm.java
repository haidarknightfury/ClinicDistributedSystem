package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ViewModel.TopMenuImpl;
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

public class DoctorRegistrationForm {

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
    private TextField   txtPhone;
    private ComboBox    txtPosition = new ComboBox();
    private DatePicker  txtDOB;
    private TextField   txtLogin;
    private TextField   txtEmail;
    private TextField   txtPassword;
    
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
        
        Label Heading1 =new Label("DOCTOR DETAILS");
        grid1.add(Heading1, 0, 0);
        
        Label lblLastName = new Label("Last Name");
        txtLastName = new TextField();
        grid1.add(lblLastName, 0, 1);
        grid1.add(txtLastName, 1, 1);
        
        
        Label lblFirstName = new Label("First Name");
        txtFirstName = new TextField();
        grid1.add(lblFirstName, 0, 2);
        grid1.add(txtFirstName, 1, 2);
        
        Label lblSex=new Label("Sex");
        ToggleGroup Sex = new ToggleGroup();
        btnMale= new RadioButton("Male");
        btnMale.setToggleGroup(Sex);
        btnMale.setSelected(true);
        btnFemale = new RadioButton("Female");
        btnFemale.setToggleGroup(Sex);
        
        grid1.add(lblSex, 0, 3);
        grid1.add(btnMale, 1, 3);
        grid1.add(btnFemale, 2, 3);
               
        Label lblPhone = new Label("Phone");
        txtPhone = new TextField();
        grid1.add(lblPhone, 0, 4);
        grid1.add(txtPhone, 1, 4);
        
        Label lblDob= new Label("Date of Birth");
        txtDOB=new DatePicker();
        String pattern = "yyyy-MM-dd";
        txtDOB.setEditable(false);
        txtDOB.setPromptText(pattern.toLowerCase());
        txtDOB.setConverter(new StringConverter<LocalDate>() {
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
        
        grid1.add(lblDob,0, 5);
        grid1.add(txtDOB, 1, 5);
        
        Label lblAddress = new Label("Address");
        txtAddress = new TextField();
        grid1.add(lblAddress, 0, 6);
        grid1.add(txtAddress, 1, 6);
        
        
        grid2=new GridPane();
        newPos=new Button("New");
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setStyle(cssBorder);
        grid2.setPadding(new Insets(25, 25, 25, 25));
        
        Label Heading2=new Label("LOGIN DETAILS");
        grid2.add(Heading2, 0, 0);
        
        Label lblLoginID=new Label("Login ID");
        txtLogin=new TextField();
        grid2.add(txtLogin, 1, 2);
        grid2.add(lblLoginID, 0, 2);
        
        Label lblPassword=new Label("Password");
        txtPassword=new TextField();
        grid2.add(lblPassword, 0, 3);
        grid2.add(txtPassword, 1, 3);
        
        Label lblEmail=new Label("Email");
        txtEmail=new TextField();
        grid2.add(lblEmail, 0, 4);
        grid2.add(txtEmail, 1, 4);
        
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
    
    public void setListPosition(ObservableList<String> positions)
    {
    	txtPosition.getItems().clear();
    	txtPosition.getItems().addAll(positions);
    	txtPosition.getSelectionModel().selectFirst();
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
    
    public ComboBox getTxtPosition(){
    	  return txtPosition;
    }
    
    public DatePicker getTxtDOB(){
    	return txtDOB;
    }

	public ObservableList<String> getListPosition() {
		return positions;
	}
   
	
    public TextField getTxtLogin(){
    	return txtLogin;
    }

    public TextField getTxtPassword(){
    	return txtPassword;
    }

    public TextField getTxtEmail(){
    	return txtEmail;
    }
    
    public TextField getTxtAddress()
    {
    	return txtAddress;
    }

}
    

