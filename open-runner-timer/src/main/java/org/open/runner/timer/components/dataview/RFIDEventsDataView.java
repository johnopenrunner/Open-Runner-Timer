package org.open.runner.timer.components.dataview;

import java.io.IOException;
import java.util.Hashtable;

import org.open.runner.timer.api.model.EventType;
import org.open.runner.timer.api.model.RFIDEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class RFIDEventsDataView extends VBox {

	@FXML
	private TableView<RFIDEvent> view;

	private ObservableList<RFIDEvent> data = FXCollections.observableArrayList();
	public Hashtable tagStore = null;

	public RFIDEventsDataView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DataView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	public void initialize() {
		/*
		 * setVgrow(this, Priority.ALWAYS); setFillWidth(true);
		 */

		initialiseTableView();

		// data.add(new Company("name", "desc", ItemStatus.NEW));
		// data.add(new DataItem("name2", "desc2", ItemStatus.ACTIVE));

		view.setItems(data);
		/*
		 * view.getItems().add(new DataItem("name", "desc", ItemStatus.NEW));
		 * view.getItems().add(new DataItem("name2", "desc2", ItemStatus.ACTIVE));
		 */

	}

	public void refresh() {
		data.clear();
		view.setItems(data);
		view.refresh();
	}

	private TableColumn<RFIDEvent, String> buildColumn(final String header, final String property, final int width) {

		TableColumn<RFIDEvent, String> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<RFIDEvent, String>(property));
		return col;

	}

	private TableColumn<RFIDEvent, EventType> buildEventTypeColumn(final String header,
			final String property, final int width) {

		TableColumn<RFIDEvent, EventType> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<RFIDEvent, EventType>(property));
		col.setCellFactory(new Callback<TableColumn<RFIDEvent, EventType>, TableCell<RFIDEvent, EventType>>() {

			@Override
			public TableCell<RFIDEvent, EventType> call(TableColumn<RFIDEvent, EventType> param) {
				return new EventTypeCell<>();
			}
		});

		return col;

	}

	private void initialiseTableView() {

		view.getColumns().addAll(buildEventTypeColumn("Event Type", "eventType", 100));

	}

	public void updateData(RFIDEvent event) {
		data.add(event);
		view.refresh();
	}

	public class EventTypeCell<T> extends TableCell<T, EventType> {

		// private final ImageView image;

		public EventTypeCell() {
			// add ImageView as graphic to display it in addition
			// to the text in the cell
			/*
			 * image = new ImageView(); image.setFitWidth(64); image.setFitHeight(64);
			 * image.setPreserveRatio(true);
			 */

			// setGraphic(image);
			// setMinHeight(70);
		}

		@Override
		protected void updateItem(EventType item, boolean empty) {
			super.updateItem(item, empty);

			if (empty || item == null) {
				// set back to look of empty cell
				setText(null);
				// image.setImage(null);
			} else {
				// set image and text for non-empty cell
				// image.setImage(item.getEmoji());
				setText(item.getDescription());
			}
		}
	}
}
