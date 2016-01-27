package com.graham.model;

import org.mortbay.log.Log;

import com.graham.model.benchmarks.BenchmarkResult;
import com.graham.model.benchmarks.MRBenchmarkResult;

public class Cluster {
	

	private String id;
	private String name;
	private String ipAddress;
	private String username;
	private String throughputThreshold;
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Cluster() {
		
	}
	
	public Cluster(String name, String ipAddress, String username) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.username = username;
	}
	
	public Cluster(String name, String ipAddress) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.username = "hadoop";
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getThroughputThreshold() {
		return throughputThreshold;
	}

	public void setThroughputThreshold(String throughputThreshold) {
		this.throughputThreshold = throughputThreshold;
	}

	// Runs default benchmark
	public BenchmarkResult runDFSIOBenchmark(int numFiles, int fileSize) {
		Log.warn("Running DFSIO Benchmark! On Cluster:\nCluster Name: " + getName() + "\nIP Address: " + getIpAddress());
		DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread(ipAddress, username, numFiles, fileSize);
		Thread t = new Thread(dfsio);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		return dfsio.getBenchmarkResult();
	}
	
	public BenchmarkResult runDFSIOBenchmarkAsync(int numFiles, int fileSize) {
		Log.warn("Running DFSIO Benchmark! On Cluster:\nCluster Name: " + getName() + "\nIP Address: " + getIpAddress() + "\n");
		DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread(ipAddress, username, numFiles, fileSize);
		Thread t = new Thread(dfsio);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		return dfsio.getBenchmarkResult();
	}
	
	public MRBenchmarkResult runMRBenchmarkAsync(int numRuns) {
		MRBenchThread mrbench = new MRBenchThread(ipAddress, username, numRuns);
		Thread t = new Thread(mrbench);
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		return mrbench.getBenchmarkResult();
	}
	
	
	// Runs TeraSort benchmark
	public void runTeraSort() {
		TeraSortBenchmarkThread tera = new TeraSortBenchmarkThread(ipAddress, username);
		tera.run();
	}
}
