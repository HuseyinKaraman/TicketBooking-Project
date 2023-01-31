package com.ticketbooking.model;

import java.time.LocalDateTime;

import com.ticketbooking.model.enums.NotificationType;

public class SmsNotification extends Notification {

	public SmsNotification(String notificationMessage) {
		this.notificationMessage = notificationMessage;
		this.notificationType = NotificationType.SMS;
		this.notificationTime = LocalDateTime.now();
	}
	
}
