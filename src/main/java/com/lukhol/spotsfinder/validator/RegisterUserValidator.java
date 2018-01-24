package com.lukhol.spotsfinder.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.model.User;

@Component
public class RegisterUserValidator {
	public void validate(User user) throws RegisterUserException {
		checkCondition(StringUtils.isEmpty(user.getEmail()), "Email cannot be empty.");
		checkCondition(StringUtils.isEmpty(user.getFirstname()), "Firstname cannot be empty.");
		checkCondition(StringUtils.isEmpty(user.getLastname()), "Lastname cannot be empty.");
		checkCondition(StringUtils.isEmpty(user.getPassword()), "Password cannot be null.");
	}
	
	private void checkCondition(boolean condition, String message) throws RegisterUserException {
		if(condition) {
			throw new RegisterUserException(message);
		}
	}
}
