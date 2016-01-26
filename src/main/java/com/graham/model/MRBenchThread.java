package com.graham.model;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.hadoop.hdfs.NNBench;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MRBench;
import org.apache.hadoop.util.ProgramDriver;

public class MRBenchThread {
	
	public void run() {
		PrintStream out = null;
		try {
			// Redirect output to a file for parsing.
			out = System.out;
			System.setOut(new PrintStream(new File("~/outputfilemr.txt")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		JobConf jobConf = new JobConf();
		jobConf.set("test.build.data", "home/hadoop/benchmark/TestDFSIO");
		jobConf.set("fs.defaultFS", "hdfs://192.168.0.106:9000");
		jobConf.set("yarn.resourcemanager.address", "192.168.0.106:5001");
		jobConf.set("mapreduce.framework.name", "yarn");
		
		MRBench mr = new MRBench();
		mr.setConf(jobConf);
			
		try {
			mr.run(String.format("-numRuns %d", 5)
					.split(" "));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Set output back to Stdout
		try {
			System.setOut(out);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
