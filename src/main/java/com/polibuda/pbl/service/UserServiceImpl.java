package com.polibuda.pbl.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterFacebookUserException;
import com.polibuda.pbl.model.Role;
import com.polibuda.pbl.model.User;
import com.polibuda.pbl.repository.RoleRepository;
import com.polibuda.pbl.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(@NonNull UserRepository userRepository, @NonNull PasswordEncoder passwordEncoder,
			@NonNull RoleRepository roleRepository){
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
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

	@Override
	public User findUserByFacebookId(String facebookId) throws NotFoundUserException {
		User user = userRepository
				.findOneByFacebookId(facebookId)
				.orElseThrow(() -> new NotFoundUserException("Not found user by facebookId"));
		
		return user;
	}

	@Override
	public User registerFacebookUser(User user, String facebookAccessToken) throws RegisterFacebookUserException {
		Role userRole = roleRepository
				.findOneByRoleName("ROLE_USER")
				.orElseThrow(() -> new RegisterFacebookUserException("Could not find role: " + "ROLE_USER"));
				
		user.setPassword(passwordEncoder.encode("facebookUserPassword"));
		user.setRoles(Arrays.asList(userRole));
		user.setActive(true);
		
		user = userRepository.save(user);
		
		return user;
	}

	@Override
	public User updateFacebookUser(User userToUpdate, User facebookUser) {
		userToUpdate.setFirstname(facebookUser.getFirstname());
		userToUpdate.setLastname(facebookUser.getLastname());
		userToUpdate.setEmail(facebookUser.getEmail());
		return userRepository.save(userToUpdate);
	}

}
