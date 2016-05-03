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
import com.graham.model.benchmarks.BenchmarkResult;
import com.graham.model.dbaccess.BenchmarkResultService;
import com.graham.model.dbaccess.ClusterService;

@Controller
@RequestMapping("dfsio")
public class DFSIOController {

	@Autowired
	private BenchmarkResultService benchmarkResultService;
	@Autowired
	private ClusterService clusterService;
		
	/// === REST FUNCTIONS === ///
	
	// GET /test/
	@RequestMapping(value = "/dfsio", method = RequestMethod.POST)
	public @ResponseBody Callable<BenchmarkResult> dfsioAsync(String id, int numFiles, int fileSize) throws Exception {
		
		// Validate input
		if(dfsioRequestIsValid(id, numFiles, fileSize)) {
			// Run benchmark and store it
			BenchmarkResult result = benchmarkDFSIOAsync(id, numFiles, fileSize);
			benchmarkResultService.addBenchmarkResult(result);

			return new Callable<BenchmarkResult>() {

				@Override
				public BenchmarkResult call() throws Exception {
					// TODO Auto-generated method stub
					return result;
				}
			};
			
		} else {
		}
			throw new IllegalArgumentException("");
	}

	// REST GET /benchmarks/
	@RequestMapping("/benchmarks/{id}")
	public ResponseEntity<ArrayList<BenchmarkResult>> getBenchmarks(@PathVariable("id") String id) {
		// Get benchmarks from DB
		ArrayList<BenchmarkResult> results = (ArrayList<BenchmarkResult>) benchmarkResultService.listClusterBenchmarkResultByDate(id);
		return new ResponseEntity<ArrayList<BenchmarkResult>>(results, HttpStatus.OK);
	}
	
	@RequestMapping("/benchmarks/last/{id}")
	public ResponseEntity<ArrayList<BenchmarkResult>> getLastFiveBenchmarks(@PathVariable("id") String id) {
		// Get benchmarks from DB
		ArrayList<BenchmarkResult> results = (ArrayList<BenchmarkResult>) benchmarkResultService.listLastFiveClusterBenchmarkResult(id);
		return new ResponseEntity<ArrayList<BenchmarkResult>>(results, HttpStatus.OK);
	}

	// GET /benchmark/{id}
	@RequestMapping(value = "/benchmark/{id}", method = RequestMethod.GET)
	public ResponseEntity<BenchmarkResult> benchmark(@PathVariable("id") String id) {
		
		// Get benchmark result
		BenchmarkResult result = benchmarkResultService.getBenchmarkResult(id);
		if(result != null) {
			return new ResponseEntity<BenchmarkResult>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// POST /delete/{id}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> deleteResult(@PathVariable("id") String id) {
		// Get benchmark result
		BenchmarkResult result = benchmarkResultService.getBenchmarkResult(id); 
		benchmarkResultService.deleteBenchmarkResult(result);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// === PRIVATE CODE === // 

	private BenchmarkResult benchmarkDFSIOAsync(String id, int numFiles, int fileSize) throws IOException, ConnectTimeoutException {
		Cluster cluster = clusterService.getCluster(id);
		BenchmarkResult result = cluster.runDFSIOBenchmark(numFiles, fileSize);
		result.setClusterName(cluster.getName());
		result.setClusterId(cluster.getId());
		return result;
	}
	
	private boolean dfsioRequestIsValid(String id, int numFiles, int fileSize) {
		if(id != null && id.length() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/// === ERROR HANDLING === ///

	// Handles an error in Cluster connection
	@ExceptionHandler({IOException.class, ConnectTimeoutException.class})
	public ResponseEntity<String> handleConnectionFailure(Exception ex) {
		//ex.printStackTrace();
		System.out.println("Connection Timeout Failure");
		return new ResponseEntity<String>("{" + "\"message\"" + ":" + "\"Connection Failure\"" + "}", HttpStatus.BAD_REQUEST);
	}

	// Handles an error in Cluster connection
	@ExceptionHandler(RemoteException.class)
	public ResponseEntity<String> handleRemoteError(RemoteException ex) {
		ex.printStackTrace();
		return new ResponseEntity<String>("{" + "\"message\"" + ":" + "\"Cluster Error\"" + "}", HttpStatus.TOO_MANY_REQUESTS);
	}

	// Handles an error in Cluster connection
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentError(IllegalArgumentException ex) {
		ex.printStackTrace();
		return new ResponseEntity<String>("{" + "\"message\"" + ":" + "\"Bad Request\"" + "}", HttpStatus.BAD_REQUEST);
	}
}