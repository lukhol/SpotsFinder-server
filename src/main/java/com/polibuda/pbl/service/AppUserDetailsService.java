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

import com.polibuda.pbl.model.User;
import com.polibuda.pbl.repository.UserRepository;

import lombok.NonNull;

@Component
public class AppUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public AppUserDetailsService(@NonNull UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOneByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s doesn't exist", username)));
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);

        return userDetails;
	}	
}
