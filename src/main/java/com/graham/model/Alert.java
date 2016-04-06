package com.graham.model;

import java.util.UUID;

public class Alert {
	private String id;
	private String key;
	private String name;
	private String value;
	private long timestamp;
	private String action;
	
	public Alert() {
		
	}
	
	public Alert(String key, String name, String value, long timestamp, String action) {
		this.id = UUID.randomUUID().toString();
		this.key = key;
		this.name = name;
		this.value = value;
		this.timestamp = timestamp;
		this.action = action;
	}
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
