package com.ticketbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketbooking.exception.NotificationException;
import com.ticketbooking.model.EmailNotification;
import com.ticketbooking.model.Notification;
import com.ticketbooking.model.PushNotification;
import com.ticketbooking.model.SmsNotification;
import com.ticketbooking.model.enums.NotificationType;
import com.ticketbooking.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository; 
 	
	public Notification create(NotificationType notificationType, String message) {
		Notification notification = null;
		try {
			if (NotificationType.EMAIL.equals(notificationType)) {
				notification = new EmailNotification("Please confirm your email address : "+ message);
			}else if(NotificationType.SMS.equals(notificationType)) {
				notification = new SmsNotification("Ticket list you have taken : "+message);
			}else if(NotificationType.PUSH.equals(notificationType)) {
				notification = new PushNotification(message);
			}
			notification = notificationRepository.save(notification);
		} catch (Exception e) {
			throw new NotificationException("Notification Exception!");
		}
		return notification;
	}
	
}
