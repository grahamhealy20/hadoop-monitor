package com.graham.controller;

import java.util.ArrayList;
import java.util.List;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;

import com.graham.model.Alert;
import com.graham.model.Cluster;
import com.graham.model.dbaccess.ClusterService;
import com.graham.model.metrics.Metrics;
import com.graham.model.utils.HttpHelper;
import com.graham.model.utils.RulesParser;

// Controller to handle metrics
@Controller
public class MetricsController implements ApplicationListener<BrokerAvailabilityEvent>{
	private final int DELAY = 1000;
	
	@Autowired
	private ClusterService clusterService;

	@Autowired
	private MessageSendingOperations<String> messagingTemplate;

	private HttpHelper http = new HttpHelper();
	
	// Publishes cluster metrics every set interval
	@Scheduled(fixedDelay = DELAY)
	public void sendDataUpdates() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		// Grab clusters
		ArrayList<Cluster> clusters = (ArrayList<Cluster>) clusterService.listClusters();

		for (Cluster cluster : clusters) {
			try {
				// Get metrics
				Metrics metrics = http.downloadJmxMetrics(cluster.getIpAddress());
				
				// Parse for rules
				
				List<Alert> alerts = RulesParser.parseRules(metrics, cluster.getRules());
				
				// If alerts are not null send out websocket
				if(alerts != null) {
					Log.info("Sending alerts " + alerts);
					
					//Save alerts to cluster
					for(Alert alert : alerts) {
						cluster.getAlerts().add(alert);
					}
					clusterService.updateCluster(cluster);
					//this.alertTemplate.convertAndSend("/alerts/" + cluster.getId(), alerts);
				}
				
				//Applications apps = http.downloadClusterApps(cluster.getIpAddress());
				this.messagingTemplate.convertAndSend("/data/" + cluster.getId(), metrics);
				Thread.sleep(10000);
			} catch (ResourceAccessException e) {
				// TODO: handle exception
				//e.printStackTrace();
			}
		}
	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent arg0) {
		// TODO Auto-generated method stub
	}
}
