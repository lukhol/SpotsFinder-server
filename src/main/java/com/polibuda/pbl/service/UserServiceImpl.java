package com.polibuda.pbl.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterExternalServiceUserException;
import com.polibuda.pbl.exception.RegisterUserException;
import com.polibuda.pbl.model.Role;
import com.polibuda.pbl.model.User;
import com.polibuda.pbl.repository.RoleRepository;
import com.polibuda.pbl.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Value("${user.avatar.path}")
	private String AVATARS_PATH;
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final MessageSource messageSource;
	
	@Autowired
	public UserServiceImpl(@NonNull UserRepository userRepository, @NonNull PasswordEncoder passwordEncoder,
			@NonNull RoleRepository roleRepository, @NonNull MessageSource messageSource){
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.messageSource = messageSource;
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
		
		log.info("Not found user by email {}, and provided password.", email);
		throw new NotFoundUserException("Wrong credential.");
	}

	@Override
	public Optional<User> findUserByFacebookId(String facebookId) throws NotFoundUserException {
		return userRepository.findOneByFacebookId(facebookId);
	}
	
	@Override
	public Optional<User> findUserByGoogleId(String googleId){
		return userRepository.findOneByGoogleId(googleId);
	}

	@Override
	public User registerExternalUser(User user, String externalAccessToken) throws RegisterExternalServiceUserException {
		Role userRole = roleRepository
				.findOneByRoleName("ROLE_USER")
				.orElseThrow(() -> new RegisterExternalServiceUserException("Could not find role: " + "ROLE_USER"));
				
		user.setPassword(passwordEncoder.encode("externaluser"));
		user.setRoles(Arrays.asList(userRole));
		user.setActive(true);
		
		user = userRepository.save(user).get();
		
		return user;
	}

	@Override
	public User updateUserInfo(User userToUpdate, User userWithNewInformation) {
		userToUpdate.setFirstname(userWithNewInformation.getFirstname());
		userToUpdate.setLastname(userWithNewInformation.getLastname());
		userToUpdate.setEmail(userWithNewInformation.getEmail());
		userToUpdate.setAvatarUrl(userWithNewInformation.getAvatarUrl());
		return userRepository.save(userToUpdate).get();
	}

	@Override
	public User findExternalServiceUser(User externalUser) throws NotFoundUserException {
		User user = null;
		
		if(!StringUtils.isEmpty(externalUser.getFacebookId()))
			user = userRepository
					.findOneByFacebookId(externalUser.getFacebookId())
					.orElse(null);
		
		if(!StringUtils.isEmpty(externalUser.getGoogleId()))
			user = userRepository
					.findOneByGoogleId(externalUser.getGoogleId())
					.orElse(null);
		
		if(user == null)
			throw new NotFoundUserException("Could not find external user.");
				
		return user;
	}

	@Override
	public User registerUser(User user, Locale locale) throws RegisterUserException {
		
		Optional<User> userByEmailOptional = userRepository.findOneByEmail(user.getEmail());
		
		if(userByEmailOptional.isPresent())
			throw new RegisterUserException(messageSource.getMessage("error.register.emailInUse", null, locale));
		
		Role userRole = roleRepository
				.findOneByRoleName("ROLE_USER")
				.orElseThrow(() -> new RegisterUserException("Could not find role: " + "ROLE_USER"));
				
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(userRole));
		user.setAvatarUrl(String.format(String.format("%s\\%d.jpg", AVATARS_PATH , user.getId())));
		user.setActive(true);
		
		return userRepository.save(user).get();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findOneById(id);
	}

	@Override
	public void saveAvatar(byte[] avatarBytes, long userId) throws IOException {
		FileOutputStream fos = new FileOutputStream(String.format("%s\\%d.jpg", AVATARS_PATH , userId)); 
		
		try {
		    fos.write(avatarBytes);
		}
		finally {
		    fos.close();
		}
	}
}
