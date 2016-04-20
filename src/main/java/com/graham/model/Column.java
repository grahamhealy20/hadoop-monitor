package com.graham.model;

public class Column {
	private Metric metric;
	private double limit;
	
	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	public double getLimit() {
		return limit;
	}
	public void setLimit(double limit) {
		this.limit = limit;
	}
	
	public Column() {
		this.metric = new Metric();
	}
}
