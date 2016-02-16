package com.graham.model;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MRBench;

import com.graham.model.benchmarks.MRBenchmarkResult;
import com.graham.model.utils.Utilities;

public class MRBenchThread {
	private MRBenchmarkResult result;
	private String ipAddress;
	private String user;
	private String dataLines;
	private int numRuns;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDataLines() {
		return dataLines;
	}

	public void setDataLines(String dataLines) {
		this.dataLines = dataLines;
	}

	public int getNumRuns() {
		return numRuns;
	}

	public void setNumRuns(int numRuns) {
		this.numRuns = numRuns;
	}
	
	public MRBenchThread(String ipAddress, String user, int numRuns) {
		setIpAddress(ipAddress);
		setUser(user);
		setNumRuns(numRuns);	
	}
	
	public MRBenchmarkResult run() throws Exception {
		PrintStream out = System.out;
		
		// Generate unique result file name
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
		Date date = new Date();
		
		String fileOutputName = "MRBenchmark-" + dateFormat.format(date) + ".txt";
		String location = Utilities.checkDirectory("MRBench") + "/" +  fileOutputName;
		
		com.graham.model.utils.Utilities.pipeOutputToFile(location);
		
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://" + ipAddress + ":9000");
		conf.set("hadoop.job.ugi", user);
		conf.set("yarn.resourcemanager.address", ipAddress + ":5001");
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("output.dir", "/balancer/");
		conf.set("ipc.client.connect.max.retries.on.timeouts", "5");
		
		MRBench mr = new MRBench();
		mr.setConf(conf);
			
		mr.run(String.format("-numRuns %d", numRuns)
				.split(" "));
		
		// Set output back to Stdout
		try {
			System.setOut(out);
			System.out.println("Stdout restored");
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		//Format output
		String[] values = com.graham.model.utils.Utilities.splitMRBenchOutput(location); 

		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String dateString = dateFormat.format(date);
		
		result = new MRBenchmarkResult(numRuns, values[0], values[2], values[3], dateString, values[4]);
		return result;
	}
}
