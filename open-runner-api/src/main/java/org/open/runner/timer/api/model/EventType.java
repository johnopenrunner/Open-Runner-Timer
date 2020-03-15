package org.open.runner.timer.api.model;

public enum EventType {
	NOT_SUPPORTED("Not Supported"),
	DEBUG_INFO_EVENT("Debug Infgo Event"),
	HANDHELD_TRIGGER_EVENT("Handheld Trigger Event"),
	GPI_EVENT("GPI Event"),
	ANTENNA_EVENT("Antenna Event"),
	INVENTORY_START_EVENT("Inventory Start Event"),
	INVENTORY_STOP_EVENT("Inventory Stop Event"),
	ACCESS_START_EVENT("Access Stop Event"),
	ACCESS_STOP_EVENT("Access Stop Event"),
	DISCONNECTION_EVENT("Reader Disconnection Event"),
	BUFFER_FULL_EVENT("Buffer Full Event"),
	TAG_READ_EVENT("Tag Read Event"),
	NXP_EAS_ALARM_EVENT("NXP with EAS System Bit Set Event"),
	READER_EXCEPTION_EVENT("Reader Exception Event"),
	TEMPERATURE_ALARM_EVENT("Temperature Threshold Event");
	
	/*Display version of the event type */
	private String description;
	
	
	EventType(final String displayDescription) {
		this.description = displayDescription;
	}
	
	public String getDescription() {
		return description;
	}
	
}
