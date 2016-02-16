package com.graham.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	// GET /
	@RequestMapping("/")
	public ModelAndView index() {

		ModelAndView mv = new ModelAndView("login");
		//mv.addObject("benchmarks", results);
		return mv;	
	}

	// GET /test/
	@RequestMapping("/dfsio")
	public @ResponseBody Callable<BenchmarkResult> dfsioAsync(String id, int numFiles, int fileSize) throws IOException, RemoteException {
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
	}
	
	// Handles an error in Cluster connection
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleConnectionFailure() {
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
	
	// Handles an error in Cluster connection
	@ExceptionHandler(RemoteException.class)
	public ResponseEntity<String> handleRemoteError() {
		return new ResponseEntity<String>(HttpStatus.TOO_MANY_REQUESTS);
	}
	
	// GET /benchmarks/
	@RequestMapping("/benchmarks")
	public ModelAndView benchmarks() {
		ArrayList<BenchmarkResult> results = (ArrayList<BenchmarkResult>) benchmarkResultService.listBenchmarkResultByDate();

		ModelAndView mv = new ModelAndView("benchmarks");
		mv.addObject("clusters", clusterService.listClusters());
		mv.addObject("benchmarks", results);
		return mv;
	}

	// GET /benchmarks/
	@RequestMapping("/dfsiobenchmarks")
	public ModelAndView benchmarksById(@RequestParam("id") String id) {
		ArrayList<BenchmarkResult> results = (ArrayList<BenchmarkResult>) benchmarkResultService.listClusterBenchmarkResultByDate(id);

		ModelAndView mv = new ModelAndView("dfsiobenchmarks");
		mv.addObject("cluster",  clusterService.getCluster(id));
		mv.addObject("clusters", clusterService.listClusterById(id));
		mv.addObject("dfsio", results);
		return mv;
	}

	// GET /benchmarks?id={id}
	@RequestMapping(value = "/benchmark", method = RequestMethod.GET)
	public ModelAndView benchmark(@RequestParam("id") String id) {
		ModelAndView mv;
		// Get benchmark result
		BenchmarkResult result = benchmarkResultService.getBenchmarkResult(id);
		if(result != null) {
			mv = new ModelAndView("benchmark");
			mv.addObject("benchmark", result);
		} else {
			mv = new ModelAndView("error");
			mv.addObject("message", "Benchmark not found");
		}
		return mv;
	}

	// GET /benchmarks?id={id}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") String id, @RequestParam("clusterId") String clusterId) {

		// Get benchmark result

		BenchmarkResult result = benchmarkResultService.getBenchmarkResult(id); 
		benchmarkResultService.deleteBenchmarkResult(result);
		return new ModelAndView("redirect:/dfsio/dfsiobenchmarks?id="+clusterId);
	}

//	private BenchmarkResult benchmarkDFSIO(String id, int numFiles, int fileSize) {
//		Cluster cluster = clusterService.getCluster(id);
//		BenchmarkResult result = cluster.runDFSIOBenchmark(numFiles, fileSize);
//		result.setClusterName(cluster.getName());
//		return result;
//	}
	


	private BenchmarkResult benchmarkDFSIOAsync(String id, int numFiles, int fileSize) throws IOException {
		Cluster cluster = clusterService.getCluster(id);
		BenchmarkResult result = cluster.runDFSIOBenchmark(numFiles, fileSize);
		result.setClusterName(cluster.getName());
		result.setClusterId(cluster.getId());

		// Set flag for poor performance
		//		if(Double.parseDouble(result.getThroughputMb()) < Double.parseDouble(cluster.getThroughputThreshold()))
		//			result.setAlarm(true);
		//		else
		//			result.setAlarm(false);
		return result;
	}
	

	// Runs DFSIO benchmark via command line
	@SuppressWarnings("unused")
	private void benchmarkRuntime() {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PumpStreamHandler strHandler = new PumpStreamHandler(outStream);
			CommandLine cmdLine = CommandLine.parse("./dfsio.sh");
			DefaultExecutor exec = new DefaultExecutor();
			exec.setStreamHandler(strHandler);
			exec.setExitValue(0);
			exec.setWorkingDirectory(new File("/home/hadoop/hadoop/share/hadoop/mapreduce"));
			int exitCode = exec.execute(cmdLine);

			System.out.println("OUT " + outStream.toString());
			System.out.println("EXIT VAL " + exitCode);

		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}