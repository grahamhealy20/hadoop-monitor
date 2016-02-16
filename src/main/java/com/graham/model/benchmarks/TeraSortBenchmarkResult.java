package com.graham.model.benchmarks;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TeraSortBenchmarkResult {
	@Id
	private String id;
	private String clusterName;
	private String clusterId;
	private String date;

	public TeraSortBenchmarkResult(String id, String clusterName, String clusterId, String date) {
		this.id = id;
		this.clusterName = clusterName;
		this.clusterId = clusterId;
		this.date = date;
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

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
