package ViewModel;

import GUI.TopMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TopMenuImpl {
      public TopMenu topMenu;

	public TopMenuImpl() {
		this.topMenu=new TopMenu();
		topMenu.getHomeBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 Main.Main.NavigateTo(new LoginFormImpl().createScene());
			}
		});
		
		topMenu.getPreviousBtn().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.Main.Back();
			}
		});
	}
	
	public TopMenu getTopMenu(){
		return topMenu;
	}
	
	
      
}
