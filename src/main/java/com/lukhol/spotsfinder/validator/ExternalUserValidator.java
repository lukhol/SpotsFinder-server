package com.lukhol.spotsfinder.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.model.User;

@Component
public class ExternalUserValidator {
	public void validate(User user) throws RegisterExternalServiceUserException {
		checkCondition(StringUtils.isEmpty(user.getFacebookId()) && StringUtils.isEmpty(user.getGoogleId()),
				"At least one external id must be provided.");
		
		checkCondition(!StringUtils.isEmpty(user.getFacebookId()) && !StringUtils.isEmpty(user.getGoogleId()),
				"Only one external id must be provided.");
	}
	
	private void checkCondition(boolean condition, String message) throws RegisterExternalServiceUserException {
		if(condition){
			throw new RegisterExternalServiceUserException(message);	
		}
	}
}
