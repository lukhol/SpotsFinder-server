package com.lukhol.spotsfinder.service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.model.Role;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.RoleRepository;
import com.lukhol.spotsfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRegisterServiceImpl implements UserRegisterService {
	
	@Value("${server.baseurl}")
	private String BASE_URL;
	
	private final UserRepository userRepository;
	private final MessageSource messageSource;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User registerUser(User user, Locale locale) throws RegisterUserException {
		
		Optional<User> userByEmailOptional = userRepository.findOneByEmail(user.getEmail());
		
		if(userByEmailOptional.isPresent())
			throw new RegisterUserException(messageSource.getMessage("error.register.emailInUse", null, locale));
		
		Role userRole = roleRepository
				.findOneByRoleName("ROLE_USER")
				.orElseThrow(() -> new RegisterUserException("Could not find role: " + "ROLE_USER"));
				
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(userRole));
		user.setActive(true);
		
		user = userRepository.save(user);
		
		user.setAvatarUrl(String.format("%s%s%d.jpg", BASE_URL, "/user/avatar/", user.getId())); //This will be save without call .save() because method is inside transaction.
		
		return user;
	}
	
	@Override
	@Transactional
	public User registerExternalUser(User user, String externalAccessToken) throws RegisterExternalServiceUserException {
		Role userRole = roleRepository
				.findOneByRoleName("ROLE_USER")
				.orElseThrow(() -> new RegisterExternalServiceUserException(String.format("Could not find role: %s.", "ROLE_USER")));
		
		user.setPassword(passwordEncoder.encode("externaluser"));
		user.setRoles(Arrays.asList(userRole));
		user.setActive(true);
		
		user = userRepository.save(user);
		
		return user;
	}
}
