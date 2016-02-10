package com.graham.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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


	// GET /
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("login");
		return mv;	
	}

	// POST /test/
	@RequestMapping("/mrbench")
	public @ResponseBody MRBenchmarkResult mrBenchAsync(String id, int numRuns) {
		System.out.println("in controller");

		MRBenchmarkResult result = benchmarkMRBenchAsync(id, numRuns);
		benchmarkResultService.addBenchmarkResult(result);

		return result;
	}

	// GET /benchmarks/
	@RequestMapping("/mrbenchmarks")
	public ModelAndView benchmarksById(@RequestParam("id") String id) {
		// Get all MRBenchmarks
		ArrayList<MRBenchmarkResult> results = (ArrayList<MRBenchmarkResult>) benchmarkResultService.listClusterBenchmarkResultByDate(id);

		ModelAndView mv = new ModelAndView("mrbench/benchmarks");
		mv.addObject("cluster",  clusterService.getCluster(id));
		mv.addObject("clusters", clusterService.listClusterById(id));
		mv.addObject("mrbench", results);
		return mv;
	}

	// GET /benchmarks?id={id}
	@RequestMapping(value = "/benchmark", method = RequestMethod.GET)
	public ModelAndView benchmark(@RequestParam("id") String id) {

		ModelAndView mv;

		// Get benchmark result
		MRBenchmarkResult result = benchmarkResultService.getBenchmarkResult(id);
		if(result != null) {
			mv = new ModelAndView("mrbench/benchmark");
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
		MRBenchmarkResult result = benchmarkResultService.getBenchmarkResult(id); 
		benchmarkResultService.deleteBenchmarkResult(result);
		return new ModelAndView("redirect:mrbenchmarks?id=" + result.getClusterId());
	}

	private MRBenchmarkResult benchmarkMRBenchAsync(String id, int numRuns) {
		Cluster cluster = clusterService.getCluster(id);
		MRBenchmarkResult result = cluster.runMRBenchmarkAsync(numRuns);
		result.setClusterName(cluster.getName());
		result.setClusterId(cluster.getId());
		return result;
	}

}