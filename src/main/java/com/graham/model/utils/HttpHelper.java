package com.graham.model.utils;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.graham.model.metrics.Metrics;


//Class used to help download from REST endpoints
public class HttpHelper {

	public Metrics downloadJmxMetrics(String ipAddress) throws ResourceAccessException {
		RestTemplate temp = new RestTemplate();
		Metrics obj = temp.getForObject("http://" + ipAddress + ":50070/jmx", Metrics.class);
		return obj;
	}
	
	public String downloadDataNodeLog(String ipAddress) {
		RestTemplate temp = new RestTemplate();
		String obj = temp.getForObject("http://" + ipAddress + ":8088/logs/hadoop-hadoop-datanode-" + ipAddress + ".log", String.class);
		return obj;
	}
	
	public String downloadNameNodeLog(String ipAddress) {
		RestTemplate temp = new RestTemplate();
		String obj = temp.getForObject("http://" + ipAddress + ":8088/logs/hadoop-hadoop-namenode-" + ipAddress + ".log", String.class);
		return obj;
	}
}
