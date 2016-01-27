package com.graham.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class utilities {
	
	
	//public static String[] splitFile(String location) {
		//File output = new File(location);
		
	//}
	
	public static ArrayList<String> splitDFSIOFile(String location) {
		ArrayList<String> values = new ArrayList<String>();
		// Open DFSIO file
		File output = new File(location);
		
		// Read contents to string
		String out = readFileToString(output);
		
		// Split the string by new line char
		String[] lines = out.split("\n");
		
		// Parse each line and scrape the relevant value
		for(String line : lines) {
			String[] result = line.split(": ");
			
			// Grab value and tidy up.
			String value = result[1].replace("\n", "");

			// Add to list
			values.add(value);
		}
		
		return values;
	}
	
	
	public static String[] splitTeraSortOutput(String location) {
		ArrayList<String> values = new ArrayList<String>();
		
		File output = new File(location);
		String out = readFileToString(output);
		
		//Split lines by new line
		String[] lines = out.split("\n");
		
		return lines;
	}
	
	// Parse a MRBench output file into just values
	public static String[] splitMRBenchOutput(String location) {
		
		File output = new File(location);
		String out = readFileToString(output);
		
		//Skip the first line and just get results of second line
		String[] values = out.split("\n");
		
		//return values row
		return values[2].replace("\t", " ").split(" ");
	}
	
	// Sets the stdout to a file 
	public static void pipeOutputToFile(String location) {
		PrintStream fileOut;
		try {
			fileOut = new PrintStream(new File(location));
			System.setOut(fileOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Reads a file into a string via Apache Commons
	private static String readFileToString(File fileToRead) {
		String out = null;
		try {
			out = FileUtils.readFileToString(fileToRead);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}
