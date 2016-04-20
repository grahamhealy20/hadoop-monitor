package com.graham.model;

import java.util.ArrayList;
import java.util.List;

// Class to manage layout on front end
public class Layout {
	private List<Row> rows;
	
	public Layout() {
		this.rows = new ArrayList<Row>();
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
}
