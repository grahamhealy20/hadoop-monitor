package com.graham.model.utils;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

//Class used to help download from REST endpoints
public class HttpHelper {
	/* 
	 * Hadoop REST URL: 80080/ws/v1/cluster
	 * 						 	   /cluster/scheduler
	 */

	// Download
	public void downloadHadoopClusterOverview() {
		CloseableHttpClient client = HttpClients.createDefault();
		//		HttpGet req = new HttpGet("192.168.0.106:50070/jmx?qry=Hadoop:*");
		HttpGet req = new HttpGet("http://ip.jsontest.com/");

		try {
			//Handle response
			String response = client.execute(req, new BasicResponseHandler());
			System.out.println("Metrics downloaded");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
}
