package com.graham.model;

public class Metric {
	private String id;
	private String key;
	private String name;
	private String unit;
	private String maxValue;
	
	public Metric() {
		
	}
	
	public Metric(String key, String name, String unit, String maxValue) {
		this.key = key;
		this.name = name;
		this.unit = unit;
		this.maxValue = maxValue;
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

	public String getUnit() {
		return unit;
	}

	public String getMaxValue() {
		return maxValue;
	}
	
	
}
