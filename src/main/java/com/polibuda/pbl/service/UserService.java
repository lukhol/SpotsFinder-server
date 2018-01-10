package com.polibuda.pbl.service;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterFacebookUserException;
import com.polibuda.pbl.model.User;

public interface UserService {
	User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException;
	User findUserByFacebookId(String facebookId) throws NotFoundUserException;
	User registerFacebookUser(User user, String facebookAccessToken) throws RegisterFacebookUserException;
	User updateFacebookUser(User userToUpdate, User facebookUser);
}
