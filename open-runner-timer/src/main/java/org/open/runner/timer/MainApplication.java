package org.open.runner.timer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

	private MainController controller;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		controller = new MainController();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(controller);
		
		scene.getStylesheets().add("app.css");

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		controller.shutdown();		
	}
}