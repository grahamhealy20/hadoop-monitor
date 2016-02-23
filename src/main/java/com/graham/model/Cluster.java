package com.graham.model;

import java.io.IOException;

import org.apache.hadoop.net.ConnectTimeoutException;
import org.mortbay.log.Log;
import org.springframework.data.mongodb.core.mapping.Document;

import com.graham.model.benchmarks.BenchmarkResult;
import com.graham.model.benchmarks.MRBenchmarkResult;

@Document
public class Cluster {
	
	private String id;
	private String name;
	private String ipAddress;
	private String username;
	private String throughputThreshold;
	
	private DFSIOOptions dfsioOptions;
	 
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
		dfsioOptions = new DFSIOOptions();
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
	
	
	public DFSIOOptions getDfsioOptions() {
		return dfsioOptions;
	}

	public void setDfsioOptions(DFSIOOptions dfsioOptions) {
		this.dfsioOptions = dfsioOptions;
	}

	public BenchmarkResult runDFSIOBenchmark(int numFiles, int fileSize) throws IOException, ConnectTimeoutException {
		Log.warn("\nRunning DFSIO Benchmark! On Cluster:\nCluster Name: " + getName() + "\nIP Address: " + getIpAddress() + "\n");
		DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread(ipAddress, username, numFiles, fileSize);
		return dfsio.runBenchmark();
	}
	
	public MRBenchmarkResult runMRBenchmarkAsync(int numRuns) throws Exception {
		MRBenchThread mrbench = new MRBenchThread(ipAddress, username, numRuns);
		return mrbench.run();
	}
	
	
	// Runs TeraSort benchmark
	public void runTeraSortBenchmark(int size) {
		TeraSortBenchmarkThread tera = new TeraSortBenchmarkThread(ipAddress, username, size);
		tera.run();
	}
	
}
