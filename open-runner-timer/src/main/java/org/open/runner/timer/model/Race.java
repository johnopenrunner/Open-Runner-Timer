package org.open.runner.timer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Race {
	private int raceID;
	private String raceName;
	private RaceType raceType;
	private LocalDateTime raceDate;

	private List<Participant> participants;

	public Race(String raceName, RaceType raceType) {
		super();
		this.raceName = raceName;
		this.raceType = raceType;
	}

	public int getRaceID() {
		return raceID;
	}

	public String getRaceName() {
		return raceName;
	}

	public RaceType getRaceType() {
		return raceType;
	}

	public LocalDateTime getRaceDate() {
		return raceDate;
	}

	public List<Participant> getParticipants() {

		if (this.participants == null) {
			this.participants = new ArrayList<>();
		}
		return participants;
	}

}
