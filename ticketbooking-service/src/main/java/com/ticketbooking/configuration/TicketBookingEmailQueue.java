package com.ticketbooking.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketBookingEmailQueue {

	@Value("${rabbitmq.queue}")
	private String queueName;

	@Value("${rabbitmq.exchange}")
	private String exchange;

	@Value("${rabbitmq.routingkey}")
	private String routingkey;

	@Bean
	Queue userQueue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange userExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue userQueue, DirectExchange userExchange) {
		return BindingBuilder.bind(userQueue).to(userExchange).with(routingkey);
	}

	@Bean
	MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public String getQueueName() {
		return queueName;
	}

	public String getExchange() {
		return exchange;
	}

}
