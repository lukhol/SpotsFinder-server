package com.lukhol.spotsfinder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.UserRepository;

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
	public UserDetails loadUserByUsername(String databaseUserId) throws UsernameNotFoundException {
		log.info("Checking userId: {} credential.", databaseUserId);
		
		Long id = Long.valueOf(databaseUserId);
		
		User user = userRepository
				.findOneById(id)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User with id %s doesn't exist", databaseUserId)));
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getId().toString(), user.getPassword(), authorities);
		
		log.info("User with userId: {} login successfully.", user.getId());

        return userDetails;
	}	
}
