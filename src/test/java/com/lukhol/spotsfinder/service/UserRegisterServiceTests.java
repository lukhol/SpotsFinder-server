package com.lukhol.spotsfinder.service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.model.Role;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.RoleRepository;
import com.lukhol.spotsfinder.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRegisterServiceTests {
	
	private UserRegisterService userRegisterService;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String email = "email@email.com";
	private String password = "admin";
	private String encodedPassword = "$2a$10$mQQlk1yaVJrAGcnRt0XWlezw9Y9xSDcn.m6DW5Z2wS/QSi8pCTp4m";
	private User sampleUser;
	
	@Before
	public void setUp(){
		userRegisterService = new UserRegisterServiceImpl(userRepository, messageSource, roleRepository, passwordEncoder);
		
		sampleUser = User.builder()
				.id(1l)
				.firstname("firstname")
				.lastname("lastname")
				.avatarUrl("http://site.com/1")
				.email(email)
				.password(encodedPassword)
				.facebookId("123456789")
				.roles(Arrays.asList(Role.builder().roleName("ROLE_USER").build()))
				.build();
	}
	
	@Test(expected = RegisterExternalServiceUserException.class)
	public void CannotRegisterExternalUser_notFoundRole() throws RegisterExternalServiceUserException {
		Mockito
			.when(roleRepository.findOneByRoleName("ROLE_USER"))
			.thenReturn(Optional.ofNullable(null));
		
		userRegisterService.registerExternalUser(sampleUser, "external_access_token");
	}
	
	@Test
	public void CanRegisterExternalUser() throws RegisterExternalServiceUserException {
		Role roleUser = Role.builder().roleName("ROLE_USER").description("description").build();
		
		User user = new User();
		
		Mockito
			.when(roleRepository.findOneByRoleName("ROLE_USER"))
			.thenReturn(Optional.of(roleUser));
		
		Mockito
			.when(userRepository.save(user))
			.thenReturn(user);
		
		User userFromService = userRegisterService.registerExternalUser(user, "externalAccessToken");
		
		assert userFromService.getPassword() != null;
		assert userFromService.isActive() == true;
		assert userFromService.getRoles() != null;
		assert userFromService.getRoles().get(0).getRoleName().equals("ROLE_USER");
		
		Mockito
			.verify(roleRepository, Mockito.times(1))
			.findOneByRoleName("ROLE_USER");
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.save(user);
	}
	
	@Test(expected = RegisterUserException.class)
	public void cannotRegisterUser_userWithProvidedEmailExistInDatabase() throws RegisterUserException {
		Mockito
			.when(userRepository.findOneByEmail(email))
			.thenReturn(Optional.of(sampleUser));
		
		userRegisterService.registerUser(sampleUser, Locale.forLanguageTag("en-EN"));
	}
	
	@Test(expected = RegisterUserException.class)
	public void cannotRegisterUser_cannotFindRole() throws RegisterUserException {
		Role userRole = Role.builder().roleName("ROLE_USER").description("role description").build();
		User user = new User();
		
		Mockito
			.when(userRepository.findOneByEmail(user.getEmail()))
			.thenReturn(Optional.ofNullable(null));
		
		Mockito
			.when(roleRepository.findOneByRoleName(userRole.getRoleName()))
			.thenReturn(Optional.ofNullable(null));
		
		userRegisterService.registerUser(user,  Locale.forLanguageTag("pl-pl"));
	}
	
	@Test 
	public void canRegisterUser() throws RegisterUserException {
		Role userRole = Role.builder().roleName("ROLE_USER").description("role description").build();
		User user = User.builder().password(password).id(1l).email(email).build();
		
		Mockito
			.when(userRepository.findOneByEmail(user.getEmail()))
			.thenReturn(Optional.ofNullable(null));
	
		Mockito
			.when(roleRepository.findOneByRoleName(userRole.getRoleName()))
			.thenReturn(Optional.ofNullable(userRole));
		
		Mockito
			.when(userRepository.save(user))
			.thenReturn(user);
		
		User userFromService = userRegisterService.registerUser(user,  Locale.forLanguageTag("pl-PL"));
		
		assert userFromService.getRoles() != null;
		assert userFromService.getRoles().get(0).getRoleName().equals(userRole.getRoleName());
		assert userFromService.getPassword() != null;
		assert userFromService.isActive();
		assert userFromService.getAvatarUrl().contains("user/avatar/1");
		
		Mockito
			.verify(roleRepository, Mockito.times(1))
			.findOneByRoleName("ROLE_USER");
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.findOneByEmail(email);
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.save(Mockito.isA(User.class));
	}
	
}
