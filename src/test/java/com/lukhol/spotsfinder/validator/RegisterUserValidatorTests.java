package com.lukhol.spotsfinder.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.validator.RegisterUserValidator;

@RunWith(JUnit4.class)
public class RegisterUserValidatorTests {

	private RegisterUserValidator registerUserValidator;
	private User properUser;
	@Before
	public void setUp() {
		registerUserValidator = new RegisterUserValidator();
		
		properUser = User
				.builder()
				.email("email@email.com")
				.firstname("firstname")
				.lastname("lastname")
				.password("password")
				.build();
	}
	
//	@Test(expected = RegisterUserException.class)
//	public void cannotValidate_emailIsEmpty() throws RegisterUserException {
//		properUser.setEmail("");
//		registerUserValidator.validate(properUser);
//	}
//	
//	@Test(expected = RegisterUserException.class)
//	public void cannotValidate_passwordIsEmpty() throws RegisterUserException {
//		properUser.setPassword("");
//		registerUserValidator.validate(properUser);
//	}
//	
//	@Test(expected = RegisterUserException.class)
//	public void cannotValidate_firstnameIsEmpty() throws RegisterUserException {
//		properUser.setFirstname("");
//		registerUserValidator.validate(properUser);
//	}
//	
//	@Test(expected = RegisterUserException.class)
//	public void cannotValidate_lastnameIsEmpty() throws RegisterUserException {
//		properUser.setLastname("");
//		registerUserValidator.validate(properUser);
//	}
//	@Test
//	public void canValidate_userHasAllRequiredFields() throws RegisterUserException {
//		registerUserValidator.validate(properUser);
//	}
}
