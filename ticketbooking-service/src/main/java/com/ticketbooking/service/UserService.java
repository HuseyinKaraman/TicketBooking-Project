package com.ticketbooking.service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ticketbooking.configuration.TicketBookingEmailQueue;
import com.ticketbooking.controller.UserController;
import com.ticketbooking.converter.UserConverter;
import com.ticketbooking.dto.request.LoginRequest;
import com.ticketbooking.dto.request.UserRequest;
import com.ticketbooking.dto.response.UserResponse;
import com.ticketbooking.exception.TicketBookingServiceException;
import com.ticketbooking.model.User;
import com.ticketbooking.model.enums.UserType;
import com.ticketbooking.repository.UserRepository;
import com.ticketbooking.util.PasswordUtil;

@Service
public class UserService {

	private static final String EMAIL_VEYA_SIFRE_YANLIS = "EMAIL VEYA SIFRE YANLIS";
	private static final String LOGIN_BASARILI = "LOGIN BASARILI";
	private static final String IS_LOGGED_IN = "KULLANICI GIRIS YAPMIS DURUMDA";
	private static final String IS_NOT_LOGGED_IN = "KULLANICI GIRIS YAPMAMIS DURUMDA";
	
	private UserRepository userRepository;
	private UserConverter userConverter;
	private CacheManager cacheManager;
	private RabbitTemplate rabbitTemplate;
	private TicketBookingEmailQueue emailQueue;
    final private Logger logger = Logger.getLogger(UserController.class.getName());

	public UserService(UserRepository userRepository, UserConverter userConverter, CacheManager cacheManager,
			RabbitTemplate rabbitTemplate, TicketBookingEmailQueue emailQueue) {
		this.userRepository = userRepository;
		this.userConverter = userConverter;
		this.cacheManager = cacheManager;
		this.rabbitTemplate = rabbitTemplate;
		this.emailQueue = emailQueue;
	}

	/** @Note:user kayıt edilir. */
	public UserResponse create(UserRequest userRequest) {
		String hash = PasswordUtil.preparePasswordHash(userRequest.getPassword());
		logger.log(Level.INFO, "[createUser] - password hash created: {0}", hash);
		
		User savedUser = userRepository.save(userConverter.convert(userRequest, hash));
		logger.log(Level.INFO, "[createUser] - user created: {0}", savedUser.getId());
		
		/** @NOTE: send email info to rabbitmq */
		rabbitTemplate.convertAndSend(emailQueue.getQueueName(), savedUser.getEmail());
		logger.log(Level.WARNING, "[createUser] - userRequest: {0}, sent to : {1}",
				new Object[] { userRequest.getEmail(), emailQueue.getQueueName() });
		
		return userConverter.convert(savedUser);
	}


	/** @Note: kayıtlı user'ları verir */
	public List<UserResponse> findAll() {
		return userConverter.convert(userRepository.findAll());
	} 

	/** @Note: Girilen userId'e ait user'i doner */
	public User findById(Integer userId) {
		return userRepository.findById(userId).orElseThrow(() -> new TicketBookingServiceException("user not found!"));
	}

	
	/** @Note: Kullanıcı giris yapar ve Redis ile login oldugu bilgisi cache'e alınır**/
	@Cacheable(value = "loggedUser", key = "#loginRequest.email", unless = "#result == 'EMAIL VEYA SIFRE YANLIS'")
	public String login(LoginRequest loginRequest) {
		User foundUser = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new TicketBookingServiceException("user not found!"));
		String passwordHash = PasswordUtil.preparePasswordHash(loginRequest.getPassword());

		boolean result = PasswordUtil.validatePassword(passwordHash, foundUser.getPassword());
		
		if (result) {
			logger.log(Level.INFO, "[login] -  user have logged in, userEmail: {0} : ", loginRequest.getEmail());
			return LOGIN_BASARILI; 
		}
		
		logger.log(Level.WARNING, "[login] -  email or password is invalid!, userEmail: {0} : ", loginRequest.getEmail());
		return EMAIL_VEYA_SIFRE_YANLIS;
	} 

	/** @Note: Kullanıcının giriş yapıp yapmadıgı bilgisi sorgulanır!**/
	public String isLoggedIn(String email) {
		Cache cache = cacheManager.getCache("loggedUser");
		ValueWrapper valueWrapper = cache.get(email);
		if ( Objects.nonNull(valueWrapper)) {
			logger.log(Level.INFO, "[isLoggedIn] -  user already loggedIn, userEmail: {0} : ", email);
			return valueWrapper.get().equals(LOGIN_BASARILI) ? IS_LOGGED_IN : IS_NOT_LOGGED_IN;
		}
		 
		logger.log(Level.WARNING, "[isLoggedIn] - user not loggedIn, userEmail: {0} : ", email);
		throw new TicketBookingServiceException("user not loggedIn!");
	}

	/** @Note: Id'si girilen user'in admin olup olmadıgı sorgusu yapılır **/
	public boolean isAdminUser(int userId) {
		User user = findById(userId);
		if (UserType.ADMIN.equals(user.getUserType())) {
			return true;
		}
		logger.log(Level.WARNING, "[isAdminUser] - you are not authorized userId: {0} : ", user.getId());
		throw new TicketBookingServiceException("you are not authorized");
	}

}
