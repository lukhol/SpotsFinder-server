package com.lukhol.spotsfinder.service;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.model.Role;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.RoleRepository;
import com.lukhol.spotsfinder.repository.UserRepository;
import com.lukhol.spotsfinder.service.user.UserService;
import com.lukhol.spotsfinder.service.user.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
	
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	private String email = "email@email.com";
	private String password = "admin";
	private String encodedPassword = "$2a$10$mQQlk1yaVJrAGcnRt0XWlezw9Y9xSDcn.m6DW5Z2wS/QSi8pCTp4m";
	private User sampleUser;
	
	@Before
	public void setUp(){
		userService = new UserServiceImpl(passwordEncoder, userRepository, roleRepository);
		
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
	
	@Test(expected = NotFoundUserException.class)
	public void cannotGetUserByEmailAndPassword_notFoundInDatabase() throws NotFoundUserException{
		
		Mockito
			.when(userRepository.findOneByEmail(email))
			.thenReturn(Optional.ofNullable(null));
		
		userService.findUserByEmailAndPassword(email, password);
	}
	
	@Test(expected = NotFoundUserException.class)
	public void cannotGetUserByEmailAndPassword_wrongCredential() throws NotFoundUserException {
		User userWithWrongPassword = User
				.builder()
				.email(email)
				.password(encodedPassword + "costam")
				.build();
		
		Mockito
			.when(userRepository.findOneByEmail(email))
			.thenReturn(Optional.of(userWithWrongPassword));
		
		userService.findUserByEmailAndPassword(email, password);
	}
	
	@Test
	public void canGetUserByEmailAndPassword() throws NotFoundUserException {
		User user = User
				.builder()
				.email(email)
				.password(encodedPassword)
				.build();
		
		Mockito
			.when(userRepository.findOneByEmail(email))
			.thenReturn(Optional.of(user));
		
		User userFromService = userService.findUserByEmailAndPassword(email, password);
		
		assert userFromService != null;
	}
	
	@Test
	public void canFindUserByFacebookId() throws NotFoundUserException {
		String facebookId = "123456789";
		
		Mockito
			.when(userRepository.findOneByFacebookId(facebookId))
			.thenReturn(Optional.of(sampleUser));
		
		User user = userService.findUserByFacebookId(facebookId).get();
		
		assert user != null;
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.findOneByFacebookId(facebookId);
	}
	
	@Test
	public void canFindUserByGoogleId() throws NotFoundUserException {
		String googleId = "123456789";
		
		Mockito
			.when(userRepository.findOneByGoogleId(googleId))
			.thenReturn(Optional.of(sampleUser));
		
		User user = userService.findUserByGoogleId(googleId).get();
		
		assert user != null;
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.findOneByGoogleId(googleId);
	}
	
	@Test
	public void canUpdateUserInfo() {
		User userToUpdate = new User();
		User userWithNewInformation = User.builder()
				.firstname("firstname")
				.lastname("lastname")
				.email(email)
				.avatarUrl("http://avatarurl.pl/1")
				.build();
		
		Mockito
			.when(userRepository.save(userToUpdate))
			.thenReturn(userToUpdate);
		
		User updatedUser = userService.updateUserInfo(userToUpdate, userWithNewInformation);
		
		assert updatedUser.getFirstname().equals("firstname");
		assert updatedUser.getLastname().equals("lastname");
		assert updatedUser.getEmail().equals(email);
		assert updatedUser.getAvatarUrl().equals("http://avatarurl.pl/1");
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.save(userToUpdate);
	}
	
	@Test(expected = NotFoundUserException.class)
	public void cannotFindExternlServiceUser_notFoundUserInRepositoryWithProvidedFacebookId() throws NotFoundUserException{
		User user = User.builder().facebookId("123456789").build();
		
		Mockito
			.when(userRepository.findOneByFacebookId("123456789"))
			.thenReturn(Optional.ofNullable(null));
		
		userService.findExternalServiceUser(user);
	}
	
	@Test(expected = NotFoundUserException.class)
	public void cannotFindExternlServiceUser_notFoundUserInRepositoryWithProvidedGoogleId() throws NotFoundUserException{
		User user = User.builder().googleId("123456789").build();
		
		Mockito
			.when(userRepository.findOneByGoogleId("123456789"))
			.thenReturn(Optional.ofNullable(null));
		
		userService.findExternalServiceUser(user);
	}
	
	@Test
	public void canFindExternalServiceUser_byFacebookId() throws NotFoundUserException {
		String facebookId = "123456789";
		
		User user = User.builder().facebookId(facebookId).build();
		
		Mockito
			.when(userRepository.findOneByFacebookId(facebookId))
			.thenReturn(Optional.ofNullable(user));
		
		User userFromService = userService.findExternalServiceUser(user);
		
		assert userFromService.getFacebookId().equals(facebookId);
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.findOneByFacebookId(facebookId);
	}
	
	@Test
	public void canFindExternalServiceUser_byGoogleId() throws NotFoundUserException {
		String googleId = "123456789";
		
		User user = User.builder().googleId(googleId).build();
		
		Mockito
			.when(userRepository.findOneByGoogleId(googleId))
			.thenReturn(Optional.ofNullable(user));
		
		User userFromService = userService.findExternalServiceUser(user);
		
		assert userFromService.getGoogleId().equals(googleId);
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.findOneByGoogleId(googleId);
	}
}
