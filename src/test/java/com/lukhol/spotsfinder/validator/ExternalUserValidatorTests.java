package com.lukhol.spotsfinder.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.validator.ExternalUserValidator;

@RunWith(JUnit4.class)
public class ExternalUserValidatorTests {

	private ExternalUserValidator externalUserValidator;
	
	@Before
	public void setUp() {
		externalUserValidator = new ExternalUserValidator();
	}
	
	@Test(expected = RegisterExternalServiceUserException.class)
	public void cannotValidateExternalUser_bothExternalIdsAreEmpty() throws RegisterExternalServiceUserException {
		externalUserValidator.validate(User.builder().facebookId(null).googleId(null).build());
	}
	
	@Test(expected = RegisterExternalServiceUserException.class)
	public void cannotValidateExternalUser_bothExternalIdsAreProvided() throws RegisterExternalServiceUserException {
		externalUserValidator.validate(User.builder().facebookId("123").googleId("789").build());
	}
	
	@Test
	public void canValidate_onlyFacebookIdProvider() throws RegisterExternalServiceUserException {
		externalUserValidator.validate(User.builder().facebookId("123").build());
	}
	
	@Test
	public void canValidate_onlyGoogleIdProvider() throws RegisterExternalServiceUserException {
		externalUserValidator.validate(User.builder().googleId("123").build());
	}
}
