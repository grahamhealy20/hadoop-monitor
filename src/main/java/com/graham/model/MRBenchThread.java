package com.graham.model;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MRBench;

import com.graham.model.benchmarks.MRBenchmarkResult;

public class MRBenchThread implements Runnable {
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
	
	public void run() {
		PrintStream out = System.out;
		
		// Generate unique result file name
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
		Date date = new Date();
		String fileOutputName = "MRBenchmark-" + dateFormat.format(date) + ".txt";
		String location = "/home/hadoop/" + fileOutputName;
		
		com.graham.model.utils.utilities.pipeOutputToFile(location);
		
		JobConf jobConf = new JobConf();
		jobConf.set("test.build.data", "home/hadoop/benchmark/MRBench");
		jobConf.set("fs.defaultFS", "hdfs://192.168.0.106:9000");
		jobConf.set("yarn.resourcemanager.address", "192.168.0.106:5001");
		jobConf.set("mapreduce.framework.name", "yarn");
		
		MRBench mr = new MRBench();
		mr.setConf(jobConf);
			
		try {
			mr.run(String.format("-numRuns %d", numRuns)
					.split(" "));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Set output back to Stdout
		try {
			System.setOut(out);
			System.out.println("Stdout restored");
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		//Format output
		String[] values = com.graham.model.utils.utilities.splitMRBenchOutput(location); 

		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String dateString = dateFormat.format(date);
		
		result = new MRBenchmarkResult(numRuns, values[0], values[2], values[3], dateString, values[4]);
	}

	public MRBenchmarkResult getBenchmarkResult() {
		return result;
	}
}
