package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TopMenu extends HBox {
	
	public Button getNextBtn() {
		return NextBtn;
	}

	public void setNextBtn(Button nextBtn) {
		NextBtn = nextBtn;
	}

	public Button getPreviousBtn() {
		return PreviousBtn;
	}

	public void setPreviousBtn(Button previousBtn) {
		PreviousBtn = previousBtn;
	}

	public Button getHomeBtn() {
		return HomeBtn;
	}

	public void setHomeBtn(Button homeBtn) {
		HomeBtn = homeBtn;
	}

	Button NextBtn;
	Button PreviousBtn;
	Button HomeBtn;
	
	public TopMenu(){
		super();
		
		this.setPadding(new Insets(15, 12, 15, 12));
		this.setSpacing(10);
	    this.setAlignment(Pos.TOP_CENTER);
		

		
		PreviousBtn=new Button("");
		Image imagePrev = new Image(getClass().getResourceAsStream("/Assets/Prev.png"));
		PreviousBtn.setGraphic(new ImageView(imagePrev));
		
		HomeBtn=new Button("");
		Image imageHome = new Image(getClass().getResourceAsStream("/Assets/Home.png"));
		HomeBtn.setGraphic(new ImageView(imageHome));
		
		NextBtn=new Button("");
		Image imageNext = new Image(getClass().getResourceAsStream("/Assets/Next.png"));
		NextBtn.setGraphic(new ImageView(imageNext));
		
		this.getChildren().add(PreviousBtn);
		this.getChildren().add(HomeBtn);
		this.getChildren().add(NextBtn);

	}
	

}
