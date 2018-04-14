package com.lukhol.spotsfinder.endpoint.user;

import java.util.Locale;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.exception.ResetPasswordException;
import com.lukhol.spotsfinder.exception.UpdateUserException;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.service.user.UserPasswordService;
import com.lukhol.spotsfinder.service.user.UserRegisterService;
import com.lukhol.spotsfinder.service.user.UserService;
import com.lukhol.spotsfinder.validator.ExternalUserValidator;
import com.lukhol.spotsfinder.validator.RegisterUserValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersRestEndpoint {

	private final UserService userService;
	private final UserRegisterService userRegisterService;
	private final UserPasswordService userPasswordService;
	private final RegisterUserValidator registerUserValidator;
	private final ExternalUserValidator externalUserValidator;
	
	@GetMapping("/login")
	@CrossOrigin(origins = "http://localhost:7777")
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
			user = userRegisterService.registerExternalUser(externalUser, externalAccessToken);
		}
		
		log.info("User with id: {}{} created and logged in.", user.getFacebookId(), user.getGoogleId());
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestHeader(value="Accept-Language") String acceptLanguage, @RequestBody @Valid User user,
			final BindingResult bindingResult) throws RegisterUserException, MethodArgumentNotValidException {
		
		log.info("Started registering user with email: {}.", user.getEmail());
		
		registerUserValidator.validate(user, bindingResult);		
		user = userRegisterService.registerUser(user, Locale.forLanguageTag(acceptLanguage));
		
		log.info("Registering user with email: {} has been completed succesfully.", user.getEmail());
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/recover")
	public ResponseEntity<?> recoverAccount(@RequestParam @Email String emailAddress) throws NotFoundUserException {
		log.info("GET /user/recover?emailAddress={}", emailAddress);
		
		userPasswordService.recoverAccount(emailAddress);
			
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@GetMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestParam String code, @RequestParam @Email String email, @RequestParam @Length(min = 5) String newPassword) throws ResetPasswordException, NotFoundUserException {
		log.info("GET /user/resetPassword code={}, email={}, newPassword={}", code, email, newPassword);
		
		userPasswordService.resetPassword(code, email, newPassword);
		
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
	
	@GetMapping("/info/{userId}")
	public ResponseEntity<User> fetchUser(@PathVariable(name = "userId") String userId) {
		log.debug("GET /userinfo/" + userId);
		
		User user = userService.findUserById(Long.valueOf(userId)).get();
		user.setPassword("You ain't gona get it ;)");
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
