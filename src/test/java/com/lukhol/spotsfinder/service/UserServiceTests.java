package com.lukhol.spotsfinder.service;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.util.StringUtils;

import com.lukhol.spotsfinder.email.EmailSender;
import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.RegisterExternalServiceUserException;
import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.exception.ResetPasswordException;
import com.lukhol.spotsfinder.model.AccountRecover;
import com.lukhol.spotsfinder.model.Role;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.AccountRecoverRepository;
import com.lukhol.spotsfinder.repository.RoleRepository;
import com.lukhol.spotsfinder.repository.UserRepository;
import com.lukhol.spotsfinder.service.UserService;
import com.lukhol.spotsfinder.service.UserServiceImpl;

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
	
	@Mock
	private EmailSender emailSender;
	
	@Mock
	private AccountRecoverRepository accountRecoverRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	private String email = "email@email.com";
	private String password = "admin";
	private String encodedPassword = "$2a$10$mQQlk1yaVJrAGcnRt0XWlezw9Y9xSDcn.m6DW5Z2wS/QSi8pCTp4m";
	private User sampleUser;
	
	@Before
	public void setUp(){
		userService = new UserServiceImpl(userRepository, passwordEncoder, roleRepository, messageSource, emailSender, accountRecoverRepository);
		
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
	
	@Test(expected = RegisterExternalServiceUserException.class)
	public void CannotRegisterExternalUser_notFoundRole() throws RegisterExternalServiceUserException {
		Mockito
			.when(roleRepository.findOneByRoleName("ROLE_USER"))
			.thenReturn(Optional.ofNullable(null));
		
		userService.registerExternalUser(sampleUser, "external_access_token");
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
		
		User userFromService = userService.registerExternalUser(user, "externalAccessToken");
		
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
	
	@Test(expected = RegisterUserException.class)
	public void cannotRegisterUser_userWithProvidedEmailExistInDatabase() throws RegisterUserException {
		Mockito
			.when(userRepository.findOneByEmail(email))
			.thenReturn(Optional.of(sampleUser));
		
		userService.registerUser(sampleUser, Locale.forLanguageTag("en-EN"));
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
		
		userService.registerUser(user,  Locale.forLanguageTag("pl-pl"));
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
		
		User userFromService = userService.registerUser(user,  Locale.forLanguageTag("pl-PL"));
		
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
	
	@Test
	public void canSetInternalAvatarUrl() {
		User user = User.builder().id(1l).build();
		
		Mockito
			.when(userRepository.save(user))
			.thenReturn(user);
		
		String urlFromService = userService.setInternalAvatarUrl(user);
		
		assert urlFromService.contains("user/avatar/1");
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.save(Mockito.isA(User.class));
	}
	
	@Test(expected = NotFoundUserException.class)
	public void cannotRecoverAccount_userByEmailNotFound() throws NotFoundUserException {
		Mockito
			.when(userRepository.existByEmail(email))
			.thenReturn(false);
		
		userService.recoverAccount(email);
	}
	
	@Test
	public void canRecoverAccount_newRecover() throws NotFoundUserException {
		Mockito
			.when(userRepository.existByEmail(email))
			.thenReturn(true);
		
		Mockito
			.when(accountRecoverRepository.findOneByEmail(email))
			.thenReturn(Optional.empty());
		
		userService.recoverAccount(email);
	
		Mockito
			.verify(accountRecoverRepository, Mockito.times(1))
			.save(Mockito.argThat(matcher -> {
				AccountRecover ar = (AccountRecover)matcher;
				return ar.getEmail().equals(email) &&
						ar.getTimestamp() != null &&
						!StringUtils.isEmpty(ar.getGuid());
			}));
		
		Mockito
			.verify(emailSender, Mockito.times(1))
			.sendEmail(Mockito.startsWith(email), Mockito.startsWith("Spots Finder - email reset."), Mockito.contains("/views/user/recover/"));
	}
	
	@Test
	public void canRecoverAccount_retrieveFromDatabase() throws NotFoundUserException {
		String guid = UUID.randomUUID().toString();
		Date date = new Date();
		AccountRecover expectedAccountRecover = AccountRecover
				.builder()
				.email(email)
				.guid(guid)
				.id(15l)
				.timestamp(date)
				.build();
		
		Mockito
			.when(userRepository.existByEmail(email))
			.thenReturn(true);
		
		Mockito
			.when(accountRecoverRepository.findOneByEmail(email))
			.thenReturn(Optional.of(expectedAccountRecover));
		
		userService.recoverAccount(email);
		
		Mockito
			.verify(userRepository, Mockito.times(1))
			.existByEmail(email);
		
		Mockito
			.verify(accountRecoverRepository, Mockito.times(1))
			.findOneByEmail(email);
		
		Mockito
			.verify(accountRecoverRepository, Mockito.times(1))
			.save(Mockito.argThat(matcher -> {
				AccountRecover ar = (AccountRecover)matcher;
				return ar.getTimestamp() != date &&
						ar.getGuid().equals(expectedAccountRecover.getGuid());
			}));
		
		Mockito
			.verify(emailSender, Mockito.times(1))
			.sendEmail(Mockito.startsWith(email), Mockito.startsWith("Spots Finder - email reset."), Mockito.contains("/views/user/recover/"));
	}
	
	@Test(expected = ResetPasswordException.class)
	public void cannotResetPassword_expiredBecauseNotFoundInDatabase() throws ResetPasswordException, NotFoundUserException {
		String guid = UUID.randomUUID().toString();
		
		Mockito
			.when(accountRecoverRepository.findOneByGuid(guid))
			.thenReturn(Optional.empty());
		
		userService.resetPassword(guid, email, password);
	}
	
	@Test(expected = ResetPasswordException.class)
	public void cannotResetPassword_guidAndEmailAreNotForTheSameUser() throws ResetPasswordException, NotFoundUserException {
		String guid = UUID.randomUUID().toString();
		AccountRecover ar = AccountRecover
				.builder()
				.email("notgoodemail@email.com")
				.timestamp(new Date())
				.id(1l)
				.guid(guid)
				.build();
		
		Mockito
			.when(accountRecoverRepository.findOneByGuid(guid))
			.thenReturn(Optional.of(ar));
		
		userService.resetPassword(guid, email, password);
	}
	
	@Test
	public void canResetPassword() throws ResetPasswordException, NotFoundUserException {
		String guid = UUID.randomUUID().toString();
		AccountRecover ar = AccountRecover
				.builder()
				.email(email)
				.timestamp(new Date())
				.id(1l)
				.guid(guid)
				.build();
		
		User user = User
				.builder()
				.id(10l)
				.password("password")
				.build();
		
		Mockito
			.when(accountRecoverRepository.findOneByGuid(guid))
			.thenReturn(Optional.of(ar));
		
		Mockito
			.when(userRepository.findOneByEmail(email))
			.thenReturn(Optional.of(user));
		
		userService.resetPassword(guid, email, password);
		
		assert !user.getPassword().equals(password);
		
//		Mockito
//			.verify(userRepository, Mockito.times(1))
//			.save(Mockito.argThat(matcher -> {
//				User userToMatch = (User)matcher;
//				return userToMatch.getId() == 10l;
//			}));
		
		Mockito
			.verify(accountRecoverRepository, Mockito.times(1))
			.delete(1l);
	}
}
