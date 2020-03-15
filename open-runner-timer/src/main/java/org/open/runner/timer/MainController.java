package org.open.runner.timer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.open.runner.timer.api.RFIDDataSource;
import org.open.runner.timer.api.exceptions.DataSourceException;
import org.open.runner.timer.api.listener.RFIDEventListener;
import org.open.runner.timer.api.model.EventType;
import org.open.runner.timer.api.model.RFIDAntennaEvent;
import org.open.runner.timer.api.model.RFIDAntennaEventType;
import org.open.runner.timer.api.model.RFIDEvent;
import org.open.runner.timer.api.model.RFIDTagData;
import org.open.runner.timer.api.model.TagEvent;
import org.open.runner.timer.components.ORAntennaBar;
import org.open.runner.timer.components.ORToolBar;
import org.open.runner.timer.components.dataview.RFIDEventsDataView;
import org.open.runner.timer.components.dataview.RFIDTagDataView;
import org.open.runner.timer.components.dataview.RaceDataView;
import org.open.runner.timer.components.dataview.RaceResultsDataView;
import org.open.runner.timer.config.Configuration;
import org.open.runner.timer.datasource.zebrafx.ZebraFX7500DataSource;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController extends VBox {
	@FXML
	HBox commandBar;
	@FXML
	public ORToolBar toolBar;

	@FXML
	public ORAntennaBar antennaBar;

	@FXML
	public AnchorPane content;

	/**
	 * Data display for RFID device events
	 */
	private RFIDEventsDataView events;
	private RFIDTagDataView tags;
	private RaceDataView races;
	private RaceResultsDataView raceResults;
	

	private RFIDDataSource dataSource;

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

	public void shutdown() {

		System.out.println("Shutdown Called");
		try {
			dataSource.disconnect();
			dataSource.logout();
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {

		events = new RFIDEventsDataView();
		tags = new RFIDTagDataView();
		races = new RaceDataView();

		raceResults = new RaceResultsDataView();

		// antennaBar.
		// commandBar.setHgrow(antennaBar, Priority.ALWAYS);

		events.prefWidthProperty().bind(content.widthProperty());
		events.prefHeightProperty().bind(content.heightProperty());

		tags.prefWidthProperty().bind(content.widthProperty());
		tags.prefHeightProperty().bind(content.heightProperty());


		races.prefWidthProperty().bind(content.widthProperty());
		races.prefHeightProperty().bind(content.heightProperty());
		
		raceResults.prefWidthProperty().bind(content.widthProperty());
		raceResults.prefHeightProperty().bind(content.heightProperty());

		dataSource = new ZebraFX7500DataSource();

		toolBar.addEventsAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				toggleEventDisplay();
			}
		});

		toolBar.addTagsAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				toggleTagDisplay();
			}
		});
	
		
		toolBar.addRaceAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				toggleRacesDisplay();
			}
		});
		
		toolBar.addRaceResultsAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				toggleRaceResultsDisplay();
			}
		});

		toolBar.addConnectAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					InetAddress host = InetAddress.getByName("192.168.200.134");
					if (dataSource.connect(host)) {

						toolBar.updateConnectionStatus(true);
						
						if (dataSource.login(host, "admin", "change")) {
							dataSource.synchroniseDateTime(LocalDateTime.now());
							dataSource.logout();
						}

						updateAntennaDisplay();

						dataSource.addEventListener(new RFIDEventListener() {
							@Override
							public void processStatusEvent(RFIDEvent eventType) {

								if (EventType.DISCONNECTION_EVENT.equals(eventType.getEventType())) {
									try {
										dataSource.disconnect();
										dataSource.reconnect();
									} catch (DataSourceException e) {
										e.printStackTrace();
									}
								} else if (EventType.ANTENNA_EVENT.equals(eventType.getEventType())) {

									final RFIDAntennaEvent antEvent = RFIDAntennaEvent.class.cast(eventType);

									antennaBar.setAntennae(antEvent.getAntennaID(), antEvent.getAntennaEventType()
											.equals(RFIDAntennaEventType.ANTENNA_CONNECTED));

								}

								events.updateData(eventType);

							}

							@Override
							public void processTagEvent(TagEvent tagEvent, RFIDTagData tagData) {

								tags.updateData(tagEvent, tagData);
							}
						});

						dataSource.registerNotifyEventType(EventType.ANTENNA_EVENT);
						dataSource.registerNotifyEventType(EventType.ACCESS_START_EVENT);
						dataSource.registerNotifyEventType(EventType.ACCESS_STOP_EVENT);
						dataSource.registerNotifyEventType(EventType.READER_EXCEPTION_EVENT);
						dataSource.registerNotifyEventType(EventType.TEMPERATURE_ALARM_EVENT);
						dataSource.registerNotifyEventType(EventType.INVENTORY_START_EVENT);
						dataSource.registerNotifyEventType(EventType.INVENTORY_STOP_EVENT);
						dataSource.registerNotifyEventType(EventType.DEBUG_INFO_EVENT);
						dataSource.registerNotifyEventType(EventType.DISCONNECTION_EVENT);

						dataSource.start();

					}
				} catch (DataSourceException |

						UnknownHostException e) {
					System.out.println(e.getMessage());
				}

			}

			private void updateAntennaDisplay() throws DataSourceException {
				for (int index = 1; index <= dataSource.getSupportedAntennae(); index++) {
					antennaBar.setAntennae(index, dataSource.isAntennaConnected(index));
				}
			}
		});

		// Default display
		toggleTagDisplay();

	}

	private void toggleEventDisplay() {
		configureView();
		content.getChildren().add(events);
	}
	
	private void toggleRacesDisplay() {
		configureView();
		content.getChildren().add(races);
	}

	private void toggleRaceResultsDisplay() {
		configureView();
		content.getChildren().add(raceResults);
	}

	private void toggleTagDisplay() {
		configureView();
		content.getChildren().add(tags);
	}
	
	private void configureView() {
		content.getChildren().clear();
	}
}