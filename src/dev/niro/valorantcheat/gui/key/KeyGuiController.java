package dev.niro.valorantcheat.gui.key;

import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.jnativehook.keyboard.NativeKeyEvent;

import dev.niro.valorantcheat.Main;
import dev.niro.valorantcheat.gui.GuiFrame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

public class KeyGuiController implements Initializable {

	@FXML
	TableView<KeyTableItem> table;
	@FXML
	TableColumn<KeyTableItem, Integer> keycode;
	@FXML
	TableColumn<KeyTableItem, CheckBox> hold;
	@FXML
	TableColumn<KeyTableItem, String> keyname;
	@FXML
	TableColumn<KeyTableItem, Button> delete;
	
	GuiFrame gui;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		KeyGuiController controller = this;
		
		table.setEditable(true);
		keycode.setEditable(true);
		keyname.setEditable(false);
		delete.setEditable(true);
		
		keycode.setCellValueFactory(new PropertyValueFactory<KeyTableItem, Integer>("id"));
		keyname.setCellValueFactory(new PropertyValueFactory<KeyTableItem, String>("name"));
		hold.setCellValueFactory(new PropertyValueFactory<KeyTableItem, CheckBox>("hold"));
		delete.setCellValueFactory(new PropertyValueFactory<KeyTableItem, Button>("delete"));
		
		hold.setStyle("-fx-alignment: CENTER;");
		delete.setStyle("-fx-alignment: CENTER;");
				
		Callback<TableColumn<KeyTableItem, Integer>, TableCell<KeyTableItem, Integer>> cellFactory =
	            new Callback<TableColumn<KeyTableItem, Integer>, TableCell<KeyTableItem, Integer>>() {
	                public TableCell<KeyTableItem, Integer> call(TableColumn<KeyTableItem, Integer> p) {
	                    return new EditingCell(controller);
	                }
	            };
		keycode.setCellFactory(cellFactory);
	            		
		ObservableList<KeyTableItem> data = FXCollections.observableArrayList();
		for(Entry<Integer, Boolean> entry : Main.keys.entrySet()) {
			data.add(new KeyTableItem(this, entry.getKey(), entry.getKey() <= 5 ? "Mouse " + entry.getKey() : NativeKeyEvent.getKeyText(entry.getKey()), entry.getValue()));
		}
		data.add(new KeyTableItem(this, -1, "New Key", true));
		table.setItems(data);
	}
	
	public void changeKeyCode(int oldValue, int newValue) {
    	boolean b = Main.keys.containsKey(oldValue) ? Main.keys.get(oldValue) : true;
    	for(Entry<Integer, Boolean> entry : ((HashMap<Integer, Boolean>)Main.keys.clone()).entrySet()) {
    		if(entry.getKey().intValue() == oldValue) 
    			Main.keys.remove(entry.getKey());
    	}
		Main.keys.put(newValue, b);
		
		updateData();
	}
	
	public void updateData() {
		table.getItems().clear();
		for(Entry<Integer, Boolean> entry : Main.keys.entrySet()) {
			table.getItems().add(new KeyTableItem(this, entry.getKey(), entry.getKey() <= 5 ? "Mouse " + entry.getKey() : NativeKeyEvent.getKeyText(entry.getKey()), entry.getValue()));
		}
		table.getItems().add(new KeyTableItem(this, -1, "New Key", true));
	}
		
	public GuiFrame getGui() {
		return gui;
	}

	public void setGui(GuiFrame gui) {
		this.gui = gui;
	}   
}

class EditingCell extends TableCell<KeyTableItem, Integer> {

    private TextField textField;
    KeyGuiController controller;
    int oldValue;
    
    public EditingCell(KeyGuiController controller) {
    	this.controller = controller;
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }

        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(textField);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(String.valueOf(getString()));
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(String.valueOf(getString()));
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }
    
    private void createTextField() {
        textField = new TextField(String.valueOf(getString()));
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
            	try {
            		commitEdit(Integer.valueOf(textField.getText()));
            		controller.changeKeyCode(oldValue, Integer.valueOf(textField.getText()));
				} catch (Exception e) {}                
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
        this.oldValue = Integer.valueOf(textField.getText());
    }

    private Integer getString() {
        return getItem() == null ? -1 : getItem();
    }
}