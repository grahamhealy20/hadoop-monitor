package com.graham.model.utils;

import org.springframework.web.client.RestTemplate;

import com.graham.model.metrics.App;
import com.graham.model.metrics.Apps;
import com.graham.model.metrics.Beans;


//Class used to help download from REST endpoints
public class HttpHelper {

	public Beans downloadJmxMetrics(String ipAddress) {
		RestTemplate temp = new RestTemplate();
		Beans obj = temp.getForObject("http://" + ipAddress + ":50070/jmx?qry=Hadoop:service=NameNode,name=JvmMetrics", Beans.class);
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
	
	public void downloadResourceManagerApps(String ipAddress) {
		RestTemplate temp = new RestTemplate();
		Apps obj = temp.getForObject("http://" + ipAddress + ":8088/ws/v1/cluster/apps", Apps.class);
		System.out.println(obj);
	}
	
}
