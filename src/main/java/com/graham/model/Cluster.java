package com.graham.model;

public class Cluster {
	private String name;
	private String ipAddress;
	 
	public Cluster() {
		this("Test Cluster", "192.168.0.1");
	}
	
	public Cluster(String name, String ipAddress) {
		this.name = name;
		this.ipAddress = ipAddress;
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
		DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread();
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
		DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread();
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
