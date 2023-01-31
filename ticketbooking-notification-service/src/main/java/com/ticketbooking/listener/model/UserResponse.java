package com.ticketbooking.listener.model;



import com.ticketbooking.listener.model.enums.UserType;

import lombok.Data;

public @Data class UserResponse {

	private int id;
	private String username;
	private String email;
	private String mobileNumber;
	private UserType userType;
}
