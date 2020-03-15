package org.open.runner.timer.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Participant implements Serializable {
	private static final long serialVersionUID = 5395450776766432119L;
	private String tagID;
	private String name;
	private String avatar;
	
	private int startPosition;
	private int endPosition;
	
	private LocalDateTime chipStartTime;
	private LocalDateTime chipEndTime;
	
	
	
	
}
