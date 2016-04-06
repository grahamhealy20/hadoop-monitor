package com.graham.model;

public class Metric {
	private String id;
	private String key;
	private String name;
	
	public Metric() {
		
	}
	
	public Metric(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
