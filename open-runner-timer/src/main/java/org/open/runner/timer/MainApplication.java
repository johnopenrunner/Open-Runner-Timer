package org.open.runner.timer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApplication extends Application {

	public static void main(final String [] args) {
		Application.launch(args);		
	}
	
	@Override
	public void init() throws Exception {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {	
		primaryStage.setScene(new Scene(new MainController()));
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {	
	}
}