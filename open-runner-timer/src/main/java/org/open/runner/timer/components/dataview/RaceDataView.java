package org.open.runner.timer.components.dataview;

import java.io.IOException;
import java.time.LocalDateTime;

import org.open.runner.timer.api.model.EventType;
import org.open.runner.timer.model.Participant;
import org.open.runner.timer.model.Race;
import org.open.runner.timer.model.RaceType;

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

public class RaceDataView extends VBox {

	@FXML
	private TableView<Race> view;

	private ObservableList<Race> data = FXCollections.observableArrayList();

	public RaceDataView() {

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

	private TableColumn<Race, String> buildColumn(final String header, final String property, final int width) {

		TableColumn<Race, String> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<Race, String>(property));
		return col;

	}

	private TableColumn<Race, LocalDateTime> buildLocalDateTimeColumn(final String header, final String property,
			final int width) {

		TableColumn<Race, LocalDateTime> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<Race, LocalDateTime>(property));
		col.setCellFactory(new Callback<TableColumn<Race, LocalDateTime>, TableCell<Race, LocalDateTime>>() {

			@Override
			public TableCell<Race, LocalDateTime> call(TableColumn<Race, LocalDateTime> param) {
				return new LocalDateTimeCell<>("dd-MM-yyyy HH:mm:ss");
			}
		});

		return col;

	}

	private TableColumn<Race, RaceType> buildEventTypeColumn(final String header, final String property,
			final int width) {

		TableColumn<Race, RaceType> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<Race, RaceType>(property));
		col.setCellFactory(new Callback<TableColumn<Race, RaceType>, TableCell<Race, RaceType>>() {

			@Override
			public TableCell<Race, RaceType> call(TableColumn<Race, RaceType> param) {
				return new EventTypeCell<>();
			}
		});

		return col;

	}

	private void initialiseTableView() {

		view.getColumns().addAll(buildColumn("Race ID", "raceID", 150), buildColumn("Race Name", "raceName", 150),
				buildEventTypeColumn("Race Type", "raceType", 150),
				buildLocalDateTimeColumn("Race Date", "raceDate", 150));

	}

	public void updateData(Race event) {
		data.add(event);
		view.refresh();
	}

	public class EventTypeCell<T> extends TableCell<T, RaceType> {

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
		protected void updateItem(RaceType item, boolean empty) {
			super.updateItem(item, empty);

			if (empty || item == null) {
				setText(null);
			} else {
				setText(item.getDescription());
			}
		}
	}
}
