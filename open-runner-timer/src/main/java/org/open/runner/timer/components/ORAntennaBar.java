package org.open.runner.timer.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ORAntennaBar extends HBox {

	@FXML
	private ImageView antenna1;

	@FXML
	private ImageView antenna2;

	@FXML
	private ImageView antenna3;

	@FXML
	private ImageView antenna4;

	private Image disconnectedImage;

	private Image connectedImage;

	public ORAntennaBar() {
		final FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ORAntennaBar.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	public void initialize() {
		connectedImage = new Image(getClass().getResourceAsStream("/images/ant-con.png"));
		disconnectedImage = new Image(getClass().getResourceAsStream("/images/ant-discon.png"));

		antenna1.setImage(disconnectedImage);
		antenna2.setImage(disconnectedImage);
		antenna3.setImage(disconnectedImage);
		antenna4.setImage(disconnectedImage);
	}

	public void setAntennae(int index, boolean b) {
		final Image img = b == true ? connectedImage : disconnectedImage;
		if (index >= 1 && index <= 4) {

			switch (index) {
			case 1:
				antenna1.setImage(img);
				break;
			case 2:
				antenna2.setImage(img);
				break;
			case 3:
				antenna3.setImage(img);
				break;
			case 4:
				antenna4.setImage(img);
				break;
			}

		}

	}

}
