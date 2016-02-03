package com.graham.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.graham.model.metrics.MetricTest;

// Controller to handle metrics
@Controller
public class MetricsController implements ApplicationListener<BrokerAvailabilityEvent>{
	private final int DELAY = 1000;

	@Autowired
	private MessageSendingOperations<String> messagingTemplate;

	// Publishes cluster metrics every set interval
	@Scheduled(fixedDelay = DELAY)
	public void sendDataUpdates() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		String dateStr = dateFormat.format(date);
		MetricTest mt = new MetricTest(dateStr);

		// Get clusters, loop over each and return metrics on separate channels
		for(int i = 0; i < 3; i++) {			
			System.out.println("Sending Cluster Metrics " + i);
			this.messagingTemplate.convertAndSend("/data/" + i, mt);
		}
	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent arg0) {
		// TODO Auto-generated method stub
	}
}
