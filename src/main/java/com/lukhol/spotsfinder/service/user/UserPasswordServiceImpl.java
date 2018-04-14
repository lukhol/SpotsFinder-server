package com.lukhol.spotsfinder.service.user;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.email.EmailSender;
import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.ResetPasswordException;
import com.lukhol.spotsfinder.model.AccountRecover;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.AccountRecoverRepository;
import com.lukhol.spotsfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPasswordServiceImpl implements UserPasswordService {

	@Value("${server.baseurl}")
	private String BASE_URL;
	
	private final EmailSender emailSender;
	private final AccountRecoverRepository accountRecoverRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;	

	@Override
	@Transactional
	public void recoverAccount(String email) throws NotFoundUserException {
		boolean userExist = userRepository.existByEmail(email);
		
		if(!userExist)
			throw new NotFoundUserException(String.format("Not found user with provided email: %s.", email));
		
		AccountRecover accountRecover = accountRecoverRepository
			.findOneByEmail(email)
			.orElseGet(() -> {
				UUID uuid = UUID.randomUUID();
				String guid = uuid.toString();
				
				return AccountRecover.builder()
						.email(email)
						.guid(guid)
						.build();
			});
		
		accountRecover.setTimestamp(new Date());
		
		accountRecoverRepository.save(accountRecover);
		
		StringBuilder urlStringBuilder = new StringBuilder();
		urlStringBuilder
			.append(BASE_URL)
			.append("/views/user/recover/")
			.append(accountRecover.getGuid());
		
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder
			.append("Please click on: ")
			.append(urlStringBuilder.toString())
			.append("\n\n")
			.append("This link will be active for 24 hours.")
			.append("\n\nSpots Finder");
		
		emailSender.sendEmail(email, "Spots Finder - email reset.", messageBuilder.toString());
	}

	@Override
	@Transactional
	public Optional<AccountRecover> findOneByGuid(String guid) {
		return accountRecoverRepository.findOneByGuid(guid);
	}

	@Override
	@Transactional
	public void resetPassword(String code, String email, String newPassword) throws ResetPasswordException, NotFoundUserException {
		AccountRecover accountRecover = accountRecoverRepository
			.findOneByGuid(code)
			.orElseThrow(() -> new ResetPasswordException("Link expired."));
		
		if(!accountRecover.getEmail().equals(email))
			throw new ResetPasswordException("This link seems to be wrong. Try generate reset password link again.");
		
		User user = userRepository
				.findOneByEmail(email)
				.orElseThrow(() -> new NotFoundUserException("Not found user with provided email."));
		
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		accountRecoverRepository.delete(accountRecover.getId());
	}
}
