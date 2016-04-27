package com.graham.model;

// A view model class used for holding a metric and it's current value to be presented on the front end UI.
public class LayoutMetric {
	Metric metric;
	String currentValue;
		
	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public LayoutMetric(Metric metric, String value) {
		this.metric = metric;
		this.currentValue  = value;
	}
}
