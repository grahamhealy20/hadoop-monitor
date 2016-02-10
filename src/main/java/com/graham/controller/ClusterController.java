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
import com.graham.model.dbaccess.ClusterService;
import com.graham.model.utils.HttpHelper;


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
	
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	public String saveCluster(@RequestParam("name") String name, @RequestParam("ipAddress") String ipAddress) {
		Cluster cluster = new Cluster(name, ipAddress);
		
		clusterService.addCluster(cluster);
		return "redirect:/cluster/";
	}

	@RequestMapping(value = "/updateCluster", method = RequestMethod.POST)
	public String updateCluster(@RequestParam("id") String id, @RequestParam("clusterName") String name, @RequestParam("ipAddress") String ipAddress, @RequestParam("throughputThreshold") String throughputThreshold) {

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
	
	@RequestMapping(value = "/log/namenode", produces="text/plain")
	public @ResponseBody byte[] viewNamenodeLog(@RequestParam("id") String id) {
		Cluster cluster = clusterService.getCluster(id);
		HttpHelper http = new HttpHelper();

		//Download logs
		String namenodeLog = http.downloadNameNodeLog(cluster.getIpAddress());
		return namenodeLog.getBytes();
	}
	

	@RequestMapping(value = "/log/datanode", produces="text/plain")
	public @ResponseBody byte[] viewDatanodeLog(@RequestParam("id") String id) {
		Cluster cluster = clusterService.getCluster(id);
		HttpHelper http = new HttpHelper();

		//Download logs
		String log = http.downloadDataNodeLog(cluster.getIpAddress());
		return log.getBytes();
	}
}
