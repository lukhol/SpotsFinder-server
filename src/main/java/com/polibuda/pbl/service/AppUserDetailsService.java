package com.polibuda.pbl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.polibuda.pbl.model.User;
import com.polibuda.pbl.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public AppUserDetailsService(@NonNull UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
		log.info("Checking email: {} credential.", identifier);
		
		User user = userRepository
				.findOneByEmail(identifier)
				.orElse(userRepository
							.findOneByFacebookId(identifier)
							.orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s doesn't exist", identifier)))
						);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
		
		String identifierToPass = user.getFacebookId();
		
		if(!StringUtils.isEmpty(user.getEmail()))
			identifierToPass = user.getEmail();
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(identifierToPass, user.getPassword(), authorities);

        return userDetails;
	}	
}
