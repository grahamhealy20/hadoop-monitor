package com.graham.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
		
		ModelAndView mv = new ModelAndView("/cluster/clusters");
		mv.addObject("clusters", clusters);
		return mv;
	}
	
	@RequestMapping("/add")
	public ModelAndView addCluster() {
		ModelAndView mv = new ModelAndView("/cluster/addCluster");
		return mv;
	}
	
	@RequestMapping(value = "/updateCluster", method = RequestMethod.POST)
	public String saveCluster(@RequestParam("id") String id, @RequestParam("clusterName") String name, @RequestParam("ipAddress") String ipAddress, @RequestParam("throughputThreshold") String throughputThreshold) {
		
		Cluster cluster = clusterService.getCluster(id);
		
		// Edit variables
		cluster.setName(name);
		cluster.setIpAddress(ipAddress);
		cluster.setThroughputThreshold(throughputThreshold);
		
		// Update DB
		clusterService.updateCluster(cluster);
		
		return "redirect:/cluster/configure?id=" + id;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") String id) {
		Cluster cluster = clusterService.getCluster(id); 
		clusterService.deleteCluster(cluster);
		return new ModelAndView("redirect:/cluster/clusters");
	}
	
	@RequestMapping("/cluster")
	public ModelAndView cluster(@RequestParam("id") String id) {
		//Get cluster
		Cluster cluster = clusterService.getCluster(id);
		
		ModelAndView mv = new ModelAndView("/cluster/overview");
		mv.addObject("cluster", cluster);
	    return mv;	
	}
	
	@RequestMapping("/configure")
	public ModelAndView configureCluster(@RequestParam("id") String id) {
		//Get cluster
		Cluster cluster = clusterService.getCluster(id);
		
		ModelAndView mv = new ModelAndView("/cluster/configure");
		mv.addObject("cluster", cluster);
	    return mv;	
	}
	
	
	
	
	
	
	
}
