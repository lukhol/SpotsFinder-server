package com.lukhol.spotsfinder.service;

import java.util.Optional;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.UpdateUserException;
import com.lukhol.spotsfinder.model.User;

/**
 * User service which is providing User using some criteria.
 * MobileApp use this service to log in and get information
 * about user. Application also use this service to update 
 * user profile.
 * @author Lukasz
 * @since 1.0
 */
public interface UserService {
	/**
	 * Check if user exist in database using database identifier.
	 * @param id - database identifier of user
	 * @return return true if user with provided identifier exist in database
	 */
	boolean exists(Long id);
	
	/**
	 * Check if user exist in database using email.
	 * @param email - email to check
	 * @return true if user with provided email exist in database
	 */
	boolean existsByEmail(String email);
	
	//Searching
	Optional<User> findUserById(Long id);
	Optional<User> findUserByGoogleId(String googleId) throws NotFoundUserException;
	Optional<User> findUserByFacebookId(String facebookId) throws NotFoundUserException;
	User findExternalServiceUser(User externalUser) throws NotFoundUserException;
	User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException;

	//Updating
	/**
	 * Updating external user information during login.
	 * @param userToUpdate
	 * @param userWithNewInformation
	 * @return Return updated user saved in database.
	 */
	User updateUserInfo(User userToUpdate, User userWithNewInformation);
	
	/**
	 * Updating user profile from MobileApp.
	 * @param user
	 * @return Return update user saved in database.
	 * @throws UpdateUserException - throws when updating failed.
	 */
	User updateUser(User user) throws UpdateUserException;
}
