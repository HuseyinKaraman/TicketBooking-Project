package com.ticketbooking.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketBookingTicketQueue {
	
	private String ticketQueueName= "ticketbooking.notification.ticket";

	private String ticketExchange = "ticketbooking.notification.ticket";

	@Bean
	Queue ticketQueue() {
		return new Queue(ticketQueueName, false);
	}

	@Bean
	DirectExchange ticketExchange() {
		return new DirectExchange(ticketExchange);
	}

	@Bean
	Binding binding(Queue ticketQueue, DirectExchange ticketExchange) {
		return BindingBuilder.bind(ticketQueue).to(ticketExchange).with("");
	}

	@Bean
	MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public String getTicketQueueName() {
		return ticketQueueName;
	}

	public String getTicketExchange() {
		return ticketExchange;
	}

}
