package com.graham.model;

import java.util.ArrayList;
import java.util.List;

public class Row {
	List<Column> cols;

	public List<Column> getCols() {
		return cols;
	}

	public void setCols(List<Column> cols) {
		this.cols = cols;
	}
	
	public Row() {
		this.cols = new ArrayList<Column>();
	}
}
