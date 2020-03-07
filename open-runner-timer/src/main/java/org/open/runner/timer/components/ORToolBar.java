package org.open.runner.timer.components;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;


public class ORToolBar extends VBox {
	@FXML
	private ToolBar toolBar;

	@FXML
	private Button connect;
	

	public ORToolBar() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ORToolBar.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		connect.setDisable(true);
	
	}

	public void addConnectAction(final EventHandler<ActionEvent> actionListener) {
		this.connect.setOnAction(actionListener);
		this.connect.setDisable(false);
	}

}
