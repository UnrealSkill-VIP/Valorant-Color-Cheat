package dev.niro.valorantcheat.gui.key;

import dev.niro.valorantcheat.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class KeyTableItem {
	public KeyTableItem(KeyGuiController guic, int id, String name, boolean holdb) {
		this.id = id;
		this.name = name;
		this.hold = new CheckBox();
		this.hold.setSelected(holdb);
		this.hold.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	Main.keys.replace(id, hold.isSelected());
		    }
		});
		this.delete = new Button("Delete");
		this.delete.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	Main.keys.remove(id);
		    	guic.updateData();
		    }
		});
	}
	
	private Integer id;
	private String name;
	private CheckBox hold;
	private Button delete;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CheckBox getHold() {
		return hold;
	}
	public void setHold(CheckBox hold) {
		this.hold = hold;
	}
	public Button getDelete() {
		return delete;
	}
	public void setDelete(Button delete) {
		this.delete = delete;
	}
}