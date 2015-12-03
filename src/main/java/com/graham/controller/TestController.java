package com.graham.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.graham.model.BenchmarkResult;
import com.graham.model.BenchmarkResultService;
import com.graham.model.Cluster;
import com.graham.model.ClusterManager;

@Controller
public class TestController {

	@Autowired
	private BenchmarkResultService benchmarkResultService;
	
	// GET /test/
	@RequestMapping("/test")
	public ModelAndView testAction() {
		System.out.println("in controller");
		
		BenchmarkResult result = benchmarkDFSIO();
		benchmarkResultService.addBenchmarkResult(result);
		
		ModelAndView mv = new ModelAndView("test");
		mv.addObject("threadOut", "hello");
		mv.addObject("bresult", result);
		return mv;
	}
	
	// GET /test/
	@RequestMapping("/dfsio")
	public @ResponseBody BenchmarkResult dfsioAsync() {
		System.out.println("in controller");
		
		BenchmarkResult result = benchmarkDFSIOAsync();
		benchmarkResultService.addBenchmarkResult(result);
		
		return result;
	}
	
	// GET /
	@RequestMapping("/")
	public ModelAndView index() {
		
		ArrayList<BenchmarkResult> results = (ArrayList<BenchmarkResult>) benchmarkResultService.listBenchmarkResultByDate();
		
		ModelAndView mv = new ModelAndView("benchmarks");
		mv.addObject("benchmarks", results);
		return mv;
	}
	
	// GET /benchmarks/
	@RequestMapping("/benchmarks")
	public ModelAndView benchmarks() {

		ArrayList<BenchmarkResult> results = (ArrayList<BenchmarkResult>) benchmarkResultService.listBenchmarkResultByDate();
		
		ModelAndView mv = new ModelAndView("benchmarks");
		mv.addObject("benchmarks", results);
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
		public ModelAndView delete(@RequestParam("id") String id) {
			
			// Get benchmark result
			
			BenchmarkResult result = benchmarkResultService.getBenchmarkResult(id); 
			benchmarkResultService.deleteBenchmarkResult(result);
			return new ModelAndView("redirect:/benchmarks");
		}
		
		@RequestMapping(value = "/testJSON", method = RequestMethod.GET)
		public @ResponseBody BenchmarkResult testJSON() {
		
		
			return new BenchmarkResult("TEST", "TEST", "TEST", "TEST", "TEST", "TEST", "TEST", "TEST");
		}

	private BenchmarkResult benchmarkDFSIO() {
		ClusterManager manager = new ClusterManager();
		Cluster cluster = manager.getCluster();
		return cluster.runDFSIOBenchmark();
	}

	private BenchmarkResult benchmarkDFSIOAsync() {
		ClusterManager manager = new ClusterManager();
		Cluster cluster = manager.getCluster();
		return cluster.runDFSIOBenchmarkAsync();
	}

	// Runs DFSIO benchmark via command line
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
			e.printStackTrace();
		}

	}
}