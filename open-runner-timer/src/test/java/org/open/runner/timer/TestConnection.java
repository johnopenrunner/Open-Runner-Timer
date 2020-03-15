package org.open.runner.timer;

import org.junit.jupiter.api.Test;

import com.mot.rfid.api3.InvalidUsageException;
import com.mot.rfid.api3.OperationFailureException;
import com.mot.rfid.api3.PostFilter;
import com.mot.rfid.api3.RFIDReader;
import com.mot.rfid.api3.RFIDResults;
import com.mot.rfid.api3.RfidEventsListener;
import com.mot.rfid.api3.RfidReadEvents;
import com.mot.rfid.api3.RfidStatusEvents;
import com.mot.rfid.api3.START_TRIGGER_TYPE;
import com.mot.rfid.api3.STOP_TRIGGER_TYPE;
import com.mot.rfid.api3.TAG_EVENT_REPORT_TRIGGER;
import com.mot.rfid.api3.TagData;
import com.mot.rfid.api3.TriggerInfo;

public class TestConnection {

	@Test
	public void connect() {
		// Establish connection to the RFID Reader

		String hostname = "192.168.200.134";

		RFIDReader reader = new RFIDReader(hostname, 0, 0);

		try {
			reader.connect();

			if (reader.isConnected()) {
				System.out.printf("Connected!!!");

				System.out.println("\nReader ID: " + reader.ReaderCapabilities.ReaderID.getID());

				System.out.println("\nModelName: " + reader.ReaderCapabilities.getModelName());

				System.out.println(
						"\nCommunication Standard: " + reader.ReaderCapabilities.getCommunicationStandard().toString());

				System.out.println("\nCountry Code: " + reader.ReaderCapabilities.getCountryCode());

				System.out.println("\nFirwareVersion: " + reader.ReaderCapabilities.getFirwareVersion());

				System.out.println("\nRSSI Filter: " + reader.ReaderCapabilities.isRSSIFilterSupported());

				System.out
						.println("\nTag Event Reporting: " + reader.ReaderCapabilities.isTagEventReportingSupported());

				System.out
						.println("\nTag Locating Reporting: " + reader.ReaderCapabilities.isTagLocationingSupported());

				System.out.println("\nNXP Command Support: " + reader.ReaderCapabilities.isNXPCommandSupported());

				System.out.println("\nBlockEraseSupport: " + reader.ReaderCapabilities.isBlockEraseSupported());

				System.out.println("\nBlockWriteSupport: " + reader.ReaderCapabilities.isBlockWriteSupported());

				System.out.println("\nBlockPermalockSupport: " + reader.ReaderCapabilities.isBlockPermalockSupported());

				System.out.println("\nRecommisionSupport: " + reader.ReaderCapabilities.isRecommisionSupported());

				System.out.println("\nWriteWMISupport: " + reader.ReaderCapabilities.isWriteUMISupported());

				System.out.println(
						"\nRadioPowerControlSupport: " + reader.ReaderCapabilities.isRadioPowerControlSupported());

				System.out.println("\nHoppingEnabled: " + reader.ReaderCapabilities.isHoppingEnabled());

				System.out.println("\nStateAwareSingulationCapable: "
						+ reader.ReaderCapabilities.isTagInventoryStateAwareSingulationSupported());

				System.out.println("\nUTCClockCapable: " + reader.ReaderCapabilities.isUTCClockSupported());

				System.out.println("\nNumOperationsInAccessSequence: "
						+ reader.ReaderCapabilities.getMaxNumOperationsInAccessSequence());

				System.out.println("\nNumPreFilters: " + reader.ReaderCapabilities.getMaxNumPreFilters());

				System.out.println("\nNumAntennaSupported: " + reader.ReaderCapabilities.getNumAntennaSupported());

				System.out.println("\nNumGPIPorts: " + reader.ReaderCapabilities.getNumGPIPorts());

				System.out.println("\nNumGPIPorts: " + reader.ReaderCapabilities.getNumGPOPorts());

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
				
				TriggerInfo triggerInfo = new TriggerInfo();
				

				triggerInfo.StartTrigger.setTriggerType(START_TRIGGER_TYPE.START_TRIGGER_TYPE_IMMEDIATE);
				triggerInfo.StopTrigger.setTriggerType(STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_IMMEDIATE);

				triggerInfo.TagEventReportInfo.setReportNewTagEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
				triggerInfo.TagEventReportInfo.setNewTagEventModeratedTimeoutMilliseconds((short) 500);

				triggerInfo.TagEventReportInfo.setReportTagInvisibleEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
				triggerInfo.TagEventReportInfo.setTagInvisibleEventModeratedTimeoutMilliseconds((short) 500);

				triggerInfo.TagEventReportInfo.setReportTagBackToVisibilityEvent(TAG_EVENT_REPORT_TRIGGER.MODERATED);
				triggerInfo.TagEventReportInfo.setTagBackToVisibilityModeratedTimeoutMilliseconds((short) 500);

				triggerInfo.setTagReportTrigger(1);
				
				
				reader.Events.addEventsListener(new RfidEventsListener() {
					@Override
					public void eventStatusNotify(RfidStatusEvents statusEvent) {
						System.out.println("Status Notification " + statusEvent.StatusEventData.getStatusEventType());
						// Read tags

					}

					@Override
					public void eventReadNotify(RfidReadEvents readEvent) {
						// System.out.println("Read Notification " +
						// readEvent.getReadEventData().tagData);

						TagData tag = readEvent.getReadEventData().tagData;

						System.out.println("Tag ID " + tag.getTagID());

					}
				});				
				
				reader.Actions.Inventory.perform(null, triggerInfo, null);
				
				Thread.sleep(5000);
				
				reader.Actions.Inventory.stop();
			} else {
				System.out.printf("Not Connected!!!");

			}
		} catch (OperationFailureException e) {

			RFIDResults results = e.getResults();

			System.out.print("Value: ");
			System.out.print(results.getValue());

			System.out.println(e.getMessage());
		} catch (InvalidUsageException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (reader.isConnected()) {
			try {
				reader.disconnect();
			} catch (InvalidUsageException | OperationFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("End");
	}
}
