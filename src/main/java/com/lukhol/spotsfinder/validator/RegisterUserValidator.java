package com.lukhol.spotsfinder.validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.service.UserService;

@Component
public class RegisterUserValidator {
	
	private final UserService userService;
	
	@Autowired
	public RegisterUserValidator(UserService userService) {
		this.userService = userService;
	}
	
	public void validate(User user, BindingResult bindingResult) throws RegisterUserException {
		if(user.getPassword() == null || user.getPassword().length() < 5) {
			bindingResult.rejectValue("password", "error.user.password");
		}
		
		if(user.getFirstname() == null || user.getFirstname().length() == 0) {
			bindingResult.rejectValue("firstname", "error.user.firstname");
		}
		
		if(user.getLastname() == null || user.getLastname().length() == 0) {
			bindingResult.rejectValue("lastname", "error.user.lastname");
		}
		
		if(isValidEmailAddress(user.getEmail()) == false) {
			bindingResult.rejectValue("email", "error.user.email");
		}
		
		if(user.getEmail() != null && userService.existsByEmail(user.getEmail())) {
			bindingResult.rejectValue("email", "error.register.emailInUse");
		}
		
		if(bindingResult.hasErrors())
			throw new RegisterUserException(bindingResult);
	}	
	
	private boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
}
