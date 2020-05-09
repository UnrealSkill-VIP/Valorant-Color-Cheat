package dev.niro.valorantcheat.gui.weapons;

import java.net.URL;
import java.util.ResourceBundle;

import dev.niro.valorantcheat.Main;
import dev.niro.valorantcheat.enums.Weapons;
import dev.niro.valorantcheat.gui.GuiFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class WeaponGuiController implements Initializable {

	@FXML
	Button b1;
	@FXML
	Button b2;
	@FXML
	Button b3;
	@FXML
	Button b4;
	@FXML
	Button b5;
	@FXML
	Button b6;
	@FXML
	Button b7;
	@FXML
	Button b8;
	@FXML
	Button b9;
	@FXML
	Button b10;
	@FXML
	Button b11;
	@FXML
	Button b12;
	@FXML
	Button b13;
	@FXML
	Button b14;
	@FXML
	Button b15;
	@FXML
	Button b16;
	@FXML
	Button b17;
	
	GuiFrame gui;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	Weapons w = Weapons.valueOf(((Button)e.getSource()).getText());
		    	Main.recoilSpeed = w.getRecoilSpeed();
		    	Main.maxRecoil = w.getMaxRecoil();
		    	Main.sniper = w.isSniper();
		    	
		    	gui.weaponFinished();
		    }
		};
		
		b1.setOnAction(eh);
		b2.setOnAction(eh);
		b3.setOnAction(eh);
		b4.setOnAction(eh);
		b5.setOnAction(eh);
		b6.setOnAction(eh);
		b7.setOnAction(eh);
		b8.setOnAction(eh);
		b9.setOnAction(eh);
		b10.setOnAction(eh);
		b11.setOnAction(eh);
		b12.setOnAction(eh);
		b13.setOnAction(eh);
		b14.setOnAction(eh);
		b15.setOnAction(eh);
		b16.setOnAction(eh);
		b17.setOnAction(eh);
	}

	public GuiFrame getGui() {
		return gui;
	}

	public void setGui(GuiFrame gui) {
		this.gui = gui;
	}   
}