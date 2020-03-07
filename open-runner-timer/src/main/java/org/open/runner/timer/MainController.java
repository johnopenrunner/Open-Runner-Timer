package org.open.runner.timer;

import java.io.IOException;

import org.open.runner.timer.components.ORToolBar;
import org.open.runner.timer.config.Configuration;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController extends VBox {
	@FXML
	public ORToolBar toolBar;

	@FXML
	public AnchorPane content;

		
	private Configuration config;
		

	public MainController() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainApp.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	public void initialize() {

	

	}

}