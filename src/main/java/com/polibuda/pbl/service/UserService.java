package com.polibuda.pbl.service;

import java.util.Optional;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterExternalServiceUserException;
import com.polibuda.pbl.model.User;

public interface UserService {
	User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException;
	Optional<User> findUserByFacebookId(String facebookId) throws NotFoundUserException;
	Optional<User> findUserByGoogleId(String googleId) throws NotFoundUserException;
	
	User findExternalServiceUser(User externalUser) throws NotFoundUserException;
	User registerExternalUser(User user, String externalAccessToken) throws RegisterExternalServiceUserException;
	User updateUserInfo(User userToUpdate, User userWithNewInformation);
}
