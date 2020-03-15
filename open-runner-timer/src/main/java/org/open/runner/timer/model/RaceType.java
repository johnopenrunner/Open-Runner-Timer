package org.open.runner.timer.model;

public enum RaceType {
	HANDICAPPED("Handicapped Race"),
	RACE("Handicapped Race");
	
	/*Display version of the event type */
	private String description;
	
	
	RaceType(final String displayDescription) {
		this.description = displayDescription;
	}
	
	public String getDescription() {
		return description;
	}
	
}
