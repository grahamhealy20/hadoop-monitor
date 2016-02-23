package com.graham.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.TestDFSIO;
import org.apache.hadoop.net.ConnectTimeoutException;
import org.mortbay.log.Log;

import com.graham.model.benchmarks.BenchmarkResult;
import com.graham.model.utils.Utilities;

public class DFSIOBenchmarkThread {
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


	public BenchmarkResult runBenchmark() throws IOException, ConnectTimeoutException {
		ArrayList<String> formatRes = new ArrayList<String>();

		// Generate unique result file name
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
		Date date = new Date();
		long dateToStore = date.getTime();

		String fileOutputName = "DFSIOBenchmark-" + dateFormat.format(date) + ".txt";
		String location = Utilities.checkDirectory("DFSIO") + "/" +  fileOutputName;

		// Run DFSIO Benchmark
		TestDFSIO testDFSIO = new TestDFSIO();
		Configuration conf = new Configuration();
		//jobConf.set("test.build.data", "home/hadoop/benchmark/TestDFSIO");
		conf.set("fs.defaultFS", "hdfs://" + ipAddress + ":9000");
		conf.set("hadoop.job.ugi", user);
		conf.set("yarn.resourcemanager.address", ipAddress + ":5001");
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("output.dir", "/balancer/");
		conf.set("ipc.client.connect.max.retries.on.timeouts", "1");
		testDFSIO.setConf(conf);

		
		testDFSIO.run(String.format("-write -nrFiles %d -fileSize %d -resFile %s", getNumFiles(), getFileSize(), location)
				.split(" "));

		// Construct a benchmark result from the array list of values
		formatRes = com.graham.model.utils.Utilities.splitDFSIOFile(location);
		bresult = new BenchmarkResult(Character.toUpperCase(formatRes.get(0).charAt(0)) + formatRes.get(0).substring(1), dateToStore, formatRes.get(2), formatRes.get(3),
			formatRes.get(4), formatRes.get(5), formatRes.get(6), formatRes.get(7));

		Log.info("Benchmark Complete");
		return bresult;
	}

	public BenchmarkResult getBenchmarkResult() {
		return bresult;
	}

}
