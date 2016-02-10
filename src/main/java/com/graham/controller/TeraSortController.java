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
import com.graham.model.benchmarks.TeraSortBenchmarkResult;
import com.graham.model.dbaccess.ClusterService;
import com.graham.model.dbaccess.TeraSortBenchmarkResultService;

@Controller
@RequestMapping("terasort")
public class TeraSortController {

	@Autowired
	private TeraSortBenchmarkResultService benchmarkResultService;

	@Autowired
	private ClusterService clusterService;

	// GET /
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("login");
		return mv;	
	}

	// GET /test/
	@RequestMapping("/terasort")
	public @ResponseBody void teraSortAsync(String id, int size) {
		System.out.println("in controller");

		benchmarkTerasortAsync(id, size);
		//benchmarkResultService.addBenchmarkResult(result);

		//return result;
	}

	// GET /benchmarks/
	@RequestMapping("/benchmarks")
	public ModelAndView benchmarksById(@RequestParam("id") String id) {
		// Get all MRBenchmarks
		ArrayList<TeraSortBenchmarkResult> results = (ArrayList<TeraSortBenchmarkResult>) benchmarkResultService.listClusterBenchmarkResultByDate(id);

		ModelAndView mv = new ModelAndView("terasort/benchmarks");
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
		TeraSortBenchmarkResult result = benchmarkResultService.getBenchmarkResult(id);
		if(result != null) {
			mv = new ModelAndView("terasort/benchmark");
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
		TeraSortBenchmarkResult result = benchmarkResultService.getBenchmarkResult(id); 
		benchmarkResultService.deleteBenchmarkResult(result);
		return new ModelAndView("redirect:/benchmarks");
	}

	private void benchmarkTerasortAsync(String id, int size) {
		Cluster cluster = clusterService.getCluster(id);
		cluster.runTeraSortBenchmark(size);
		//result.setClusterName(cluster.getName());
		//result.setClusterId(cluster.getId());
	}

}