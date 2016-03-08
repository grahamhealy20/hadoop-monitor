package com.graham.model;


// Represents a rule in a cluster
public class Rule {
	private String metric;
	private String operator;
	private int value;
	
	public Rule() {
		
	}
	
	public Rule(String metric, String operator, int value) {
		this.metric = metric;
		this.operator = operator;
		this.value = value;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
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
}
