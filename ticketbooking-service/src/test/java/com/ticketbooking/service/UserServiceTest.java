package com.ticketbooking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ticketbooking.configuration.TicketBookingEmailQueue;
import com.ticketbooking.converter.UserConverter;
import com.ticketbooking.dto.request.LoginRequest;
import com.ticketbooking.dto.request.UserRequest;
import com.ticketbooking.dto.response.UserResponse;
import com.ticketbooking.exception.TicketBookingServiceException;
import com.ticketbooking.model.User;
import com.ticketbooking.model.enums.UserType;
import com.ticketbooking.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserConverter converter;
	@Mock
	private CacheManager cacheManager;
	@Mock
	private RabbitTemplate rabbitTemplate;
	@Mock
	private TicketBookingEmailQueue emailQueue;

	@Test
	void it_should_create() {

		// given
		Mockito.when(converter.convert(Mockito.any(UserRequest.class), Mockito.anyString())).thenReturn(new User());
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser(1, UserType.INDIVIDUAL));
		Mockito.when(converter.convert(Mockito.any(User.class))).thenReturn(getUserResponse(1));
		Mockito.when(emailQueue.getQueueName()).thenReturn("ticketbooking.notification.email");
		// when
		UserResponse userResponse = userService.create(getUserRequest());
		// then
		assertThat(userResponse).isNotNull();
		assertThat(userResponse.getUsername()).isEqualTo(getUser(1, UserType.INDIVIDUAL).getUsername());
		assertThat(userResponse.getEmail()).isEqualTo(getUser(1, UserType.INDIVIDUAL).getEmail());
		assertThat(userResponse.getUserType()).isEqualTo(getUser(1, UserType.INDIVIDUAL).getUserType());

		verify(rabbitTemplate, times(1)).convertAndSend(Mockito.anyString(), Mockito.anyString());
		verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	void it_should_get_all_users() {
		Mockito.when(userRepository.findAll()).thenReturn(getUsers());
		Mockito.when(converter.convert(Mockito.anyList())).thenReturn(getUserResponses());
		
		List<UserResponse> findAll = userService.findAll();
	
		assertThat(findAll).isNotEmpty();
		assertThat(findAll.size()).isEqualTo(6);
		assertThat(findAll.get(0).getEmail()).isEqualTo(getUser(1, UserType.ADMIN).getEmail());
		assertThat(findAll.get(0).getMobileNumber()).isEqualTo(getUser(1, UserType.ADMIN).getMobileNumber());
		assertThat(findAll.get(0).getId()).isEqualTo(getUser(1, UserType.ADMIN).getId());
	}
	
	
	
	@Test
	void it_should_throw_exception_when_user_is_not_found() {
		Throwable exception = catchThrowable(() -> userService.findById(Mockito.anyInt()));
		assertThat(exception).isInstanceOf(TicketBookingServiceException.class);
	}

	@Test
	public void it_should_throw_exception_when_user_not_admin() {
		Optional<User> optional = getOptionalUser();
		optional.get().setUserType(UserType.INDIVIDUAL);
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(optional);

		Throwable exception = catchThrowable(() -> userService.isAdminUser(Mockito.eq(1)));
		assertThat(exception).isInstanceOf(TicketBookingServiceException.class);
	}

	@Test
	public void it_should_get_true_when_is_admin() { // isAdminUser test
		Optional<User> optional = getOptionalUser();
		User user = optional.get();
		user.setUserType(UserType.ADMIN);

		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(optional);

		boolean user2 = userService.isAdminUser(1);

		assertThat(user2).isEqualTo(true);
	}

	@Test
	public void it_should_not_login_when_is_password_or_email_invalid() { // EMAIL VEYA SIFRE YANLIS
		Optional<User> optionalUser = getOptionalUser();
		Optional<LoginRequest> optionalLoginRequest = getOptionalLoginRequest();

		optionalUser.get().setEmail("test@gmail.com");
		optionalUser.get().setPassword("hashPassword");
		optionalLoginRequest.get().setPassword("InvalidPassword");
		optionalLoginRequest.get().setEmail("test@gmail.com");

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

		String login = userService.login(optionalLoginRequest.get());

		assertThat(login).isEqualTo("EMAIL VEYA SIFRE YANLIS");
	}

	@Test
	public void it_should_logged_in_when_is_password_and_email_valid() { // EMAIL VEYA SIFRE YANLIS
		Optional<User> optionalUser = getOptionalUser();
		LoginRequest loginRequest = getOptionalLoginRequest().get();

		optionalUser.get().setEmail("test@gmail.com");
		optionalUser.get().setPassword(
				"af7f8c963dadb8d9de431e736a2f421e45a3d313c23c69f93455a3def0d3c9c182d9365790854c80fe98341fe5c5eff763f69520818737a0375b762271959bd5");
		loginRequest.setPassword("hashPassword");
		loginRequest.setEmail("test@gmail.com");

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

		String login = userService.login(loginRequest);

		assertThat(login).isEqualTo("LOGIN BASARILI");
	}
	
	private User getUser(int id, UserType userType) {
		return User.builder().id(id).username("test").email("test@gmail.com").password("hashPassword")
				.userType(userType).mobileNumber("123456789").build();
	}

	private UserRequest getUserRequest() {
		return UserRequest.builder().username("test").email("test@gmail.com").password("hashPassword")
				.mobileNumber("123456789").userType(UserType.INDIVIDUAL).build();
	}

	private UserResponse getUserResponse(int id) {
		return UserResponse.builder().id(id).username("test").email("test@gmail.com").mobileNumber("123456789")
				.userType(UserType.INDIVIDUAL).build();

	}

	private List<User> getUsers() {
		return List.of(getUser(1, UserType.ADMIN), getUser(2, UserType.ADMIN),
				getUser(6, UserType.INDIVIDUAL), getUser(3, UserType.CORPARETE), getUser(4, UserType.INDIVIDUAL),
				getUser(5, UserType.CORPARETE));
	}

	private List<UserResponse> getUserResponses() {
		return List.of(getUserResponse(1), getUserResponse(2),
				getUserResponse(6), getUserResponse(3), getUserResponse(4),
				getUserResponse(5));
	}
	
	private Optional<User> getOptionalUser() {
		return Optional.of(new User());
	}

	private Optional<LoginRequest> getOptionalLoginRequest() {
		return Optional.of(new LoginRequest());
	}
}
