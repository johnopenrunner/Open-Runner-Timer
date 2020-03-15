package org.open.runner.timer.components;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

public class ORToolBar extends VBox {
	@FXML
	private ToolBar toolBar;

	@FXML
	private Button events;

	@FXML
	private Button tags;

	@FXML
	private Button races;

	@FXML
	private Button raceResults;

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
	}

	@FXML
	public void initialize() {
		connect.setTooltip(new Tooltip("Connect to RFID"));
		tags.setTooltip(new Tooltip("RFID Tag Data"));
		events.setTooltip(new Tooltip("RFID Events"));
		races.setTooltip(new Tooltip("Races"));
		raceResults.setTooltip(new Tooltip("Race Results"));

		tags.getStyleClass().add("tags");
		events.getStyleClass().add("events");
		races.getStyleClass().add("races");
		raceResults.getStyleClass().add("raceResults");
		
		updateConnectionStatus(false);

	}

	public void addConnectAction(final EventHandler<ActionEvent> eventHandler) {
		this.connect.setOnAction(eventHandler);
		this.connect.setDisable(false);
	}

	public void addEventsAction(EventHandler<ActionEvent> eventHandler) {
		this.events.setOnAction(eventHandler);
		;
		this.events.setDisable(false);

	}

	public void addTagsAction(EventHandler<ActionEvent> eventHandler) {
		this.tags.setOnAction(eventHandler);
		this.tags.setDisable(false);

	}

	public void addRaceAction(EventHandler<ActionEvent> eventHandler) {
		this.races.setOnAction(eventHandler);
		this.races.setDisable(false);
	}

	public void addRaceResultsAction(EventHandler<ActionEvent> eventHandler) {
		this.raceResults.setOnAction(eventHandler);
		this.raceResults.setDisable(false);
	}

	public void updateConnectionStatus(boolean b) {
		String connectionStyle = b ? "connected" : "disconnected";
		connect.setStyle("");
		connect.getStyleClass().clear();
		connect.getStyleClass().add(connectionStyle);

	}

}
