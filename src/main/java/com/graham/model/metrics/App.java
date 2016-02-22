package com.graham.model.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Maps to 8088/ws/v1/cluster/apps
@JsonIgnoreProperties(ignoreUnknown = true)
public class App {
	private String id;
	private String user;
	private String name;
	private String queue;
	private String state;
	private String finalStatus;
	private double progress;
	private double elapsedTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public double getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
}
