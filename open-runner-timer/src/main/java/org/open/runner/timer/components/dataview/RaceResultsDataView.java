package org.open.runner.timer.components.dataview;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Hashtable;

import org.open.runner.timer.api.model.EventType;
import org.open.runner.timer.api.model.RFIDEvent;
import org.open.runner.timer.api.model.RFIDTag;
import org.open.runner.timer.model.Participant;

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

public class RaceResultsDataView extends VBox {

	@FXML
	private TableView<Participant> view;

	private ObservableList<Participant> data = FXCollections.observableArrayList();

	public RaceResultsDataView() {

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

		initialiseTableView();

		view.setItems(data);

	}

	public void refresh() {
		data.clear();
		view.setItems(data);
		view.refresh();
	}

	private TableColumn<Participant, String> buildColumn(final String header, final String property, final int width) {

		TableColumn<Participant, String> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<Participant, String>(property));
		return col;

	}

	private TableColumn<Participant, LocalDateTime> buildLocalDateTimeColumn(final String header, final String property,
			final int width) {

		TableColumn<Participant, LocalDateTime> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<Participant, LocalDateTime>(property));
		col.setCellFactory(new Callback<TableColumn<Participant, LocalDateTime>, TableCell<Participant, LocalDateTime>>() {

			@Override
			public TableCell<Participant, LocalDateTime> call(TableColumn<Participant, LocalDateTime> param) {
				return new LocalDateTimeCell<>("dd-MM-yyyy HH:mm:ss");
			}
		});

		return col;

	}

	private TableColumn<Participant, EventType> buildEventTypeColumn(final String header, final String property,
			final int width) {

		TableColumn<Participant, EventType> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<Participant, EventType>(property));
		col.setCellFactory(new Callback<TableColumn<Participant, EventType>, TableCell<Participant, EventType>>() {

			@Override
			public TableCell<Participant, EventType> call(TableColumn<Participant, EventType> param) {
				return new EventTypeCell<>();
			}
		});

		return col;

	}

	private void initialiseTableView() {

		view.getColumns().addAll(buildColumn("Tag ID", "tagID", 150), buildColumn("Name", "name", 150),
				buildLocalDateTimeColumn("Start Time", "chipStartTime", 150),				
				buildColumn("Start Position", "startPosition", 100),
				buildLocalDateTimeColumn("End Time", "chipEndTime", 150),
				buildColumn("Finish Position", "endPosition", 100));

	}

	public void updateData(Participant event) {
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
