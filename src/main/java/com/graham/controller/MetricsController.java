package com.graham.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.graham.model.Alert;
import com.graham.model.Cluster;
import com.graham.model.LayoutMetric;
import com.graham.model.dbaccess.ClusterService;
import com.graham.model.metrics.Metrics;
import com.graham.model.utils.HttpHelper;
import com.graham.model.utils.MetricsHelper;
import com.graham.model.utils.RulesParser;

// Controller to handle metrics
@Service
public class MetricsController implements ApplicationListener<BrokerAvailabilityEvent>{

	@Autowired
	private ClusterService clusterService;

	@Autowired
	private MessageSendingOperations<String> messagingTemplate;

	@Autowired
	private MailSender mailSender;
	
	private HttpHelper http = new HttpHelper();

	private ArrayList<Alert> alertHistory = new ArrayList<Alert>();

	// Publishes cluster metrics every set interval
	@Scheduled(fixedDelay = 1000)
	public void sendDataUpdates() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		// Grab clusters
		ArrayList<Cluster> clusters = (ArrayList<Cluster>) clusterService.listClusters();

		for (Cluster cluster : clusters) {
			try {
				// Get metrics
				Metrics metrics = http.downloadJmxMetrics(cluster.getIpAddress());				

				// Parse for rule alerts
				List<Alert> alerts = RulesParser.parseRules(metrics, cluster.getRules());

				// If alerts are not null send out websocket
				if(alerts != null) {													
					// If alert is not recent, do action
					for(Alert alert : alerts) {
						if(hasRecentAlert(alert, 60000) == false) {
							Log.info("Adding new hist");
							//Remove current hist alert and pop in new one							
							alertHistory.add(alert);
							cluster.getAlerts().add(alert);	
							alert.setId(UUID.randomUUID().toString());
							clusterService.updateCluster(cluster);
							this.messagingTemplate.convertAndSend("/alerts/", alert);

							// Perform action
							if(alert.getAction().toLowerCase().equals("email")) {
								// Send email
								Log.info("EMAIL EMAIL EMAIL");
								
					
								String from = "graham.y4.project@gmail.com";
								String to = "grahamh220@gmail.com";
								String subject = "Hadoop - Alert";
								String body = "Alert! " + alert.getName() + " is " + alert.getValue();
								
								SimpleMailMessage message = new SimpleMailMessage();
								message.setFrom(from);
								message.setTo(to);
								message.setSubject(subject);
								message.setText(body);
								mailSender.send(message);
								
							} else if (alert.getAction() == "rebalance") {
								// Rebalance cluster
							}
						}
					}														
				}

				this.messagingTemplate.convertAndSend("/data/" + cluster.getId(), metrics);			
			} catch (ResourceAccessException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	@Scheduled(fixedDelay = 1500)
	public void getLayoutMetrics() throws IllegalAccessException {
		// Grab clusters
		ArrayList<Cluster> clusters = (ArrayList<Cluster>) clusterService.listClusters();
		
		for (Cluster cluster : clusters) {
			try {
				// Get metrics
				Metrics metrics = http.downloadJmxMetrics(cluster.getIpAddress());				
				
				// Process into small list of metrics
				List<LayoutMetric> metricList = MetricsHelper.parseMetrics(metrics, cluster.getLayout());
				if(metricList.size() > 0) {
					Log.info("ROW: " + metricList.get(0).getRow());
					this.messagingTemplate.convertAndSend("/data/layout/" + cluster.getId(), metricList);
				}										
			} catch (ResourceAccessException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	private boolean hasRecentAlert(Alert alert, long recent) {

		for(int i = 0; i < alertHistory.size(); i++ ){
			Alert previous = alertHistory.get(i);

			// Check for matching alert in the history
			if(previous.getKey() == alert.getKey()) {
				Log.info("" + System.currentTimeMillis());
				long currentAlertTime = alert.getTimestamp();
				long previousAlertTime = previous.getTimestamp();			
				long timeGap = currentAlertTime - previousAlertTime;			
				//Log.info("Key: " + alert.getKey()+ " GAP Time: " + timeGap);

				//Check if recent
				if(timeGap < recent) {					
					// Has a recent alert, no alert will be added
					return true;
				} else {
					Log.info("Not recent, updating new history");
					alertHistory.remove(i);
					return false;
				}
			}
		}
		return false;
	}

	@Scheduled(fixedDelay = 1000)
	public void getMetrics() {

	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent arg0) {
		// TODO Auto-generated method stub
	}
}
