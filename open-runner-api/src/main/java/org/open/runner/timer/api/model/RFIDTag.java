package org.open.runner.timer.api.model;

import java.time.LocalDateTime;

public class RFIDTag {

	private String tagId;
	private int antennaId;
	private LocalDateTime firstSeenTime;
	private LocalDateTime lastSeenTime;

	private int tagSeenCount;

	public RFIDTag(String tagId, int antennaId, LocalDateTime firstSeenTime, LocalDateTime lastSeenTime,
			int tagSeenCount) {
		super();
		this.tagId = tagId;
		this.antennaId = antennaId;
		this.firstSeenTime = firstSeenTime;
		this.lastSeenTime = lastSeenTime;
		this.tagSeenCount = tagSeenCount;
	}

	public String getTagId() {
		return tagId;
	}

	public int getAntennaId() {
		return antennaId;
	}

	public LocalDateTime getFirstSeenTime() {
		return firstSeenTime;
	}

	public LocalDateTime getLastSeenTime() {
		return lastSeenTime;
	}
	
	public void setFirstSeenTime(LocalDateTime firstSeenTime) {
		this.firstSeenTime = firstSeenTime;
	}
	public void setLastSeenTime(LocalDateTime lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}

	public int getTagSeenCount() {
		return tagSeenCount;
	}

	// Opcode
	// OpStatus

}
