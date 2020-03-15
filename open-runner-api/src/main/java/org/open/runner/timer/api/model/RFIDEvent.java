package org.open.runner.timer.api.model;

public class RFIDEvent {
	private EventType eventType;
	private RFIDAntennaEventType antennaEvent;

	public RFIDEvent(EventType evType) {
		this.eventType = evType;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
