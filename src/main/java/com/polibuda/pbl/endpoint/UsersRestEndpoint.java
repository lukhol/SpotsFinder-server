package com.polibuda.pbl.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterExternalServiceUserException;
import com.polibuda.pbl.model.User;
import com.polibuda.pbl.service.UserService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UsersRestEndpoint {

	private final UserService userService;
	
	@Autowired
	public UsersRestEndpoint(@NonNull UserService userService){
		this.userService = userService;
	}
	
	@GetMapping
	public String testMapping(){
		return "Ok";
	}
	
	@GetMapping
	@RequestMapping("/login")
	public ResponseEntity<User> loginAppUser(@RequestParam String email, @RequestParam String password) throws NotFoundUserException {
		log.info("User with email: {} is trying to log in.", email);
		
		User user = userService.findUserByEmailAndPassword(email, password);
		log.info("User with email: {} logged in succesfully.", email);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping
	@RequestMapping("/login/external")
	public ResponseEntity<User> loginFacebookUser(@RequestBody User externalUser, @RequestParam String externalAccessToken) throws RegisterExternalServiceUserException {
		log.info("User with facebookId: {}{} is trying to log in.", externalUser.getFacebookId(), externalUser.getGoogleId());
		
		//Validator
		if(StringUtils.isEmpty(externalUser.getFacebookId()) && StringUtils.isEmpty(externalUser.getGoogleId()))
			throw new RegisterExternalServiceUserException("At least one external id must be provided.");
		
		if(!StringUtils.isEmpty(externalUser.getFacebookId()) && !StringUtils.isEmpty(externalUser.getGoogleId()))
			throw new RegisterExternalServiceUserException("Only one external id must be provided.");
		
		User user;
		try {
			user = userService.findExternalServiceUser(externalUser);
			user = userService.updateUserInfo(user, externalUser);
			log.info("User with facebook id: {} successfully logged in.", user.getFacebookId());
			
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NotFoundUserException e) {
			user = userService.registerExternalUser(externalUser, externalAccessToken);
		}
		
		log.info("User with id: {}{} created and logged in.", user.getFacebookId(), user.getGoogleId());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
