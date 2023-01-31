package com.ticketbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.dto.request.LoginRequest;
import com.ticketbooking.dto.request.UserRequest;
import com.ticketbooking.dto.response.UserResponse;
import com.ticketbooking.model.User;
import com.ticketbooking.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	
	/** @Note: yeni kullanıcı oluşturur */
	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
		return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
	}

	/** @Note: Kayıtlı kullanıcıları verecektir */
	@GetMapping
	public List<UserResponse> getAll() {
		return userService.findAll();
	}
	
	/** @Note: Id'si girilen kullanıcıyı verir */
	@GetMapping(value = "/{userId}")
	public User getByUserId(@PathVariable Integer userId) {
		return userService.findById(userId);
	}
	@PostMapping(value = "/login")
	public ResponseEntity<String> Login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(userService.login(loginRequest));
	}
	
	@GetMapping(value = "/islogged/{email}")
	public ResponseEntity<String> LoggedUser(@PathVariable String email) {
		return ResponseEntity.ok(userService.isLoggedIn(email));
	}
	
}
