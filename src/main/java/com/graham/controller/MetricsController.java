package com.graham.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.graham.model.Cluster;
import com.graham.model.dbaccess.ClusterService;
import com.graham.model.metrics.Beans;
import com.graham.model.utils.HttpHelper;

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
	public void sendDataUpdates() {
		// Grab clusters
		ArrayList<Cluster> clusters = (ArrayList<Cluster>) clusterService.listClusters();

		for (Cluster cluster : clusters) {
			Beans metrics = http.downloadJmxMetrics(cluster.getIpAddress());
			this.messagingTemplate.convertAndSend("/data/" + cluster.getId(), metrics);
		}
	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent arg0) {
		// TODO Auto-generated method stub
	}
}
