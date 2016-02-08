package com.graham.model.utils;

import org.springframework.web.client.RestTemplate;

import com.graham.model.metrics.Beans;


//Class used to help download from REST endpoints
public class HttpHelper {

	public Beans downloadJmxMetrics() {
		RestTemplate temp = new RestTemplate();
		Beans obj = temp.getForObject("http://192.168.0.106:50070/jmx?qry=Hadoop:service=NameNode,name=JvmMetrics", Beans.class);
		System.out.println(obj);
		return obj;
	}
	
}
