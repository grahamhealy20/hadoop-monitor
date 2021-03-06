package com.graham.controller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.apache.hadoop.net.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graham.model.Cluster;
import com.graham.model.benchmarks.MRBenchmarkResult;
import com.graham.model.dbaccess.ClusterService;
import com.graham.model.dbaccess.MRBenchmarkResultService;

@Controller
@RequestMapping("mrbench")
public class MRBenchController {

	@Autowired
	private MRBenchmarkResultService benchmarkResultService;
	@Autowired
	private ClusterService clusterService;
	
	// === REST FUNCTIONS === //
	
	// POST /mrbench/
	@RequestMapping("/mrbench")
	public @ResponseBody Callable<MRBenchmarkResult> mrBenchAsync(String id, int numRuns) throws Exception {
		
		// Validate inputs
		if(mrRequestIsValid(id, numRuns) == true) {
			MRBenchmarkResult result = benchmarkMRBenchAsync(id, numRuns);
			benchmarkResultService.addBenchmarkResult(result);

			return new Callable<MRBenchmarkResult>() {

				@Override
				public MRBenchmarkResult call() throws Exception {
					// TODO Auto-generated method stub
					return result;
				}

			};
		} else {
			throw new IllegalArgumentException("Invalid request");
		}
	}
	
	// GET /benchmarks/{id}
	@RequestMapping("/benchmarks/{id}")
	public ResponseEntity<ArrayList<MRBenchmarkResult>> getBenchmarks(@PathVariable("id") String id) {
		// Get all MRBenchmarks
		ArrayList<MRBenchmarkResult> results = (ArrayList<MRBenchmarkResult>) benchmarkResultService.listClusterBenchmarkResultByDate(id);
		return new ResponseEntity<ArrayList<MRBenchmarkResult>>(results, HttpStatus.OK);
	}
	
	@RequestMapping("/benchmarks/last/{id}")
	public ResponseEntity<ArrayList<MRBenchmarkResult>> getLastFiveBenchmarks(@PathVariable("id") String id) {
		// Get benchmarks from DB
		ArrayList<MRBenchmarkResult> results = (ArrayList<MRBenchmarkResult>) benchmarkResultService.listLastFiveClusterBenchmarkResult(id);
		return new ResponseEntity<ArrayList<MRBenchmarkResult>>(results, HttpStatus.OK);
	}
	
	// GET /benchmarks/{id}
	@RequestMapping(value = "/benchmark/{id}", method = RequestMethod.GET)
	public ResponseEntity<MRBenchmarkResult> getBenchmark(@PathVariable("id") String id) {
		// Get benchmark result
		MRBenchmarkResult result = benchmarkResultService.getBenchmarkResult(id);
		if(result != null) {
			return new ResponseEntity<MRBenchmarkResult>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	// GET /benchmarks?id={id}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> deleteResult(@PathVariable("id") String id) {
		// Get benchmark result
		MRBenchmarkResult result = benchmarkResultService.getBenchmarkResult(id); 
		benchmarkResultService.deleteBenchmarkResult(result);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// === PRIVATE FUNCTIONS === //

	private MRBenchmarkResult benchmarkMRBenchAsync(String id, int numRuns) throws Exception {
		Cluster cluster = clusterService.getCluster(id);
		MRBenchmarkResult result = cluster.runMRBenchmarkAsync(numRuns);
		result.setClusterName(cluster.getName());
		result.setClusterId(cluster.getId());
		return result;
	}
	
	private boolean mrRequestIsValid(String id, int numRuns) {
		if(id != null && id.length() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// === EXCEPTION HANDLING === //
	// Handles an error in Cluster connection
	@ExceptionHandler({IOException.class, ConnectTimeoutException.class})
	public ResponseEntity<String> handleConnectionFailure(Exception ex) {
		ex.printStackTrace();
		System.out.println("Timeout handled");
		return new ResponseEntity<String>("Cluster Connection Failure", HttpStatus.BAD_REQUEST);
	}

	// Handles an error in Cluster connection
	@ExceptionHandler(RemoteException.class)
	public ResponseEntity<String> handleRemoteError(RemoteException ex) {
		ex.printStackTrace();
		return new ResponseEntity<String>("Cluster error", HttpStatus.TOO_MANY_REQUESTS);
	}

	// Handles an error in Cluster connection
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentError(IllegalArgumentException ex) {
		ex.printStackTrace();
		return new ResponseEntity<String>("Bad IP address", HttpStatus.BAD_REQUEST);
	}


}