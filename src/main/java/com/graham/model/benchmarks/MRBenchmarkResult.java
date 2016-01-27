package com.graham.model.benchmarks;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MRBenchmarkResult {
	@Id
	private String id;
	private String clusterName;
	private String clusterId;
	private int numRuns;
	private String dataLines;
	private String maps;
	private String reduces;
	private String date;
	private String totalTime;

	public MRBenchmarkResult(int numRuns, String dataLines, String maps, String reduces, String date, String totalTime) {
		this.numRuns = numRuns;
		this.dataLines = dataLines;
		this.maps = maps;
		this.reduces = reduces;
		this.date = date;
		this.totalTime = totalTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getTotalTime() {
		return totalTime;
	}
	
	
	public String getDataLines() {
		return dataLines;
	}

	public void setDataLines(String dataLines) {
		this.dataLines = dataLines;
	}

	public String getMaps() {
		return maps;
	}

	public void setMaps(String maps) {
		this.maps = maps;
	}

	public String getReduces() {
		return reduces;
	}

	public void setReduces(String reduces) {
		this.reduces = reduces;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public int getNumRuns() {
		return numRuns;
	}

	public void setNumRuns(int numRuns) {
		this.numRuns = numRuns;
	}
	
	
}
