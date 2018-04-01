package com.lukhol.spotsfinder.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.model.User;

@Component
public class RegisterUserValidator {
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
		
		if(bindingResult.hasErrors())
			throw new RegisterUserException(bindingResult);
	}	
}
