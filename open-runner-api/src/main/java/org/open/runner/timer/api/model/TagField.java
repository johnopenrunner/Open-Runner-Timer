package org.open.runner.timer.api.model;

public enum TagField {
	
	ANTTENNA_ID("Anttena Id"),
	CHANNEL_INDEX("Channel Index"),
	CRC("Crc"),
	FIRST_SEEN_TIMESTAMP("First Seen Timestamp"),
	LAST_SEEN_TIMESTAMP("last Seen Timestamp"),
	PC("PC Bits"),
	PEAK_RSSI("Peak RSSI"),
	PHASE_INFO("Phase Info"),
	TAG_SEEN_COUNT("Tag Seen Count"),
	XPC("XPC");
	
	/*Display version of the event type */
	private String description;
	
	
	TagField(final String displayDescription) {
		this.description = displayDescription;
	}
	
	public String getDescription() {
		return description;
	}
}
