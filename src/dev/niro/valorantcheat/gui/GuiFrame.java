package dev.niro.valorantcheat.gui;


import java.io.IOException;

import dev.niro.valorantcheat.Main;
import dev.niro.valorantcheat.gui.weapons.WeaponGuiController;
import dev.niro.valorantcheat.utils.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
				
		stage.setHeight(220);
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
		
		stage.show();
		stage.centerOnScreen();
		
		Logger.log("Program started!");
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
	}
	
	public void weaponFinished() {
		if(weaponStage == null)
			return;
		
		weaponStage.close();
		weaponStage = null;
		controller.reloadGui();
	}

}
