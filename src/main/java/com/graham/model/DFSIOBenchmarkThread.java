package com.graham.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.hadoop.fs.TestDFSIO;
import org.apache.hadoop.mapred.JobConf;
import org.mortbay.log.Log;

import com.graham.model.benchmarks.BenchmarkResult;

public class DFSIOBenchmarkThread implements Runnable {
	private BenchmarkResult bresult;
	
	private String ipAddress;
	private String user;
	private int numFiles;
	private int fileSize;

	public int getNumFiles() {
		return numFiles;
	}

	public void setNumFiles(int numFiles) {
		this.numFiles = numFiles;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

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
	
	public DFSIOBenchmarkThread(String ipAddress, String user, int numFiles, int fileSize) {
		setIpAddress(ipAddress);
		setUser(user);
		setNumFiles(numFiles);
		setFileSize(fileSize);
	}

	public void run() {
		Log.info("Thread created. Running benchmark");
		
		ArrayList<String> formatRes = new ArrayList<String>();

		// Generate unique result file name
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
		Date date = new Date();
		String fileOutputName = "DFSIOBenchmark-" + dateFormat.format(date) + ".txt";

		// File output location
		String location = "/home/hadoop/" + fileOutputName;

		// Run DFSIO Benchmark
		TestDFSIO testDFSIO = new TestDFSIO();
		JobConf jobConf = new JobConf();
		jobConf.set("test.build.data", "home/hadoop/benchmark/TestDFSIO");
		jobConf.set("fs.defaultFS", "hdfs://" + ipAddress + ":9000");
		jobConf.set("hadoop.job.ugi", user);
		jobConf.set("yarn.resourcemanager.address", "192.168.0.106:5001");
		jobConf.set("mapreduce.framework.name", "yarn");

		//jobConf.set("test.build.data", "/bench/");
		
		testDFSIO.setConf(jobConf);

		try {
			testDFSIO.run(String.format("-write -nrFiles %d -fileSize %d -resFile /home/hadoop/%s", getNumFiles(), getFileSize(), fileOutputName)
					.split(" "));
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Construct a benchmark result from the array list of values
		formatRes = com.graham.model.utils.Utilities.splitDFSIOFile(location);
		bresult = new BenchmarkResult(Character.toUpperCase(formatRes.get(0).charAt(0)) + formatRes.get(0).substring(1), formatRes.get(1), formatRes.get(2), formatRes.get(3),
				formatRes.get(4), formatRes.get(5), formatRes.get(6), formatRes.get(7));

		Log.info("Benchmark Complete");
	}

	public BenchmarkResult getBenchmarkResult() {
		return bresult;
	}

}
