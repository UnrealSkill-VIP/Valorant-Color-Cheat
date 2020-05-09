package dev.niro.valorantcheat.gui;


import java.io.IOException;

import dev.niro.valorantcheat.Main;
import dev.niro.valorantcheat.gui.key.KeyGuiController;
import dev.niro.valorantcheat.gui.weapons.WeaponGuiController;
import dev.niro.valorantcheat.utils.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class GuiFrame extends Application {

	public static Stage mainStage;
	
	double xOffset;
	double yOffset;
	
	GuiController controller;
	
	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		
		stage.setTitle("VALORANT Cheat by NiroDev");
		
		FXMLLoader fxmlLoader = new FXMLLoader(); 
		Parent main = fxmlLoader.load(getClass().getResource("/resources/gui.fxml").openStream());
		controller = fxmlLoader.getController();
		controller.setGui(this);
		stage.setScene(new Scene(main));
				
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(new Image("/resources/icon.png"));
		
		main.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
		main.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Logger.log("Program exit.");
		    	Platform.exit();
		    	System.exit(0);
		    }
		});
		
		stage.show();
		controller.reloadHeight();
		stage.centerOnScreen();
		
		Logger.log("Gui started!");
	}
	
	Stage weaponStage;
	public void weaponGui() throws IOException {
		if(weaponStage != null)
			return;
		FXMLLoader fxmlLoader = new FXMLLoader(); 
		Parent root = fxmlLoader.load(Main.class.getResource("/resources/weaponsGui.fxml").openStream());
		WeaponGuiController controller = fxmlLoader.getController();
		controller.setGui(this);
		weaponStage = new Stage();
		weaponStage.getIcons().add(new Image("/resources/icon.png"));
		weaponStage.setTitle("Weapon Selection");
        weaponStage.setScene(new Scene(root));
        weaponStage.setResizable(false);
        weaponStage.show();
        weaponStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	weaponFinished();
            }
        });
	}
	
	public void weaponFinished() {
		if(weaponStage == null)
			return;
		
		weaponStage.close();
		weaponStage = null;
		controller.reloadGui();
	}
	
	Stage keyStage;
	public void keyGui() throws IOException {
		if(keyStage != null) 
			weaponFinished();
		FXMLLoader fxmlLoader = new FXMLLoader(); 
		Parent root = fxmlLoader.load(Main.class.getResource("/resources/keySettingsGui.fxml").openStream());
		KeyGuiController controller = fxmlLoader.getController();
		controller.setGui(this);
		keyStage = new Stage();
		keyStage.getIcons().add(new Image("/resources/icon.png"));
		keyStage.setTitle("Key Settings");
		keyStage.setScene(new Scene(root));
        keyStage.show();
        keyStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	if(weaponStage == null)
        			return;
        		
            	keyStage.close();
            	keyStage = null;
            }
        });
	}
	
}
