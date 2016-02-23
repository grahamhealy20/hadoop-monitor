package com.graham.model.benchmarks;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BenchmarkResult {
	@Id
	private String id;
	private String clusterName;
	private String clusterId;
	private String type;
	private long date;
	private String nrFiles;
	private String totalMb;
	private String throughputMb;
	private String avgIORate;
	private String stdDeviation;
	private String totalTime;
	private boolean alarm;

	public BenchmarkResult(String type, long date, String nrFiles,  String totalMb, String throughputMb, String avgIORate,
			String stdDeviation, String totalTime) {
		this.type = type;
		this.date = date;
		this.nrFiles = nrFiles;
		this.totalMb = totalMb;
		this.throughputMb = throughputMb;
		this.avgIORate = avgIORate;
		this.stdDeviation = stdDeviation;
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

	public String getType() {
		return type;
	}

	public long getDate() {
		return date;
	}

	public String getNrFiles() {
		return nrFiles;
	}

	public String getTotalMb() {
		return totalMb;
	}

	public String getThroughputMb() {
		return throughputMb;
	}

	public String getAvgIORate() {
		return avgIORate;
	}

	public String getStdDeviation() {
		return stdDeviation;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public boolean isAlarm() {
		return alarm;
	}

	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}
}
