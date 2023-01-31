package com.ticketbooking.converter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ticketbooking.dto.request.UserRequest;
import com.ticketbooking.dto.response.UserResponse;
import com.ticketbooking.dto.response.UserResponse.UserResponseBuilder;
import com.ticketbooking.model.User;
import com.ticketbooking.model.User.UserBuilder;

@Component
public class UserConverter {

	public UserConverter() {
		// TODO Auto-generated constructor stub
	}

	public UserResponse convert(User user) {
		UserResponseBuilder responseBuilder = UserResponse.builder().email(user.getEmail()).id(user.getId())
				.username(user.getUsername()).userType(user.getUserType()).mobileNumber(user.getMobileNumber())
				.createDate(user.getCreateDate());
		return responseBuilder.build();
	}

	public User convert(UserRequest userRequest, String hash) {
		UserBuilder userBuilder = User.builder().username(userRequest.getUsername()).email(userRequest.getEmail())
				.password(hash).userType(userRequest.getUserType()).createDate(LocalDateTime.now()).mobileNumber(userRequest.getMobileNumber());
		return userBuilder.build();
	}

	public List<UserResponse> convert(List<User> userList) {
		return userList.stream().map(this::convert).toList();

	}

}
