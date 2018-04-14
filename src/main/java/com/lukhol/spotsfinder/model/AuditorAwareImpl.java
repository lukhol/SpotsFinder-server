package com.lukhol.spotsfinder.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.lukhol.spotsfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component("auditorAware")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuditorAwareImpl implements AuditorAware<User> {

	@Autowired
	private final UserRepository userRepository;
	
	@Override
	public User getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String userIdString = auth.getName();
		long userId = Long.parseLong(userIdString);
		
		User user = userRepository.findOne(userId);
		
		return user;
	}
}