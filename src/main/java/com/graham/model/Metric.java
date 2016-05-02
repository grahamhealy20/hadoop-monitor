package com.graham.model;

import com.graham.model.utils.ValidationHelper;

public class Metric {
	private String id;
	private String key;
	private String name;
	private String unit;
	private String maxValue;
	
	public Metric() {
		
	}

	
	public Metric(String key, String name, String unit, String maxValue) {
		this.setKey(key);
		this.setName(name);
		this.setUnit(unit);
		this.setMaxValue(maxValue);
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
		if(ValidationHelper.required(key))
			this.key = key;
		else
			throw new IllegalArgumentException("Metric key is required");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(ValidationHelper.required(name))
			this.name = name;
		else
			throw new IllegalArgumentException("Metric name is required");
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		if(ValidationHelper.required(unit) == true)
			this.unit = unit;
		else		
			this.unit = "";
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		if(ValidationHelper.required(maxValue))
			this.maxValue = maxValue;
		else
			throw new IllegalArgumentException("Metric max value is required");
	}
}
