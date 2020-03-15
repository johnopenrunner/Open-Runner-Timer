package org.open.runner.timer.api.model;

public enum TagEvent {

	UNKNOWN_STATE("Unknown State"), NEW_TAG_VISIBLE("New Tag Visible"), TAG_NOT_VISIBLE("Tag Not Visible"),
	TAG_BACK_TO_VISIBILITY("Tag Back to Visibility"), TAG_MOVING("Tag Moving"), TAG_STATIONARY("Tag Stationary"),
	NONE("None");

	/* Display version of the event type */
	private String description;

	TagEvent(final String displayDescription) {
		this.description = displayDescription;
	}

	public String getDescription() {
		return description;
	}
}
