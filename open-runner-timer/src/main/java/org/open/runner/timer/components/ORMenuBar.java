package org.open.runner.timer.components;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class ORMenuBar extends VBox {

	@FXML
	public ORToolBar toolBar;

	@FXML
	private MenuItem mnuFileOpen;

	@FXML
	private MenuItem mnuFileExit;

	@FXML
	private MenuItem mnuAdminCompany;
	@FXML
	private MenuItem mnuAdminContract;

	@FXML
	private MenuItem mnuAdminProject;

	@FXML
	private MenuItem mnuAdminInvoice;

	public ORMenuBar() {
		final FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ORMenuBar.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		// fxmlLoader.setLocation(this.getClass().getResource("ORMenuBar.fxml"));
		try {
			fxmlLoader.load();
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	public void addCompanyAction(final EventHandler<ActionEvent> actionListener) {
		this.mnuAdminCompany.setOnAction(actionListener);
	}

	public void addContractAction(final EventHandler<ActionEvent> actionListener) {
		this.mnuAdminContract.setOnAction(actionListener);
	}

	public void addProjectAction(final EventHandler<ActionEvent> actionListener) {
		this.mnuAdminProject.setOnAction(actionListener);
	}

	public void addInvoiceAction(EventHandler<ActionEvent> actionListener) {
		this.mnuAdminInvoice.setOnAction(actionListener);

	}

	public void initialize() {
		mnuFileExit.setOnAction(e -> {
			Platform.exit();
		});
	}

}
