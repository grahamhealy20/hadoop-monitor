package com.graham.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.graham.model.BenchmarkResult;
import com.graham.model.Cluster;
import com.graham.model.dbaccess.ClusterService;


@Controller
@RequestMapping("cluster")
public class ClusterController {
	
	@Autowired
	private ClusterService clusterService;
	
	@RequestMapping("/")
	public String index() {
		
		return "redirect:clusters";
	}
	
	@RequestMapping("/clusters")
	public ModelAndView clusters() {
		ArrayList<Cluster> clusters = (ArrayList<Cluster>) clusterService.listClusters();
		
		ModelAndView mv = new ModelAndView("clusters");
		mv.addObject("clusters", clusters);
		return mv;
	}
	
	@RequestMapping("/add")
	public ModelAndView addCluster() {
		ModelAndView mv = new ModelAndView("addCluster");
		return mv;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView saveCluster(@RequestParam("name") String name, @RequestParam("ipAddress") String ipAddress) {
		Cluster cluster = new Cluster(name, ipAddress);
		clusterService.addCluster(cluster);
		ModelAndView mv = new ModelAndView("/cluster/success");
		mv.addObject("cluster", cluster);
		
		return mv;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") String id) {
		
		// Get cluster
		Cluster cluster = clusterService.getCluster(id); 
		clusterService.deleteCluster(cluster);
		return new ModelAndView("redirect:/cluster/clusters");
	}
	
	@RequestMapping("/cluster")
	public ModelAndView cluster(@RequestParam("id") String id) {
		
		//Get cluster
		Cluster cluster = clusterService.getCluster(id);
		ModelAndView mv = new ModelAndView("overview");
	    return mv;	
	}
	
	
	
	
	
	
	
}
