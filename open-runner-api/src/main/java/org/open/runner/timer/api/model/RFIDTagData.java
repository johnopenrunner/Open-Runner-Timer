package org.open.runner.timer.api.model;

import java.time.LocalDateTime;

/**
 * Associated Tag Data.
 * 
 *
 */
public class RFIDTagData {
	private String tagID;
	private short AntennaId;		
	private int tagSeenCount;
	private LocalDateTime firstSeenTime;
	private LocalDateTime lastSeenTime;
	
	public String getTagID() {
		return tagID;
	}
	public void setTagID(String tagID) {
		this.tagID = tagID;
	}
	public short getAntennaId() {
		return AntennaId;
	}
	public void setAntennaId(short antennaId) {
		AntennaId = antennaId;
	}
	public int getTagSeenCount() {
		return tagSeenCount;
	}
	public void setTagSeenCount(int tagSeenCount) {
		this.tagSeenCount = tagSeenCount;
	}
	public LocalDateTime getLastSeenTime() {
		return lastSeenTime;
	}
	public void setLastSeenTime(LocalDateTime lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}
	public LocalDateTime getFirstSeenTime() {
		return firstSeenTime;
	}
	public void setFirstSeenTime(LocalDateTime firstSeenTime) {
		this.firstSeenTime = firstSeenTime;
	}
	
	
	
	
	
}
