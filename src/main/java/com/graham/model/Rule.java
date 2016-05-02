package com.graham.model;

import java.util.UUID;

import com.graham.model.utils.ValidationHelper;

// Represents a rule in a cluster
public class Rule {
	private String id;
	private String metric;
	private String name;
	private String operator;
	private int value;
	private String action;
	
	public Rule() {}
	
	public Rule(String metric, String name, String operator, int value, String action) {
		this.setId(UUID.randomUUID().toString());
		this.setMetric(metric);
		this.setName(name);
		this.setOperator(operator);
		this.setValue(value);
		this.setAction(action);
	}
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		if(ValidationHelper.required(id) == true)
			this.id = id;
		else
			throw new IllegalArgumentException("ID is required");
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		if(ValidationHelper.required(metric))
			this.metric = metric;
		else
			throw new IllegalArgumentException("Metric is required");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(ValidationHelper.required(name))
			this.name = name;
		else
			throw new IllegalArgumentException("Name is required");
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		if(ValidationHelper.required(operator))
			this.operator = operator;
		else
			throw new IllegalArgumentException("Operator is required");
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if(ValidationHelper.required(Integer.toString(value)))
			this.value = value;
		else
			throw new IllegalArgumentException("Value is required");
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		if(ValidationHelper.required(action))
			this.action = action;
		else
			throw new IllegalArgumentException("Action is required");
	}
	
}
