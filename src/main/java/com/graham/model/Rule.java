package com.graham.model;

import java.util.UUID;

// Represents a rule in a cluster
public class Rule {
	private String id;
	private String metric;
	private String name;
	private String operator;
	private int value;
	private String action;
	
	public Rule() {
		
	}
	
	public Rule(String metric, String name, String operator, int value, String action) {
		this.id = UUID.randomUUID().toString();
		this.metric = metric;
		this.name = name;
		this.operator = operator;
		this.value = value;
		this.action = action;
	}
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
