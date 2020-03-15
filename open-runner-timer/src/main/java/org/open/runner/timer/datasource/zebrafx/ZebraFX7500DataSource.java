package org.open.runner.timer.datasource.zebrafx;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.open.runner.timer.api.RFIDDataSource;
import org.open.runner.timer.api.exceptions.DataSourceException;
import org.open.runner.timer.api.listener.RFIDEventListener;
import org.open.runner.timer.api.model.EventType;
import org.open.runner.timer.api.model.RFIDAntennaEvent;
import org.open.runner.timer.api.model.RFIDAntennaEventType;
import org.open.runner.timer.api.model.RFIDEvent;
import org.open.runner.timer.api.model.RFIDTagData;
import org.open.runner.timer.api.model.TagEvent;
import org.open.runner.timer.api.model.TagField;

import com.mot.rfid.api3.ANTENNA_EVENT_TYPE;
import com.mot.rfid.api3.AntennaInfo;
import com.mot.rfid.api3.Events.StatusEventData;
import com.mot.rfid.api3.InvalidUsageException;
import com.mot.rfid.api3.LoginInfo;
import com.mot.rfid.api3.OperationFailureException;
import com.mot.rfid.api3.READER_TYPE;
import com.mot.rfid.api3.RFIDReader;
import com.mot.rfid.api3.ReaderManagement;
import com.mot.rfid.api3.RfidEventsListener;
import com.mot.rfid.api3.RfidReadEvents;
import com.mot.rfid.api3.RfidStatusEvents;
import com.mot.rfid.api3.SECURE_MODE;
import com.mot.rfid.api3.START_TRIGGER_TYPE;
import com.mot.rfid.api3.STATUS_EVENT_TYPE;
import com.mot.rfid.api3.STOP_TRIGGER_TYPE;
import com.mot.rfid.api3.SYSTEMTIME;
import com.mot.rfid.api3.TAG_EVENT;
import com.mot.rfid.api3.TAG_EVENT_REPORT_TRIGGER;
import com.mot.rfid.api3.TagData;
import com.mot.rfid.api3.TriggerInfo;

/**
 * DataSource Implementation for Zebra FX7500 RFID Reader.
 * 
 *
 */
public class ZebraFX7500DataSource implements RFIDDataSource {

	private RFIDReader reader;
	private ReaderManagement rm;
	private LoginInfo loginInfo;
	private RFIDEventListener listener;
	private Set<EventType> notifyEventTypeList = new HashSet<>();

	// Trigger Info
	private TriggerInfo triggerInfo;
	// Antenna Info
	private AntennaInfo antennaInfo = null;

	public ZebraFX7500DataSource() {
		reader = new RFIDReader();
		rm = new ReaderManagement();

		// Create Antenna Info
		antennaInfo = new AntennaInfo();

		triggerInfo = new TriggerInfo();

		/*
		 * // create the post filter postFilter = new PostFilter();
		 * 
		 * processor = new ZebraFXEventProcessor(reader, listener, notifyEventTypeList);
		 */

	}

	private void configure() {
		reader.Events.setInventoryStartEvent(true);
		reader.Events.setInventoryStopEvent(true);
		reader.Events.setAccessStartEvent(true);
		reader.Events.setAccessStopEvent(true);
		reader.Events.setAntennaEvent(true);
		reader.Events.setGPIEvent(true);
		reader.Events.setBufferFullEvent(true);
		reader.Events.setBufferFullWarningEvent(true);
		reader.Events.setReaderDisconnectEvent(true);
		reader.Events.setReaderExceptionEvent(true);
		reader.Events.setTagReadEvent(true);
		reader.Events.setAttachTagDataWithReadEvent(false);
		reader.Events.setTemperatureAlarmEvent(true);

		triggerInfo.StartTrigger.setTriggerType(START_TRIGGER_TYPE.START_TRIGGER_TYPE_IMMEDIATE);
		triggerInfo.StopTrigger.setTriggerType(STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_IMMEDIATE);

		triggerInfo.TagEventReportInfo.setReportNewTagEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
		triggerInfo.TagEventReportInfo.setNewTagEventModeratedTimeoutMilliseconds((short) 100);

		triggerInfo.TagEventReportInfo.setReportTagInvisibleEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
		triggerInfo.TagEventReportInfo.setTagInvisibleEventModeratedTimeoutMilliseconds((short) 100);

		triggerInfo.TagEventReportInfo.setReportTagBackToVisibilityEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
		triggerInfo.TagEventReportInfo.setTagBackToVisibilityModeratedTimeoutMilliseconds((short) 100);

		triggerInfo.setTagReportTrigger(1);

		// Add the event listener
		reader.Events.addEventsListener(new EventHandler(this.listener));

	}

	@Override
	public boolean connect(InetAddress hostname) throws DataSourceException {

		if (hostname == null)
			throw new IllegalArgumentException("host address is null");

		// TODO port number
		// TODO timeout

		reader.setHostName(hostname.getHostAddress());
		reader.setPort(5084);
		try {
			reader.connect();

			System.out.println("Antenna Count : " + reader.ReaderCapabilities.getNumAntennaSupported());

		} catch (InvalidUsageException | OperationFailureException e) {
			throw new DataSourceException("Unable to connect to reader", e);

		}
		return reader.isConnected();
	}

	@Override
	public boolean login(InetAddress hostname, String username, String password) throws DataSourceException {

		if (hostname == null)
			throw new IllegalArgumentException("host address is null");

		if (username == null)
			throw new IllegalArgumentException("username is null");

		if (password == null)
			throw new IllegalArgumentException("password is null");

		this.loginInfo = new LoginInfo(hostname.getHostAddress(), username, password, SECURE_MODE.HTTP, true);

		assert (rm != null);

		try {
			rm.login(this.loginInfo, READER_TYPE.FX);
		} catch (InvalidUsageException | OperationFailureException e) {
			throw new DataSourceException("Unable to connect to reader for managment operations", e);
		}

		return rm.isLoggedIn();
	}

	@Override
	public boolean disconnect() throws DataSourceException {
		if (reader != null && reader.isConnected()) {
			try {
				reader.disconnect();
			} catch (InvalidUsageException | OperationFailureException e) {
				throw new DataSourceException("Unable to disconnect from reader", e);
			}
		}
		return !reader.isConnected();
	}

	@Override
	public boolean reconnect() throws DataSourceException {
		System.out.println("Reconnecting to device");
		if (reader != null && !reader.isConnected()) {
			try {
				reader.reconnect();
			} catch (InvalidUsageException | OperationFailureException e) {
				throw new DataSourceException("Unable to reconnect to reader", e);
			}
		}
		return !reader.isConnected();

	}

	@Override
	public boolean logout() throws DataSourceException {
		if (rm != null && rm.isLoggedIn()) {
			try {
				rm.logout();
			} catch (InvalidUsageException | OperationFailureException e) {
				throw new DataSourceException("Unable to logout from reader", e);
			}
		}
		return !reader.isConnected();
	}

	@Override
	public void synchroniseDateTime(LocalDateTime dateTime) throws DataSourceException {

		assert (rm != null);

		if (rm.isLoggedIn()) {
			System.out.println("Synchronising DateTime");

			final SYSTEMTIME systemTime = new SYSTEMTIME();
			systemTime.Day = (short) dateTime.getDayOfMonth();
			systemTime.Month = (short) dateTime.getMonthValue();
			systemTime.Year = (short) dateTime.getYear();
			systemTime.Hour = (short) dateTime.getHour();
			systemTime.Minute = (short) dateTime.getMinute();
			systemTime.Second = (short) dateTime.getSecond();

			systemTime.Milliseconds = (short) 0;// dateTime.getNano();

			try {
				rm.setLocalTime(systemTime);
			} catch (InvalidUsageException | OperationFailureException e) {
				throw new DataSourceException("Unable to configure new reader system time", e);
			}

		}

	}

	@Override
	public void registerNotifyEventType(EventType eventType) {
		if (!notifyEventTypeList.contains(eventType)) {
			notifyEventTypeList.add(eventType);
		}
	}

	@Override
	public void start() throws DataSourceException {

		System.out.println("Starting....");
		AntennaInfo myAntennInfo = null;
		// Set the Antenna Info
		if (antennaInfo.getAntennaID() != null)
			myAntennInfo = antennaInfo;

		configure();

		try {
			reader.Actions.purgeTags();
			reader.Actions.Inventory.perform(null, triggerInfo, myAntennInfo);
		} catch (InvalidUsageException | OperationFailureException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stop() throws DataSourceException {
		try {
			reader.Actions.Inventory.stop();
		} catch (InvalidUsageException | OperationFailureException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setReaderProperties(Properties properties) throws DataSourceException {

	}

	@Override
	public boolean isAntennaConnected(final int antennaID) throws DataSourceException {
		boolean connected = false;
		if (antennaID >= 1 && antennaID <= getSupportedAntennae()) {

			try {
				connected = reader.Config.Antennas.getPhysicalProperties(antennaID).isConnected();
			} catch (InvalidUsageException | OperationFailureException e) {
				throw new DataSourceException("Unable get configuation for antenna " + antennaID, e);
			}
		}
		return connected;
	}

	@Override
	public void getAnntennaConfig(short antennaID) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSupportedAntennae() {
		return reader.ReaderCapabilities.getNumAntennaSupported();
	}

	@Override
	public TagField[] getTagFields() {

		return null;
	}

	@Override
	public void addEventListener(final RFIDEventListener listener) {
		this.listener = listener;
	}

	class EventHandler implements RfidEventsListener {
		private RFIDEventListener eventListener;

		public EventHandler(RFIDEventListener eventListener) {
			this.eventListener = eventListener;
		}

		@Override
		public void eventStatusNotify(RfidStatusEvents statusEvent) {
			final StatusEventData svEventData = statusEvent.StatusEventData;
			if (svEventData != null) {
				final EventType evType = convertEventType(svEventData.getStatusEventType());

				RFIDEvent evEvent = null;

				if (STATUS_EVENT_TYPE.ANTENNA_EVENT.equals(svEventData.getStatusEventType())) {
					final int anntennaID = svEventData.AntennaEventData.getAntennaID();

					evEvent = new RFIDAntennaEvent(evType,
							ANTENNA_EVENT_TYPE.ANTENNA_CONNECTED.equals(svEventData.AntennaEventData.getAntennaEvent())
									? RFIDAntennaEventType.ANTENNA_CONNECTED
									: RFIDAntennaEventType.ANTENNA_DISCONNECTED,
							anntennaID);
				} else {
					evEvent = new RFIDEvent(evType);
				}

				eventListener.processStatusEvent(evEvent);
			}
		}

		@Override
		public void eventReadNotify(RfidReadEvents readEvent) {

			TagData[] myTags = reader.Actions.getReadTags(100);
			if (myTags != null) {
				for (int index = 0; index < myTags.length; index++) {
					try {
						final TagData tag = myTags[index];
						final RFIDTagData td = new RFIDTagData();

						td.setTagID(tag.getTagID());
						td.setAntennaId(tag.getAntennaID());
						td.setTagSeenCount(tag.getTagSeenCount());

						final SYSTEMTIME tagTime = tag.getTagEventTimeStamp();

						final SYSTEMTIME firstSeenTime = tag.SeenTime.getUTCTime().getFirstSeenTimeStamp();

						final SYSTEMTIME lastSeenTime = tag.SeenTime.getUTCTime().getLastSeenTimeStamp();

						td.setFirstSeenTime(convertSystemTime(firstSeenTime));
						td.setLastSeenTime(convertSystemTime(lastSeenTime));

						eventListener.processTagEvent(convertTagEvent(tag.getTagEvent()), td);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

		}

		private LocalDateTime convertSystemTime(SYSTEMTIME time) {
			LocalDateTime tagTime = null;

			if (time != null) {
				tagTime = LocalDateTime.of(time.Year, time.Month, time.Day, time.Hour, time.Minute, time.Second,
						time.Milliseconds);

			}
			return tagTime;

		}

		private EventType convertEventType(STATUS_EVENT_TYPE type) {
			EventType evType = EventType.NOT_SUPPORTED;

			if (STATUS_EVENT_TYPE.ANTENNA_EVENT.equals(type)) {
				evType = EventType.ANTENNA_EVENT;
			} else if (STATUS_EVENT_TYPE.ACCESS_START_EVENT.equals(type)) {
				evType = EventType.ACCESS_START_EVENT;
			} else if (STATUS_EVENT_TYPE.ACCESS_STOP_EVENT.equals(type)) {
				evType = EventType.ACCESS_STOP_EVENT;
			} else if (STATUS_EVENT_TYPE.BUFFER_FULL_EVENT.equals(type)) {
				evType = EventType.BUFFER_FULL_EVENT;
			} else if (STATUS_EVENT_TYPE.DISCONNECTION_EVENT.equals(type)) {
				evType = EventType.DISCONNECTION_EVENT;
			} else if (STATUS_EVENT_TYPE.READER_EXCEPTION_EVENT.equals(type)) {
				evType = EventType.READER_EXCEPTION_EVENT;
			} else if (STATUS_EVENT_TYPE.DEBUG_INFO_EVENT.equals(type)) {
				evType = EventType.DEBUG_INFO_EVENT;
			} else if (STATUS_EVENT_TYPE.GPI_EVENT.equals(type)) {
				evType = EventType.GPI_EVENT;
			} else if (STATUS_EVENT_TYPE.HANDHELD_TRIGGER_EVENT.equals(type)) {
				evType = EventType.HANDHELD_TRIGGER_EVENT;
			} else if (STATUS_EVENT_TYPE.INVENTORY_START_EVENT.equals(type)) {
				evType = EventType.INVENTORY_START_EVENT;
			} else if (STATUS_EVENT_TYPE.INVENTORY_STOP_EVENT.equals(type)) {
				evType = EventType.INVENTORY_STOP_EVENT;
			}

			return evType;

		}

		private TagEvent convertTagEvent(TAG_EVENT tagEvent) {
			TagEvent evType = TagEvent.NONE;

			if (TAG_EVENT.NEW_TAG_VISIBLE.equals(tagEvent)) {
				evType = TagEvent.NEW_TAG_VISIBLE;
			} else if (TAG_EVENT.TAG_BACK_TO_VISIBILITY.equals(tagEvent)) {
				evType = TagEvent.TAG_BACK_TO_VISIBILITY;
			} else if (TAG_EVENT.TAG_MOVING.equals(tagEvent)) {
				evType = TagEvent.TAG_MOVING;
			} else if (TAG_EVENT.TAG_STATIONARY.equals(tagEvent)) {
				evType = TagEvent.TAG_STATIONARY;
			} else if (TAG_EVENT.UNKNOWN_STATE.equals(tagEvent)) {
				evType = TagEvent.UNKNOWN_STATE;
			}
			return evType;
		}

	}
}
