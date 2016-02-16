package com.graham.model.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JvmMetric extends Bean {
	
	@JsonProperty("MemNonHeapUsedM") private double memNonHeapUsed;
	@JsonProperty("MemNonHeapCommittedM") private double memNonHeapCommittedM;	
	@JsonProperty("MemNonHeapMaxM") private double memNonHeapMaxM;	
	@JsonProperty("MemHeapUsedM") private double memHeapUsedM;	
	@JsonProperty("MemHeapCommittedM") private double memHeapCommittedM;
	@JsonProperty("MemHeapMaxM") private double memHeapMaxM;	
	@JsonProperty("MemMaxM") private double memMaxM;	
	@JsonProperty("GcCount") private int gcCount;	
	@JsonProperty("GcTimeMillis") private int gcTimeMillis;	
	@JsonProperty("GcNumWarnThresholdExceeded") private int gcNumWarnThresholdExceeded;
	@JsonProperty("GcNumInfoThresholdExceeded") private int gcNumInfoThresholdExceeded;	
	@JsonProperty("GcTotalExtraSleepTime") private int gcTotalExtraSleepTime;
	@JsonProperty("ThreadsNew")	private int threadsNew;
	@JsonProperty("ThreadsRunnable") private int threadsRunnable;	
	@JsonProperty("ThreadsBlocked") private int threadsBlocked;	
	@JsonProperty("ThreadsWaiting") private int threadsWaiting;	
	@JsonProperty("ThreadsTimedWaiting") private int threadsTimedWaiting;	
	@JsonProperty("ThreadsTerminated") private int threadsTerminated;
	@JsonProperty("LogFatal") private int logFatal;
	@JsonProperty("LogError") private int logError;	
	@JsonProperty("LogWarn") private int logWarn;	
	@JsonProperty("LogInfo") private int logInfo;
	
	
	public int getGcTimeMillis() {
		return gcTimeMillis;
	}

	public void setGcTimeMillis(int gcTimeMillis) {
		this.gcTimeMillis = gcTimeMillis;
	}

	public double getMemNonHeapUsed() {
		return memNonHeapUsed;
	}

	public void setMemNonHeapUsed(double memNonHeapUsed) {
		this.memNonHeapUsed = memNonHeapUsed;
	}

	public double getMemNonHeapCommittedM() {
		return memNonHeapCommittedM;
	}

	public void setMemNonHeapCommittedM(double memNonHeapCommittedM) {
		this.memNonHeapCommittedM = memNonHeapCommittedM;
	}

	public double getMemNonHeapMaxM() {
		return memNonHeapMaxM;
	}

	public void setMemNonHeapMaxM(double memNonHeapMaxM) {
		this.memNonHeapMaxM = memNonHeapMaxM;
	}

	public double getMemHeapUsedM() {
		return memHeapUsedM;
	}

	public void setMemHeapUsedM(double memHeapUsedM) {
		this.memHeapUsedM = memHeapUsedM;
	}

	public double getMemHeapCommittedM() {
		return memHeapCommittedM;
	}

	public void setMemHeapCommittedM(double memHeapCommittedM) {
		this.memHeapCommittedM = memHeapCommittedM;
	}

	public double getMemHeapMaxM() {
		return memHeapMaxM;
	}

	public void setMemHeapMaxM(double memHeapMaxM) {
		this.memHeapMaxM = memHeapMaxM;
	}

	public double getMemMaxM() {
		return memMaxM;
	}

	public void setMemMaxM(double memMaxM) {
		this.memMaxM = memMaxM;
	}

	public int getGcCount() {
		return gcCount;
	}

	public void setGcCount(int gcCount) {
		this.gcCount = gcCount;
	}

	public int getGcNumWarnThresholdExceeded() {
		return gcNumWarnThresholdExceeded;
	}

	public void setGcNumWarnThresholdExceeded(int gcNumWarnThresholdExceeded) {
		this.gcNumWarnThresholdExceeded = gcNumWarnThresholdExceeded;
	}

	public int getGcNumInfoThresholdExceeded() {
		return gcNumInfoThresholdExceeded;
	}

	public void setGcNumInfoThresholdExceeded(int gcNumInfoThresholdExceeded) {
		this.gcNumInfoThresholdExceeded = gcNumInfoThresholdExceeded;
	}

	public int getGcTotalExtraSleepTime() {
		return gcTotalExtraSleepTime;
	}

	public void setGcTotalExtraSleepTime(int gcTotalExtraSleepTime) {
		this.gcTotalExtraSleepTime = gcTotalExtraSleepTime;
	}

	public int getThreadsNew() {
		return threadsNew;
	}

	public void setThreadsNew(int threadsNew) {
		this.threadsNew = threadsNew;
	}

	public int getThreadsRunnable() {
		return threadsRunnable;
	}

	public void setThreadsRunnable(int threadsRunnable) {
		this.threadsRunnable = threadsRunnable;
	}

	public int getThreadsBlocked() {
		return threadsBlocked;
	}

	public void setThreadsBlocked(int threadsBlocked) {
		this.threadsBlocked = threadsBlocked;
	}

	public int getThreadsWaiting() {
		return threadsWaiting;
	}

	public void setThreadsWaiting(int threadsWaiting) {
		this.threadsWaiting = threadsWaiting;
	}

	public int getThreadsTimedWaiting() {
		return threadsTimedWaiting;
	}

	public void setThreadsTimedWaiting(int threadsTimedWaiting) {
		this.threadsTimedWaiting = threadsTimedWaiting;
	}

	public int getThreadsTerminated() {
		return threadsTerminated;
	}

	public void setThreadsTerminated(int threadsTerminated) {
		this.threadsTerminated = threadsTerminated;
	}

	public int getLogFatal() {
		return logFatal;
	}

	public void setLogFatal(int logFatal) {
		this.logFatal = logFatal;
	}

	public int getLogError() {
		return logError;
	}

	public void setLogError(int logError) {
		this.logError = logError;
	}

	public int getLogWarn() {
		return logWarn;
	}

	public void setLogWarn(int logWarn) {
		this.logWarn = logWarn;
	}

	public int getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(int logInfo) {
		this.logInfo = logInfo;
	}
	
}
