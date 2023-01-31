package com.ticketbooking.dto.request;


import com.ticketbooking.model.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRequest {

	private String username;
	private String email;
	private String password;
	private String mobileNumber;
	private UserType userType;

}
