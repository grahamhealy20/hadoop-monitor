package com.graham.controller;

import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.net.ConnectTimeoutException;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.graham.model.Cluster;
import com.graham.model.dbaccess.ClusterService;
import com.graham.model.metrics.Apps;
import com.graham.model.utils.HttpHelper;

@Controller
@RequestMapping("cluster")
public class ClusterController {

	@Autowired
	private ClusterService clusterService;
	
	// REST GET 
	@RequestMapping("/clusters")
	public @ResponseBody ResponseEntity<ArrayList<Cluster>> getClusters() {
		ArrayList<Cluster> clusters = (ArrayList<Cluster>) clusterService.listClusters();

		ResponseEntity<ArrayList<Cluster>> response = new ResponseEntity<>(clusters, HttpStatus.OK);
		return response;
	}
	
	// REST GET 
	@RequestMapping("/cluster/{id}")
	public @ResponseBody ResponseEntity<Cluster> getCluster(@PathVariable("id") String id) {
		Cluster cluster = clusterService.getCluster(id);

		ResponseEntity<Cluster> response = new ResponseEntity<>(cluster, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Cluster> addClusterRest(@RequestBody Cluster cluster) {		
		Cluster c = clusterService.addCluster(cluster); 
		if(c.getId() != null) {
			return new ResponseEntity<Cluster>(cluster, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<Cluster>(cluster, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<String> deleteCluster(@RequestBody Cluster cluster) {		
		clusterService.deleteCluster(cluster);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<Cluster> editCluster(@RequestBody Cluster cluster) {		
		clusterService.updateCluster(cluster);
		return new ResponseEntity<Cluster>(cluster, HttpStatus.OK);
	}
	
	////////// FULL LOGS //////////
	
	@RequestMapping(value = "/log/namenode/{id}", produces="text/plain")
	public @ResponseBody byte[] viewNamenodeLog(@PathVariable("id") String id) throws IOException {
		Cluster cluster = clusterService.getCluster(id);
		HttpHelper http = new HttpHelper();

		//Download logs
		String namenodeLog = http.downloadNameNodeLog(cluster.getIpAddress());
		getLastLines(namenodeLog, 10);
		return namenodeLog.getBytes();
	}
	
	@RequestMapping(value = "/log/datanode/{id}", produces="text/plain")
	public @ResponseBody byte[] viewDatanodeLog(@PathVariable("id") String id) {
		Cluster cluster = clusterService.getCluster(id);
		HttpHelper http = new HttpHelper();

		//Download logs
		String log = http.downloadDataNodeLog(cluster.getIpAddress());
		return log.getBytes();
	}
	
	////////// LOG TAILS //////////
	
	@RequestMapping(value = "/log/namenode/{id}/tail", produces="text/plain")
	public @ResponseBody Callable<String> viewNamenodeLogTail(@PathVariable("id") String id) throws IOException {
		Cluster cluster = clusterService.getCluster(id);
		HttpHelper http = new HttpHelper();

		//Download logs
		String namenodeLog = http.downloadNameNodeLog(cluster.getIpAddress());
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				return getLastLines(namenodeLog, 10);
			}
		};
	}
	
	@RequestMapping(value = "/log/datanode/{id}/tail", produces="text/plain")
	public @ResponseBody Callable<String> viewDatanodeLogTail(@PathVariable("id") String id) throws IOException {
		Cluster cluster = clusterService.getCluster(id);
		HttpHelper http = new HttpHelper();

		//Download logs
		String log = http.downloadDataNodeLog(cluster.getIpAddress());
		
		return new Callable<String> () {
			@Override
			public String call() throws Exception {
				return getLastLines(log, 10);
			}		
		};		
	}
	
	///////// GET JOBS /////////
	
	@RequestMapping(value = "/jobs/{id}") 
	public @ResponseBody ResponseEntity<Apps> getClusterJobs(@PathVariable("id") String id) throws ResourceAccessException, ConnectException {
		Cluster cluster = clusterService.getCluster(id);
		HttpHelper http = new HttpHelper();
		
		//Download apps
		Apps apps = http.downloadClusterApps(cluster.getIpAddress());
		return new ResponseEntity<>(apps, HttpStatus.OK);
	}
	
	///////// EXCEPTION HANDLERS //////////
	
	@ExceptionHandler({ResourceAccessException.class, ConnectException.class})
	public ResponseEntity<String> handleConnectionFailure(Exception ex) {
		//ex.printStackTrace();
		return new ResponseEntity<String>("{" + "\"message\"" + ":" + "\"Connection Failure\"" + "}", HttpStatus.BAD_REQUEST);
	}
	
	////////// PRIVATE METHODS //////////
	
	// Returns the last n lines of a given string
	private String getLastLines(String text, int lines) throws IOException {
		ArrayList<String> list = (ArrayList<String>)IOUtils.readLines(new StringReader(text));
		ArrayList<String> sub = new ArrayList<String>();
		String tail = "";
		
		for(String line : list) {
			if(sub.size() >= lines) {
				sub.remove(0);			
			}
			sub.add(line);					
		}
		
		for(String line : sub) {
			tail += "\n" + line;
		}
		
		return tail;	
	}
}
