package com.graham.model;

public class DFSIOOptions {
	private double throughputLimit;
	private double ioLimit;
	private double stdDeviationLimit;
	private double totalTimeLimit;
	
	public double getThroughputLimit() {
		return throughputLimit;
	}
	public void setThroughputLimit(double throughputLimit) {
		this.throughputLimit = throughputLimit;
	}
	public double getIoLimit() {
		return ioLimit;
	}
	public void setIoLimit(double ioLimit) {
		this.ioLimit = ioLimit;
	}
	public double getStdDeviationLimit() {
		return stdDeviationLimit;
	}
	public void setStdDeviationLimit(double stdDeviationLimit) {
		this.stdDeviationLimit = stdDeviationLimit;
	}
	public double getTotalTimeLimit() {
		return totalTimeLimit;
	}
	public void setTotalTimeLimit(double totalTimeLimit) {
		this.totalTimeLimit = totalTimeLimit;
	}
}
