package com.ticketbooking.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbooking.dto.request.LoginRequest;
import com.ticketbooking.dto.request.UserRequest;
import com.ticketbooking.dto.response.UserResponse;
import com.ticketbooking.model.User;
import com.ticketbooking.model.enums.UserType;
import com.ticketbooking.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void it_should_create() throws Exception {

		Mockito.when(userService.create(Mockito.any(UserRequest.class))).thenReturn(getUserResponse(1));

		String request = mapper.writeValueAsString(getUserRequest());

		ResultActions resultActions = mockMvc
				.perform(post("/users").content(request).contentType(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.username").value("test")).andExpect(jsonPath("$.email").value("test@gmail.com"))
				.andExpect(jsonPath("$.userType").value(UserType.INDIVIDUAL.toString()));
	}

	@Test
	public void it_should_get_by_user_id() throws Exception {
		// given
		Mockito.when(userService.findById(Mockito.eq(1))).thenReturn(getUser(1));

		// when
		ResultActions resultActions = mockMvc.perform(get("/users/1"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.username").value("test")).andExpect(jsonPath("$.email").value("test@gmail.com"))
				.andExpect(jsonPath("$.userType").value(UserType.INDIVIDUAL.toString()));

	}

	@Test
	public void it_should_get_users() throws Exception {
		// given
		Mockito.when(userService.findAll()).thenReturn(getAllUserResponses());

		// when
		ResultActions resultActions = mockMvc.perform(get("/users"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].username").value("test"))
				.andExpect(jsonPath("$[0].email").value("test@gmail.com"))
				.andExpect(jsonPath("$[0].userType").value(UserType.INDIVIDUAL.toString()));

	}

	@Test
	public void it_should_login() throws Exception {
		// given
		Mockito.when(userService.login(Mockito.any(LoginRequest.class))).thenReturn("LOGIN BASARILI");

		String request = mapper.writeValueAsString(getLoginRequest());
		// when
		ResultActions resultActions = mockMvc
				.perform(post("/users/login").content(request).contentType(MediaType.APPLICATION_JSON));
		// then
		resultActions.andExpect(status().isOk());

	}

	@Test
	public void it_should_loggedIn() throws Exception {
		// given
		Mockito.when(userService.isLoggedIn(Mockito.anyString())).thenReturn("KULLANICI GIRIS YAPMIS DURUMDA");

		// when
		ResultActions resultActions = mockMvc.perform(get("/users/islogged/test@gmail.com"));
		// then
		resultActions.andExpect(status().isOk());

	}

	private UserRequest getUserRequest() {
		return UserRequest.builder().username("test").email("test@gmail.com").password("hashPassword")
				.mobileNumber("123456789").userType(UserType.INDIVIDUAL).build();
	}

	private List<UserResponse> getAllUserResponses() {
		return List.of(getUserResponse(1));
	}

	private UserResponse getUserResponse(int id) {
		return UserResponse.builder().id(id).username("test").email("test@gmail.com").mobileNumber("123456789")
				.userType(UserType.INDIVIDUAL).build();
	}

	private User getUser(int id) {
		return User.builder().id(id).username("test").email("test@gmail.com").mobileNumber("123456789")
				.userType(UserType.INDIVIDUAL).build();
	}

	private LoginRequest getLoginRequest() {
		return LoginRequest.builder().email("test@gmail.com").password("hashPassword").build();
	}

}
