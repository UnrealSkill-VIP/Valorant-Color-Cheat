package dev.niro.valorantcheat.gui;

import java.net.URL;
import java.util.ResourceBundle;

import dev.niro.valorantcheat.Main;
import dev.niro.valorantcheat.utils.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class GuiController implements Initializable {

	@FXML
	AnchorPane mainPane;
	@FXML
	ImageView bgImage;
	@FXML
	HBox contentBox;
	@FXML
	VBox content1;
	@FXML
	VBox content2;
	@FXML
	VBox content3;
	@FXML
	Rectangle seperator1;
	@FXML
	Rectangle seperator2;
	@FXML
	Rectangle seperator3;
	
	@FXML
	CheckBox debug;
	@FXML
	CheckBox aimbot;
	@FXML
	CheckBox triggerbot;
	@FXML
	CheckBox sniper;
	
	@FXML
	TextField fovW;
	@FXML
	TextField fovH;
	@FXML
	TextField maxTPS;
	@FXML
	TextField moveSpeed;
	@FXML
	TextField recoilSpeed;
	@FXML
	TextField maxRecoil;
	@FXML
	TextField scopeTime;
	@FXML
	TextField reviseTime;
	
	@FXML
	Button exitButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contentBox.prefWidthProperty().bind(mainPane.widthProperty().subtract(mainPane.heightProperty().multiply(bgImage.getImage().getWidth() / bgImage.getImage().getHeight())));
		contentBox.prefHeightProperty().bind(mainPane.heightProperty());
		
		bgImage.fitHeightProperty().bind(mainPane.heightProperty());
		bgImage.fitWidthProperty().bind(mainPane.heightProperty().multiply(bgImage.getImage().getWidth() / bgImage.getImage().getHeight()));
		
		content1.prefWidthProperty().bind(contentBox.widthProperty().divide(3.0));
		content2.prefWidthProperty().bind(contentBox.widthProperty().divide(3.0));
		content3.prefWidthProperty().bind(contentBox.widthProperty().divide(3.0));
		
		seperator1.widthProperty().bind(content1.widthProperty().subtract(20));
		seperator2.widthProperty().bind(content2.widthProperty().subtract(20));
		seperator3.widthProperty().bind(content3.widthProperty().subtract(20));
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Platform.exit();
		    	Logger.log("Program exit.");
		    	System.exit(0);
		    }
		});
		
		debug.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Main.debug = ((CheckBox)e.getSource()).isSelected();
		    }
		});
		aimbot.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Main.aimbot = ((CheckBox)e.getSource()).isSelected();
		    }
		});
		triggerbot.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Main.triggerbot = ((CheckBox)e.getSource()).isSelected();
		    }
		});
		sniper.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Main.sniper = ((CheckBox)e.getSource()).isSelected();
		    }
		});
		
		ChangeListener<? super String> textFieldListener = (observable, oldValue, newValue) -> {
			TextField tf = null;
			if(observable == fovH.textProperty()) 
				tf = fovH;
			else if(observable == fovW.textProperty()) 
				tf = fovW;
			else if(observable == maxTPS.textProperty()) 
				tf = maxTPS;
			else if(observable == moveSpeed.textProperty()) 
				tf = moveSpeed;
			else if(observable == recoilSpeed.textProperty()) 
				tf = recoilSpeed;
			else if(observable == maxRecoil.textProperty()) 
				tf = maxRecoil;
			else if(observable == scopeTime.textProperty()) 
				tf = scopeTime;
			else if(observable == reviseTime.textProperty()) 
				tf = reviseTime;
			
			tf.setText(newValue.replaceAll("[^0-9.]", "") + (tf == fovH || tf == fovW ? "%" : ""));
			
			float value = 0;
			try {
				if(newValue.length() > 0)
					value = Float.valueOf(newValue.replaceAll("[^0-9.]", ""));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
						
			if(tf == fovH) 
				Main.fovHeight = value / 100;
			else if(tf == fovW) 
				Main.fovWidth = value / 100;
			else if(tf == maxTPS) 
				Main.maxTps = (int) value;
			else if(tf == moveSpeed) 
				Main.moveSpeed = value / 100;
			else if(tf == recoilSpeed) 
				Main.recoilSpeed = value / 1000;
			else if(tf == maxRecoil) 
				Main.maxRecoil = value;
			else if(tf == scopeTime) 
				Main.visirTime = value;
			else if(tf == reviseTime) 
				Main.reviseTime = value;
			
			Main.updateSettings();
		};
		
		fovW.textProperty().addListener(textFieldListener);		
		fovH.textProperty().addListener(textFieldListener);		
		maxTPS.textProperty().addListener(textFieldListener);
		moveSpeed.textProperty().addListener(textFieldListener);
		recoilSpeed.textProperty().addListener(textFieldListener);
		maxRecoil.textProperty().addListener(textFieldListener);
		scopeTime.textProperty().addListener(textFieldListener);
		reviseTime.textProperty().addListener(textFieldListener);
	}    
}