package com.graham.model;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.hdfs.server.balancer.Balancer;
import org.apache.hadoop.net.ConnectTimeoutException;
import org.mortbay.log.Log;
import org.springframework.data.mongodb.core.mapping.Document;

import com.graham.model.benchmarks.BenchmarkResult;
import com.graham.model.benchmarks.MRBenchmarkResult;
import com.graham.model.utils.ValidationHelper;

@Document
public class Cluster {
	
	private String id;
	private String name;
	private String ipAddress;
	private String username;	
	
	private DFSIOOptions dfsioOptions;
	
	private ArrayList<Rule> rules;
	private ArrayList<Alert> alerts;
	
	private Layout layout;
	 
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
		if(ValidationHelper.required(username)) {
			this.username = username;	
		} else {
			throw new IllegalArgumentException("Username is required");
		}
		
	}

	public Cluster() {
		dfsioOptions = new DFSIOOptions();
		rules = new ArrayList<Rule>();
		alerts = new ArrayList<Alert>();
		layout = new Layout();
	}
	
	public Cluster(String name, String ipAddress, String username) {
		setName(name);
		setIpAddress(ipAddress);
		setUsername(username);
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
		if(ValidationHelper.required(name) == true) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("Name is required");
		}
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		if(ValidationHelper.validateIpAddress(ipAddress) == true ) {
			this.ipAddress = ipAddress;
		} else {
			throw new IllegalArgumentException("Invalid IP Address");
		}
		
	}
	
	public DFSIOOptions getDfsioOptions() {
		return dfsioOptions;
	}

	public void setDfsioOptions(DFSIOOptions dfsioOptions) {
		this.dfsioOptions = dfsioOptions;
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	
	public ArrayList<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(ArrayList<Alert> alerts) {
		this.alerts = alerts;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	// Balance the cluster
	public void balanceCluster() {
		// Make string args from a conf
		String conf = "-D fs.defaultFS=hdfs://" + ipAddress + ":9000 -D yarn.resourcemanager.address=" + ipAddress;
		String[] args = conf.split(" ");		
		Balancer.main(args);
	}

	public BenchmarkResult runDFSIOBenchmark(int numFiles, int fileSize) throws IOException, ConnectTimeoutException {
		
		// Validate inputs
		if(numFiles > 0 && fileSize > 0) {
			Log.warn("\nRunning DFSIO Benchmark! On Cluster:\nCluster Name: " + getName() + "\nIP Address: " + getIpAddress() + "\n");
			DFSIOBenchmarkThread dfsio = new DFSIOBenchmarkThread(ipAddress, username, numFiles, fileSize);
			return dfsio.runBenchmark();
		} else {
			throw new IllegalArgumentException("Invalid parameters");
		}
	}
	
	public MRBenchmarkResult runMRBenchmarkAsync(int numRuns) throws Exception {
		// Validate input
		if(numRuns > 0) {
			MRBenchThread mrbench = new MRBenchThread(ipAddress, username, numRuns);
			return mrbench.run();
		} else {
			throw new IllegalArgumentException("Invalid parameters");
		}
	}
	
	
	// Runs TeraSort benchmark
	public void runTeraSortBenchmark(int size) {
		TeraSortBenchmarkThread tera = new TeraSortBenchmarkThread(ipAddress, username, size);
		tera.run();
	}
	
}
