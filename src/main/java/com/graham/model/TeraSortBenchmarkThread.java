package com.graham.model;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.examples.terasort.TeraGen;
import org.apache.hadoop.examples.terasort.TeraSort;
import org.apache.hadoop.examples.terasort.TeraValidate;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.mortbay.log.Log;

public class TeraSortBenchmarkThread {
	
	private String ipAddress;
	private String user;
	private int size;
	
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
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public TeraSortBenchmarkThread(String ipAddress, String user, int size) {
		setIpAddress(ipAddress);
		setUser(user);
		setSize(size);
	}
	
	public void run() {
		
		//Set config
		JobConf jobConf = new JobConf();
		//jobConf.set("test.build.data", "/bench/");
		
		//jobConf.set("test.build.data", "home/hadoop/benchmark/TeraSort");
		jobConf.set("fs.defaultFS", "hdfs://" + ipAddress + ":9000");
		jobConf.set("hadoop.job.ugi", user);
		jobConf.set("yarn.resourcemanager.address", ipAddress + ":5001");
		jobConf.set("mapreduce.framework.name", "yarn");
		
		Log.info("\nThread created. Running TeraGen facility\n");
		
		//Try and delete previous data
		String teraGenFileName = "terasortInput";
		String teraGenLocation = "/bench/" + teraGenFileName;
		
		try {
			Log.info("\nDeleting TeraGen File");
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
		teraGen.run(String.format("%d %s", size, teraGenLocation).split(" "));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.info("\nRunning TeraSort benchmark\n");
		
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
		
		try {
			Log.info("\nDeleting TeraSort Output File if already exists");
			FileSystem fs = FileSystem.get(jobConf);
			boolean recursive = true;
			fs.delete(new Path(teraSortLocation), recursive);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PrintStream out = System.out;
		com.graham.model.utils.Utilities.pipeOutputToFile(location);
	
		try {
			Log.info("Running TeraSort");
			teraSort.run(String.format("%s %s", teraGenLocation, teraSortLocation).split(" "));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Restore outout to stdout
		System.setOut(out);
		System.out.println("Restored stdout");
		
		TeraValidate teraValidate = new TeraValidate();
		teraValidate.setConf(jobConf);
		
		String teraValFileName = "terasortValidate";
		String teraValLocation = "/bench/" + teraValFileName;
		
		try {
			Log.info("\nDeleting TeraValidate File if already exists");
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

}
