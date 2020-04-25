package dev.niro.valorantcheat.gui;


import dev.niro.valorantcheat.utils.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class GuiFrame extends Application {

	double xOffset;
	double yOffset;
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("VALORANT Cheat by NiroDev");
		
		Parent main = FXMLLoader.load(getClass().getResource("/resources/gui.fxml"));
		stage.setScene(new Scene(main));
				
		stage.setHeight(230);
		stage.initStyle(StageStyle.UNDECORATED);
		
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

}
