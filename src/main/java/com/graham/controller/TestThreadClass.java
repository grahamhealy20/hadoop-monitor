package com.graham.controller;

import java.io.InputStream;

public class TestThreadClass implements Runnable{
	int counter = 0;
	String output = "";
	
	public void run() {
		System.out.println("Thread started");

		output = testCLI();
		//Simulate long computation
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Thread finished");
	}
	
	public String testCLI() {
		try {
			
			String command = "cmd /c ping 192.168.192.1";
//			String location = "C:\\WINDOWS";
			
			Process p = Runtime.getRuntime().exec(command);
			InputStream str = p.getInputStream();
			int ch;
			while((ch = str.read()) != -1) {
				//System.out.print((char)ch);
				output += (char)ch;
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return output;
	}
	
	public String getResult() {
		return output;
	}

}
