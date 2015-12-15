package com.graham.model;

import org.mortbay.log.Log;

import com.jcraft.jsch.Logger;

public class Cluster {
	private String name;
	private String ipAddress;
	private String username;
	 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Cluster() {
		this("Test Cluster", "192.168.0.106:9000", "hadoop");
	}
	
	public Cluster(String name, String ipAddress, String username) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.username = username;
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

	// Runs default benchmark
	public BenchmarkResult runDFSIOBenchmark() {
		Log.warn("Running DFSIO Benchmark! On Cluster:\nCluster Name: " + getName() + "\nIP Address: " + getIpAddress());
		DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread(ipAddress, username);
		Thread t = new Thread(dfsio);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		return dfsio.getBenchmarkResult();
	}
	
	public BenchmarkResult runDFSIOBenchmarkAsync() {
		Log.warn("Running DFSIO Benchmark! On Cluster:\nCluster Name: " + getName() + "\nIP Address: " + getIpAddress() + "\n");
		DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread(ipAddress, username);
		Thread t = new Thread(dfsio);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		return dfsio.getBenchmarkResult();
	}
	
	// Runs parameterized benchmark
	public void runDFSIOBenchmark(int numFiles, int fileSize) {
		
	}
}
