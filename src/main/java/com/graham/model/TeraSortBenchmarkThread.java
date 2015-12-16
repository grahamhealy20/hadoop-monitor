package com.graham.model;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.examples.terasort.TeraGen;
import org.apache.hadoop.examples.terasort.TeraSort;
import org.apache.hadoop.examples.terasort.TeraValidate;
import org.apache.hadoop.examples.terasort.TeraGen.Counters;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Cluster;
import org.mortbay.log.Log;

public class TeraSortBenchmarkThread {
	
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
	
	public TeraSortBenchmarkThread(String ipAddress, String user) {
		setIpAddress(ipAddress);
		setUser(user);
	}
	
	public void run() {
		ArrayList<String> formatRes = new ArrayList<String>();
		//Set config
		JobConf jobConf = new JobConf();
		//jobConf.set("test.build.data", "/bench/");
		
		jobConf.set("test.build.data", "home/hadoop/benchmark/TeraSort");
		jobConf.set("fs.defaultFS", "hdfs://" + ipAddress + ":9000");
		jobConf.set("hadoop.job.ugi", user);
		jobConf.set("yarn.resourcemanager.address", "192.168.0.106:5001");
		jobConf.set("mapreduce.framework.name", "yarn");
		Log.info("\nThread created. Running TeraGen facility\n");
		
		//Try and delete previous data
		String teraGenFileName = "terasortInput";
		String teraGenLocation = "/bench/" + teraGenFileName;
		
		Log.info("\nDeleting File");
		try {
			FileSystem fs = FileSystem.get(jobConf);
			boolean recursive = true;
			fs.delete(new Path(teraGenLocation), recursive);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		TeraGen teraGen = new TeraGen();
		teraGen.setConf(jobConf);
		
		try{
		teraGen.run(String.format("100000 %s", teraGenLocation).split(" "));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.info("\nThread created. Running TeraSort benchmark\n");
		
		String teraSortFileName = "terasortOutput";
		String teraSortLocation = "/bench/" + teraSortFileName;
		
		// Generate unique result file name
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
		Date date = new Date();
		String fileOutputName = "TeraSortBenchmark-" + dateFormat.format(date) + ".txt";

		// File output location
		String location = "/home/hadoop/" + fileOutputName;
		TeraSort teraSort = new TeraSort();		
		teraSort.setConf(jobConf);
		
		Log.info("\nDeleting File");
		try {
			FileSystem fs = FileSystem.get(jobConf);
			boolean recursive = true;
			fs.delete(new Path(teraSortLocation), recursive);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			teraSort.run(String.format("%s %s", teraGenLocation, teraSortLocation).split(" "));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TeraValidate teraValidate = new TeraValidate();
		teraValidate.setConf(jobConf);
		
		String teraValFileName = "terasortValidate";
		String teraValLocation = "/bench/" + teraValFileName;
		
		Log.info("\nDeleting File");
		try {
			FileSystem fs = FileSystem.get(jobConf);
			boolean recursive = true;
			fs.delete(new Path(teraValLocation), recursive);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Log.info("\nThread created. Running TeraValidate benchmark\n");
		
		try {
			teraValidate.run(String.format("%s %s", teraSortLocation, teraValLocation).split(" "));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private void runTeraGen() {
		
	}
	
	private void runTeraValdidate() {
		
	}
}
