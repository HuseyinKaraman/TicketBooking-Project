package com.ticketbooking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ticketbooking.model.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>{

}
