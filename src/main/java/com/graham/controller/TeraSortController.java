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
import com.graham.model.dbaccess.*;
import com.graham.model.Cluster;
import com.graham.model.ClusterManager;
import com.graham.model.MRBenchThread;

@Controller
@RequestMapping("terasort")
public class TeraSortController {

	@Autowired
	private BenchmarkResultService benchmarkResultService;

	@Autowired
	private ClusterService clusterService;
	
	// GET /test/
	//@RequestMapping("/terasort")
	//public @ResponseBody BenchmarkResult teraSortAsync(String id, int numFiles, int fileSize) {
		//System.out.println("in controller");
		
		//BenchmarkResult result = benchmarkDFSIOAsync(id, numFiles, fileSize);
		//benchmarkResultService.addBenchmarkResult(result);
		
		//return result;
	//}
	
	// GET /
	@RequestMapping("/")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("login");
		//mv.addObject("benchmarks", results);
		return mv;
		
	}
	
	@RequestMapping("/teraSort")
	public ModelAndView teraSortBenchmark() {
		ModelAndView mv = new ModelAndView("teraSort");
		return mv;	
	}
	
	@RequestMapping("/runTeraSort")
	public ModelAndView teraSortRes() {
		ModelAndView mv = new ModelAndView("teraSortResult");
		benchmarkTeraSort();
		
		//MRBenchThread mr = new MRBenchThread();
		//mr.run();
	
		return mv;
	}
	
	
	// GET /benchmarks/
	@RequestMapping("/TeraBenchmarks")
	public ModelAndView benchmarks() {
		ArrayList<BenchmarkResult> results = (ArrayList<BenchmarkResult>) benchmarkResultService.listBenchmarkResultByDate();
		
		ModelAndView mv = new ModelAndView("TeraSort");
		mv.addObject("clusters", clusterService.listClusters());
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

	
	private void benchmarkTeraSort() {
		ClusterManager manager = new ClusterManager();
		Cluster cluster = manager.getCluster();
		cluster.setIpAddress("192.168.0.106");
		cluster.setUsername("hadoop");
		cluster.runTeraSort();
	}
}