package com.lukhol.spotsfinder.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import com.lukhol.spotsfinder.email.EmailSender;
import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.ResetPasswordException;
import com.lukhol.spotsfinder.model.AccountRecover;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.AccountRecoverRepository;
import com.lukhol.spotsfinder.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPasswordServiceTests {

	private UserPasswordService userPasswordService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AccountRecoverRepository accountRecoverRepository;
	
	@Mock
	private EmailSender emailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String email = "email@email.com";
	private String password = "admin";
	
	@Before
	public void setUp() {
		userPasswordService = new UserPasswordServiceImpl(emailSender, accountRecoverRepository, passwordEncoder, userRepository);
	}
	
	@Test(expected = NotFoundUserException.class)
	public void cannotRecoverAccount_userByEmailNotFound() throws NotFoundUserException {
		Mockito
			.when(userRepository.existByEmail(email))
			.thenReturn(false);
		
		userPasswordService.recoverAccount(email);
	}
	
	@Test
	public void canRecoverAccount_newRecover() throws NotFoundUserException {
		Mockito
			.when(userRepository.existByEmail(email))
			.thenReturn(true);
		
		Mockito
			.when(accountRecoverRepository.findOneByEmail(email))
			.thenReturn(Optional.empty());
		
		userPasswordService.recoverAccount(email);
	
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
		
		userPasswordService.recoverAccount(email);
		
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
		
		userPasswordService.resetPassword(guid, email, password);
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
		
		userPasswordService.resetPassword(guid, email, password);
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
		
		userPasswordService.resetPassword(guid, email, password);
		
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
