package com.ticketbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TicketbookingEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketbookingEurekaServerApplication.class, args);
	}

}
