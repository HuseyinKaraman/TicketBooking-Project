package com.ticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TicketbookingGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketbookingGatewayApplication.class, args);
	}

}
