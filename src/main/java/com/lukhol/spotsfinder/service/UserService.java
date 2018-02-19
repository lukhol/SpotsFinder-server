package com.lukhol.spotsfinder.service;

import java.util.Optional;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.UpdateUserException;
import com.lukhol.spotsfinder.model.User;

public interface UserService {
	//Check if exist
	boolean exists(Long id);
	boolean existsByEmail(String email);
	
	//Searching
	User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException;
	Optional<User> findUserByFacebookId(String facebookId) throws NotFoundUserException;
	Optional<User> findUserByGoogleId(String googleId) throws NotFoundUserException;
	Optional<User> findUserById(Long id);
	User findExternalServiceUser(User externalUser) throws NotFoundUserException;
	
	//Updating
	User updateUserInfo(User userToUpdate, User userWithNewInformation);
	User updateUser(User user) throws UpdateUserException;
}
