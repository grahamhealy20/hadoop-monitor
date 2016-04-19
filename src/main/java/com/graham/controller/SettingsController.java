package com.graham.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graham.model.dbaccess.MetricsService;
import com.graham.model.Metric;

/* 
 * Represents general settings for the application
 * */
@Controller
@RequestMapping("settings")
public class SettingsController {
	// List of metrics for rules/alerts
	
	@Autowired
	private MetricsService metricService;
	
	@RequestMapping("/metrics")
	public @ResponseBody ResponseEntity<List<Metric>> getMetrics() {
		List<Metric> metrics = metricService.listMetrics();
		if(metrics.size() == 0) {
			return new ResponseEntity<List<Metric>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Metric>>(metricService.listMetrics(), HttpStatus.OK);
	}
	
	@RequestMapping( value = "/metric", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Metric> addMetric(@RequestBody Metric metric) {
		Metric m = metricService.addMetric(metric); 
		if(m.getId() != null) {
			return new ResponseEntity<Metric>(metric, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<Metric>(metric, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/metric/delete", method = RequestMethod.POST)
	public ResponseEntity<String> deleteMetric(@RequestBody Metric metric) {		
		metricService.deleteMetric(metric);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/metric/edit", method = RequestMethod.PUT)
	public ResponseEntity<Metric> editMetric(@RequestBody Metric metric) {		
		metricService.updateMetric(metric);
		return new ResponseEntity<Metric>(metric, HttpStatus.OK);
	}
}
