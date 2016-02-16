package com.graham.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//Class used to manage notifications
@Controller
@RequestMapping("notification")
public class NotificationController {

	@Autowired
	private MessageSendingOperations<String> messagingTemplate;
	
	// Function used to emit notifications via a websocket to a user subscribed
	public void sendNotification() {
		this.messagingTemplate.convertAndSend("/notifications/userid", "This is a notification");
	}
}
