package com.graham.model.utils;

import org.springframework.web.client.RestTemplate;

//Class used to help download from REST endpoints
public class HttpHelper {
	/* 
	 * Hadoop REST URL: 80080/ws/v1/cluster
	 * 						 	   /cluster/scheduler
	 */

	public void downloadJmxMetrics(String ipAddress) {		
		RestTemplate restTemplate = new RestTemplate();
		//String resp = restTemplate.getForObject("http://192.168.0.106:50070/jmx", String.class);
		//System.out.println(resp);
	}
	
}
