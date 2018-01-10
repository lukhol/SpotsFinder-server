package com.polibuda.pbl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.model.User;
import com.polibuda.pbl.repository.UserRepository;

import lombok.NonNull;

@Service
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(@NonNull UserRepository userRepository, @NonNull PasswordEncoder passwordEncoder){
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException {
		User user = userRepository
				.findOneByEmail(email)
				.orElseThrow(() -> new NotFoundUserException("Not found user by email."));
		
		if(passwordEncoder.matches(password, user.getPassword())){
			user.setPassword("You ain't gona get it ;)");
			return user;
		}
		
		throw new NotFoundUserException("Wrong credential.");
	}

}
