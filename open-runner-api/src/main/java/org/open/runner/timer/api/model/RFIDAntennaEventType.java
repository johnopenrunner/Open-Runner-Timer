package org.open.runner.timer.api.model;

public enum RFIDAntennaEventType {
	ANTENNA_CONNECTED("Antenna Connected"),
	ANTENNA_DISCONNECTED("Antenna Disconnected");
	
	/*Display version of the event type */
	private String description;
	
	
	RFIDAntennaEventType(final String displayDescription) {
		this.description = displayDescription;
	}
	
	public String getDescription() {
		return description;
	}
}
