package com.ticketbooking.model;

import java.time.LocalDateTime;

import com.ticketbooking.model.enums.NotificationType;

public class PushNotification extends Notification{

	public PushNotification(String notificationMessage) {
		this.notificationMessage = notificationMessage;
		this.notificationType = NotificationType.PUSH;
		this.notificationTime = LocalDateTime.now();
	}
	
	
}
