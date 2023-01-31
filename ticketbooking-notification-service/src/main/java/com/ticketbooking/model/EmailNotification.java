package com.ticketbooking.model;

import java.time.LocalDateTime;

import com.ticketbooking.model.enums.NotificationType;

public class EmailNotification extends Notification{

	public EmailNotification(String notificationMessage) {
		this.notificationMessage = notificationMessage;
		this.notificationType = NotificationType.EMAIL;
		this.notificationTime = LocalDateTime.now();
	}
	
}
