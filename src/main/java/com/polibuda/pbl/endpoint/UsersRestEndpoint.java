package com.polibuda.pbl.endpoint;

import java.util.Locale;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterExternalServiceUserException;
import com.polibuda.pbl.exception.RegisterUserException;
import com.polibuda.pbl.exception.ResetPasswordException;
import com.polibuda.pbl.exception.UpdateUserException;
import com.polibuda.pbl.model.User;
import com.polibuda.pbl.service.UserService;
import com.polibuda.pbl.validator.ExternalUserValidator;
import com.polibuda.pbl.validator.RegisterUserValidator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UsersRestEndpoint {

	private final UserService userService;
	private final RegisterUserValidator registerUserValidator;
	private final ExternalUserValidator externalUserValidator;
	
	@Autowired
	public UsersRestEndpoint(@NonNull UserService userService, @NonNull RegisterUserValidator registerUserValidator,
			@NonNull ExternalUserValidator externalUserValidator){
		this.userService = userService;
		this.registerUserValidator = registerUserValidator;
		this.externalUserValidator = externalUserValidator;
	}
	
	@GetMapping("/login")
	public ResponseEntity<User> loginAppUser(@RequestParam String email, @RequestParam String password) throws NotFoundUserException {
		
		log.info("User with email: {} is trying to log in.", email);
		
		User user = userService.findUserByEmailAndPassword(email, password);
		user.setPassword("You ain't gona get it ;)");

		log.info("User with email: {} logged in succesfully.", email);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/login/external")
	public ResponseEntity<User> loginExternalUser(@RequestBody User externalUser, @RequestParam String externalAccessToken) throws RegisterExternalServiceUserException {
		log.info("User with fbId: {}, googleId: {} is trying to log in.", externalUser.getFacebookId(), externalUser.getGoogleId());
		
		externalUserValidator.validate(externalUser);
		
		User user;
		try {
			user = userService.findExternalServiceUser(externalUser);
			user = userService.updateUserInfo(user, externalUser);
			
			log.info("User with fbId: {}, googleId: {} successfully logged in.", user.getFacebookId(), user.getGoogleId());
			
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NotFoundUserException e) {
			user = userService.registerExternalUser(externalUser, externalAccessToken);
		}
		
		log.info("User with id: {}{} created and logged in.", user.getFacebookId(), user.getGoogleId());
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestHeader(value="Accept-Language") String acceptLanguage, @RequestBody User user, 
			@RequestParam String psw) throws RegisterUserException {
		
		log.info("Started registering user with email: {}.", user.getEmail());
		
		user.setPassword(psw);
		registerUserValidator.validate(user);		
		user = userService.registerUser(user, Locale.forLanguageTag(acceptLanguage));
		
		log.info("Registering user with email: {} has been completed succesfully.", user.getEmail());
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/recover")
	public ResponseEntity<?> recoverAccount(@RequestParam @Email String emailAddress) throws NotFoundUserException {
		log.info("GET /user/recover?emailAddress={}", emailAddress);
		
		userService.recoverAccount(emailAddress);
			
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@GetMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestParam String code, @RequestParam @Email String email, @RequestParam @Length(min = 5) String newPassword) throws ResetPasswordException, NotFoundUserException {
		log.info("GET /user/resetPassword code={}, email={}, newPassword={}", code, email, newPassword);
		
		userService.resetPassword(code, email, newPassword);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody User user) throws UpdateUserException{
		log.info("POST /update userId = {}", user.getId());
		
		userService.updateUser(user);
		
		return null;
	}
	
	@GetMapping("/checkfree")
	public ResponseEntity<?> checkIfEmailIsFree(@RequestParam String emailAddress){
		log.info("GET /check?emailAddress = {}", emailAddress);
		
		boolean exist = userService.existsByEmail(emailAddress);
		
		return new ResponseEntity<Boolean>(!exist, HttpStatus.OK);
	}
	
	
}
