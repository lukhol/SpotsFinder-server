package com.polibuda.pbl.service;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterExternalServiceUserException;
import com.polibuda.pbl.exception.RegisterUserException;
import com.polibuda.pbl.exception.ResetPasswordException;
import com.polibuda.pbl.model.AccountRecover;
import com.polibuda.pbl.model.User;

public interface UserService {
	boolean exists(Long id);
	
	User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException;
	Optional<User> findUserByFacebookId(String facebookId) throws NotFoundUserException;
	Optional<User> findUserByGoogleId(String googleId) throws NotFoundUserException;
	Optional<User> findUserById(Long id);
	
	User findExternalServiceUser(User externalUser) throws NotFoundUserException;
	User registerExternalUser(User user, String externalAccessToken) throws RegisterExternalServiceUserException;
	User updateUserInfo(User userToUpdate, User userWithNewInformation);
	
	User registerUser(User user, Locale locale) throws RegisterUserException;
	
	void saveAvatar(byte[] avatarBytes, long userId) throws IOException;
	String setInternalAvatarUrl(User user);
	byte[] getUserAvatar(long userId) throws NotFoundUserException, IOException;
	
	void recoverAccount(String email) throws NotFoundUserException;
	Optional<AccountRecover> findOneByGuid(String guid);
	void resetPassword(String code, String email, String newPassword) throws ResetPasswordException, NotFoundUserException;
}
