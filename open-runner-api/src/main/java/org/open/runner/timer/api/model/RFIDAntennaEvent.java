package org.open.runner.timer.api.model;

public final class RFIDAntennaEvent extends RFIDEvent {
	private RFIDAntennaEventType antennaEventType;
	private int antennaID;

	public RFIDAntennaEvent(EventType eventType, RFIDAntennaEventType antennaEventType, int anntennaID) {
		super(eventType);

		this.antennaEventType = antennaEventType;
		this.antennaID = anntennaID;
	}

	public RFIDAntennaEventType getAntennaEventType() {
		return antennaEventType;
	}

	public int getAntennaID() {
		return antennaID;
	}

}
