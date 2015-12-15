package com.graham.model;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.fs.TestDFSIO;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.security.UserGroupInformation;
import org.mortbay.log.Log;

public class DFSIOBenchmarkThread implements Runnable {
	private BenchmarkResult bresult;
	
	private String ipAddress;
	private String user;
	
	
	
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
	
	public DFSIOBenchmarkThread(String ipAddress, String user) {
		setIpAddress(ipAddress);
		setUser(user);
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
//		jobConf.set("test.build.data", "home/hadoop/benchmark/TestDFSIO");
		jobConf.set("test.build.data", "/bench/");

		
		jobConf.set("fs.defaultFS", "hdfs://" + ipAddress);
		jobConf.set("hadoop.job.ugi", user);
		
		testDFSIO.setConf(jobConf);

		try {
			testDFSIO.run(String.format("-write -nrFiles 10 -fileSize 10 -resFile /home/hadoop/%s", fileOutputName)
					.split(" "));
		} catch (IOException e) {

			e.printStackTrace();
		}

		String out = "";

		// Get output
		File output = new File(location);
		try {
			out = FileUtils.readFileToString(output);

			System.out.println("FINISHED: " + out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] lines = out.split("\n");

		for (String string : lines) {
			String[] result = string.split(": ");
			String value = result[1].replace("\n", "");
			formatRes.add(value);
		}
		
		bresult = new BenchmarkResult(Character.toUpperCase(formatRes.get(0).charAt(0)) + formatRes.get(0).substring(1), formatRes.get(1), formatRes.get(2), formatRes.get(3),
				formatRes.get(4), formatRes.get(5), formatRes.get(6), formatRes.get(7));

		Log.info("Benchmark Complete");
	}

	public BenchmarkResult getBenchmarkResult() {
		return bresult;
	}

}
