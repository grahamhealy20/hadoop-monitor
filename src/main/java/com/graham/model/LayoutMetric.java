package com.graham.model;

// A view model class used for holding a metric and it's current value to be presented on the front end UI.
public class LayoutMetric {
	Metric metric;
	String currentValue;
	int row;
	int col;
		
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

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public LayoutMetric(Metric metric, String value, int row, int col) {
		this.metric = metric;
		this.currentValue  = value;
		this.col = col;
		this.row = row;
	}
}
