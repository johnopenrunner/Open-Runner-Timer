package org.open.runner.timer.datasource.zebrafx;

import java.util.Set;

import org.open.runner.timer.api.listener.RFIDEventListener;
import org.open.runner.timer.api.model.EventType;
import org.open.runner.timer.api.model.TagEvent;

import com.mot.rfid.api3.InvalidUsageException;
import com.mot.rfid.api3.OperationFailureException;
import com.mot.rfid.api3.RFIDReader;
import com.mot.rfid.api3.RfidEventsListener;
import com.mot.rfid.api3.RfidReadEvents;
import com.mot.rfid.api3.RfidStatusEvents;
import com.mot.rfid.api3.STATUS_EVENT_TYPE;
import com.mot.rfid.api3.TAG_EVENT;
import com.mot.rfid.api3.TagData;

/**
 * Event processor
 * 
 *
 */
public class ZebraFXEventProcessor implements Runnable {
	private RFIDReader reader;
	private RFIDEventListener evListener;
	private Set<EventType> notifyEventTypeList;
	private RfidEventsListener rfidEventsListener;

	public ZebraFXEventProcessor(final RFIDReader reader, final RFIDEventListener evListener,
			Set<EventType> notifyEventTypeList) {
		this.reader = reader;
		this.evListener = evListener;
		this.notifyEventTypeList = notifyEventTypeList;

		notifyEventTypeList.stream().forEach(e -> registerEvent(e));

		rfidEventsListener = buildRfidEventListener();

	}

	@Override
	public void run() {

		System.out.println("Event Processor running");

		reader.Events.addEventsListener(rfidEventsListener);

		try {
			reader.Actions.Inventory.perform(null, null, null);
			
		} catch (InvalidUsageException | OperationFailureException e) {
			e.printStackTrace();
		} 
		
		while (reader.isConnected()) {

			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				/*System.out.println("After perform");

				Thread.sleep(1000);

				System.out.println("After sleep");

				reader.Actions.Inventory.stop();

				System.out.println("After stop");

				TagData[] remainingTags = reader.Actions.getReadTags(100);

				for (int nIndex = 0; nIndex < remainingTags.length; nIndex++)

				{
					final TagData tag = remainingTags[nIndex];

					evListener.processEvent(convertTagEvent(tag.getTagEvent()));
					
					// tag.getOpCode()
					System.out.println("Antenna ID: " + remainingTags[nIndex].getAntennaID());
					System.out.println("Tag ID: " + remainingTags[nIndex].getTagID());
					System.out.println("Tag Event : " + remainingTags[nIndex].getTagEvent());
					System.out.println("Tag Sys Time: " + remainingTags[nIndex].getTagEventTimeStamp());

					// Update Seen Count
					
					 * Long seenCountTemp = Long.valueOf(seenCount.toString());
					 * 
					 * long seenCountTmp = (long)tag.getTagSeenCount() + seenCountTemp.longValue();
					 * Long seenCountNew = Long.valueOf(seenCountTmp);
					 * 
					 * 
					 * System.out.println("Seen Coount: " + seenCountNew);
					 

				}*/

			/*
			 * } catch (InvalidUsageException | OperationFailureException e) {
			 * e.printStackTrace(); } catch (InterruptedException e) {
			 * Thread.currentThread().interrupt(); }
			 */
		}System.out.println("After loop");reader.Events.removeEventsListener(rfidEventsListener);

	try

	{
		reader.Actions.Inventory.stop();

	}catch(InvalidUsageException|
	OperationFailureException e)
	{
		e.printStackTrace();
	}

	System.out.println("Event Processor stopped");
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

	private void registerEvent(EventType type) {
		reader.Events.setAntennaEvent(EventType.ANTENNA_EVENT.equals(type));
		reader.Events.setAccessStartEvent(EventType.ACCESS_START_EVENT.equals(type));
		reader.Events.setAccessStopEvent(EventType.ACCESS_STOP_EVENT.equals(type));
		reader.Events.setBufferFullEvent(EventType.BUFFER_FULL_EVENT.equals(type));		
		reader.Events.setGPIEvent(EventType.GPI_EVENT.equals(type));		
		reader.Events.setTagReadEvent(EventType.TAG_READ_EVENT.equals(type));
		reader.Events.setHandheldEvent(EventType.HANDHELD_TRIGGER_EVENT.equals(type));
		reader.Events.setInventoryStartEvent(EventType.INVENTORY_START_EVENT.equals(type));
		reader.Events.setInventoryStopEvent(EventType.INVENTORY_STOP_EVENT.equals(type));

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

	private RfidEventsListener buildRfidEventListener() {
		return new RfidEventsListener() {

			@Override
			public void eventStatusNotify(RfidStatusEvents statusEvent) {
				System.out.println("Status Notification " + statusEvent.StatusEventData.getStatusEventType());

				final EventType evType = convertEventType(statusEvent.StatusEventData.getStatusEventType());

				// if (notifyEventTypeList.contains(evType)) {
//					evListener.processEvent(evType);
				// }

			}

			@Override
			public void eventReadNotify(RfidReadEvents readEvent) {
				System.out.println("Read Notification " + readEvent.getReadEventData().tagData);

				final TagData tagData = readEvent.getReadEventData().tagData;

				//evListener.processEvent(con);
				// tagData.getAntennaID()
				// tagData.getTagEventTimeStamp()

			}
		};
	}
}
