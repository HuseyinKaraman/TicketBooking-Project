package com.ticketbooking.listener;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ticketbooking.listener.model.TicketDetail;
import com.ticketbooking.model.enums.NotificationType;
import com.ticketbooking.service.NotificationService;

@Component
public class NotificationListener {

	@Autowired
	private NotificationService notificationService;
	
	@RabbitListener(queues = "ticketbooking.notification.email")
	public void userNotificationListener(String email) {
		notificationService.create(NotificationType.EMAIL, email);
		notificationService.create(NotificationType.PUSH, email);
		
	}
	
	@RabbitListener(queues = "ticketbooking.notification.ticket")
	public void ticketNotificationListener(List<TicketDetail> ticketDetails) {
		notificationService.create(NotificationType.SMS, ticketDetails.toString());
		notificationService.create(NotificationType.PUSH, ticketDetails.toString());
	}
}
