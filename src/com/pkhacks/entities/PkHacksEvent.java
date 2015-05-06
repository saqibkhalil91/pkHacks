package com.pkhacks.entities;

import java.io.Serializable;

import com.parse.ParseObject;

public class PkHacksEvent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eventName;
	private String city;
	private String startDate;
	private String endDate;
	private String link;
	private EventType eventType;

	public void createObjectFromParsePbj(ParseObject parseObject) {
		eventName = parseObject.getString("event_name");
		city = parseObject.getString("City");
		startDate = parseObject.getString("start_date");
		endDate = parseObject.getString("end_date");
		link = parseObject.getString("link");

	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}