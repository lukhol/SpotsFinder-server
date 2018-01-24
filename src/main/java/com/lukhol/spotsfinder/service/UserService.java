package com.lukhol.spotsfinder.service;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.exception.ResetPasswordException;
import com.lukhol.spotsfinder.exception.UpdateUserException;
import com.lukhol.spotsfinder.model.AccountRecover;
import com.lukhol.spotsfinder.model.User;

public interface UserService {
	boolean exists(Long id);
	boolean existsByEmail(String email);
	
	User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException;
	Optional<User> findUserByFacebookId(String facebookId) throws NotFoundUserException;
	Optional<User> findUserByGoogleId(String googleId) throws NotFoundUserException;
	Optional<User> findUserById(Long id);
	
	User findExternalServiceUser(User externalUser) throws NotFoundUserException;
	User registerExternalUser(User user, String externalAccessToken) throws RegisterExternalServiceUserException;
	User updateUserInfo(User userToUpdate, User userWithNewInformation);
	User updateUser(User user) throws UpdateUserException;
	
	User registerUser(User user, Locale locale) throws RegisterUserException;
	
	void saveAvatar(byte[] avatarBytes, long userId) throws IOException;
	String setInternalAvatarUrl(User user);
	byte[] getUserAvatar(long userId) throws NotFoundUserException, IOException;
	
	void recoverAccount(String email) throws NotFoundUserException;
	Optional<AccountRecover> findOneByGuid(String guid);
	void resetPassword(String code, String email, String newPassword) throws ResetPasswordException, NotFoundUserException;
}
