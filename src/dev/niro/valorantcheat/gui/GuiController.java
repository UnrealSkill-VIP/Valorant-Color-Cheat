package dev.niro.valorantcheat.gui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


import dev.niro.valorantcheat.Main;
import dev.niro.valorantcheat.enums.AimbotMode;
import dev.niro.valorantcheat.utils.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
	Rectangle seperator4;
	
	@FXML
	CheckBox debug;
	@FXML
	CheckBox aimbot;
	@FXML
	CheckBox triggerbot;
	@FXML
	CheckBox sniper;
	@FXML
	CheckBox esp;
	@FXML
	CheckBox fov;
	
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
	TextField antiShake;
	@FXML
	TextField prefire;
	@FXML
	TextField delay;
	
	@FXML
	Button exitButton;
	@FXML
	Button loadConfig;
	@FXML
	Button keySettings;
	@FXML
	Button help;
	
	@FXML 
	Text name;
	@FXML 
	Text credit;
	
	@FXML
	ComboBox<String> aimMode;
	
	GuiFrame gui;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setText(name.getText() + " " + Main.VERSION);
				
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
		seperator4.widthProperty().bind(content3.widthProperty().subtract(20));
		
		for(AimbotMode am : AimbotMode.values()) {
			aimMode.getItems().add(am.name());
		}
		
		aimMode.setOnAction(e -> {
		    Main.aimMode = AimbotMode.valueOf(aimMode.getValue());
		});
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	GuiFrame.mainStage.close();
		    	Logger.log("Program exit.");
		    	Platform.exit();
				System.exit(0);
		    }
		});
		loadConfig.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	try {
					gui.weaponGui();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		});
		name.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://niro.dev"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		credit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://niro.dev"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		help.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("http://raboninco.com/OX7G"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		keySettings.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					getGui().keyGui();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		esp.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Main.esp = ((CheckBox)e.getSource()).isSelected();
		    }
		});
		fov.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Main.fovRect = ((CheckBox)e.getSource()).isSelected();
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
			else if(observable == antiShake.textProperty()) 
				tf = antiShake;
			else if(observable == prefire.textProperty()) 
				tf = prefire;
			else if(observable == delay.textProperty()) 
				tf = delay;
			
			tf.setText(newValue.replaceAll("[^0-9.]", "") + (tf == fovH || tf == fovW ? "%" : ""));
			
			double value = 0;
			try {
				if(newValue.length() > 0)
					value = Double.valueOf(newValue.replaceAll("[^0-9.]", ""));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
						
			if(tf == fovH) 
				Main.fovHeight = value / 100.0;
			else if(tf == fovW) 
				Main.fovWidth = value / 100.0;
			else if(tf == maxTPS) 
				Main.maxTps = (int) value;
			else if(tf == moveSpeed) 
				Main.moveSpeed = value / 100.0;
			else if(tf == recoilSpeed) 
				Main.recoilSpeed = value / 1000.0;
			else if(tf == maxRecoil) 
				Main.maxRecoil = value;
			else if(tf == antiShake) 
				Main.antiShake = value / 1000.0;
			else if(tf == prefire) 
				Main.triggerPrefire = value;
			else if(tf == delay) 
				Main.triggerDelay = (int) value;
			
			Main.updateSettings();
		};
		
		fovW.textProperty().addListener(textFieldListener);		
		fovH.textProperty().addListener(textFieldListener);		
		maxTPS.textProperty().addListener(textFieldListener);
		moveSpeed.textProperty().addListener(textFieldListener);
		recoilSpeed.textProperty().addListener(textFieldListener);
		maxRecoil.textProperty().addListener(textFieldListener);
		antiShake.textProperty().addListener(textFieldListener);
		delay.textProperty().addListener(textFieldListener);
		prefire.textProperty().addListener(textFieldListener);
		
		reloadGui();
	}
	
	public void reloadGui() {
		debug.setSelected(Main.debug);
		aimbot.setSelected(Main.aimbot);
		triggerbot.setSelected(Main.triggerbot);
		sniper.setSelected(Main.sniper);
		esp.setSelected(Main.esp);
		fov.setSelected(Main.fovRect);
		
		fovW.setText(String.valueOf(Main.fovWidth * 100) + "%");
		fovH.setText(String.valueOf(Main.fovHeight * 100) + "%");
		maxTPS.setText(String.valueOf(Main.maxTps));
		moveSpeed.setText(String.valueOf(Main.moveSpeed * 100));
		recoilSpeed.setText(String.valueOf(Main.recoilSpeed * 1000));
		maxRecoil.setText(String.valueOf(Main.maxRecoil));
		antiShake.setText(String.valueOf(Main.antiShake * 1000));
		prefire.setText(String.valueOf(Main.triggerPrefire));
		delay.setText(String.valueOf(Main.triggerDelay));
		
		aimMode.setValue(Main.aimMode.name());
	}
	
	public void reloadHeight() {			
		if(content1.getHeight() >= content2.getHeight() && content1.getHeight() >= content3.getHeight())
			GuiFrame.mainStage.setHeight(content1.getHeight());
		else if(content2.getHeight() >= content1.getHeight() && content2.getHeight() >= content3.getHeight())
			GuiFrame.mainStage.setHeight(content2.getHeight());
		else if(content3.getHeight() >= content2.getHeight() && content3.getHeight() >= content1.getHeight())
			GuiFrame.mainStage.setHeight(content3.getHeight());
	}

	public GuiFrame getGui() {
		return gui;
	}

	public void setGui(GuiFrame gui) {
		this.gui = gui;
	}    
}