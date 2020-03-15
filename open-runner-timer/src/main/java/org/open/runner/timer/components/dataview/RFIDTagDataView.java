package org.open.runner.timer.components.dataview;

import java.io.IOException;
import java.time.LocalDateTime;

import org.open.runner.timer.api.model.RFIDTag;
import org.open.runner.timer.api.model.RFIDTagData;
import org.open.runner.timer.api.model.TagEvent;

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

public class RFIDTagDataView extends VBox {

	@FXML
	private TableView<RFIDTag> view;

	private ObservableList<RFIDTag> data = FXCollections.observableArrayList();

	public RFIDTagDataView() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DataView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		// fxmlLoader.setControllerFactory(context::getBean);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		// TableColumn<? extends Object, ? extends Object> [] list= new
		// DataProviderImpl<UIOverview, String>().getColumns();

		// view.getColumns().addAll(list);

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
//		data.addAll(companyRepository.findAll());
		view.setItems(data);
		view.refresh();
	}

	private TableColumn<RFIDTag, String> buildColumn(final String header, final String property, final int width) {

		TableColumn<RFIDTag, String> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<RFIDTag, String>(property));
		return col;

	}

	private TableColumn<RFIDTag, LocalDateTime> buildLocalDateTimeColumn(final String header, final String property,
			final int width) {

		TableColumn<RFIDTag, LocalDateTime> col = new TableColumn<>(header);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<RFIDTag, LocalDateTime>(property));
		col.setCellFactory(new Callback<TableColumn<RFIDTag, LocalDateTime>, TableCell<RFIDTag, LocalDateTime>>() {

			@Override
			public TableCell<RFIDTag, LocalDateTime> call(TableColumn<RFIDTag, LocalDateTime> param) {
				return new LocalDateTimeCell<>("dd-MM-yyyy HH:mm:ss");
			}
		});

		return col;

	}

	private void initialiseTableView() {

		view.getColumns().addAll(buildColumn("EPC Id", "tagId", 200), buildColumn("Antenna Id", "antennaId", 100),
				buildLocalDateTimeColumn("First Seen", "firstSeenTime", 150),
				buildLocalDateTimeColumn("Last Seen", "lastSeenTime", 150));

		/*
		 * TableColumn<RFIDEvent, String> idCol = new TableColumn<>("Id");
		 * idCol.setMinWidth(100);
		 * 
		 * TableColumn<RFIDEvent, String> nameCol = new TableColumn<>("Name");
		 * nameCol.setMinWidth(200); nameCol.setCellValueFactory(new
		 * PropertyValueFactory<RFIDEvent, String>("name"));
		 * 
		 * TableColumn<RFIDEvent, String> descCol = new TableColumn<>("VAT No");
		 * descCol.setMinWidth(200);
		 */

	}

	private String replaceSpecialChars(final String str) {
		String result = "";
		if (null != str) {
			result = str.replaceAll("[\r\n\t]", "");
		}

		return result;
	}

	public void updateData(TagEvent tagEvent, RFIDTagData tagData) {

		data.stream().filter(t -> t.getTagId().equals(tagData.getTagID())).findFirst().ifPresentOrElse((ud) -> {

	//		ud.setFirstSeenTime(tagData.getFirstSeenTime());
			ud.setLastSeenTime(tagData.getLastSeenTime());

		}, () -> {
			data.add(new RFIDTag(tagData.getTagID(), tagData.getAntennaId(), tagData.getFirstSeenTime(),
					tagData.getLastSeenTime(), tagData.getTagSeenCount()));
		});

		if (view != null) {
			view.refresh();
		}

	}

}
