package com.lukhol.spotsfinder.service.user;

import java.util.Locale;

import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.model.User;

public interface UserRegisterService {
	User registerExternalUser(User user, String externalAccessToken) throws RegisterExternalServiceUserException;
	User registerUser(User user, Locale locale) throws RegisterUserException;
}
