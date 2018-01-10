package com.polibuda.pbl.service;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.model.User;

public interface UserService {
	User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException;
}
