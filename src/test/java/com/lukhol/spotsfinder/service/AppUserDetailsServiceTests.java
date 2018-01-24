package com.lukhol.spotsfinder.service;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lukhol.spotsfinder.model.Role;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.UserRepository;
import com.lukhol.spotsfinder.service.AppUserDetailsService;

@RunWith(JUnit4.class)
public class AppUserDetailsServiceTests {
	
	private UserDetailsService userDetailsService;
	private User sampleUser;
	
	@Mock
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userDetailsService = new AppUserDetailsService(userRepository);
		
		sampleUser = User.builder()
				.id(10l)
				.firstname("firstname")
				.lastname("lastname")
				.avatarUrl("http://site.com/1")
				.email("email")
				.password("password")
				.facebookId("123456789")
				.roles(Arrays.asList(Role.builder().roleName("ROLE_USER").build(), Role.builder().roleName("ROLE_ADMIN").build()))
				.build();
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void cannotLoadUserByUsername_userNotFound() throws UsernameNotFoundException {
		
		Mockito
			.when(userRepository.findOneById(10l))
			.thenReturn(Optional.ofNullable(null));
		
		userDetailsService.loadUserByUsername("1234");
	}
	
	@Test()
	public void canLoadUserByUsername() throws UsernameNotFoundException {
		
		Mockito
			.when(userRepository.findOneById(10l))
			.thenReturn(Optional.of(sampleUser));
		
		UserDetails userDetails = userDetailsService.loadUserByUsername("10");
		
		assert userDetails.getUsername().equals("10");
		assert userDetails.getPassword().equals("password");
		assert userDetails.getAuthorities().isEmpty() == false;
		assert userDetails.getAuthorities().stream().anyMatch(z -> z.getAuthority().equals("ROLE_USER"));
		assert userDetails.getAuthorities().stream().anyMatch(z -> z.getAuthority().equals("ROLE_ADMIN"));
		
		Mockito
			.verify(userRepository, Mockito.timeout(1))
			.findOneById(10l);

	}
}
