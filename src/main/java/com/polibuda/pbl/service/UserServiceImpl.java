package com.polibuda.pbl.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.polibuda.pbl.email.EmailSender;
import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterExternalServiceUserException;
import com.polibuda.pbl.exception.RegisterUserException;
import com.polibuda.pbl.exception.ResetPasswordException;
import com.polibuda.pbl.model.AccountRecover;
import com.polibuda.pbl.model.Role;
import com.polibuda.pbl.model.User;
import com.polibuda.pbl.repository.AccountRecoverRepository;
import com.polibuda.pbl.repository.RoleRepository;
import com.polibuda.pbl.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Value("${user.avatar.path}")
	private String AVATARS_PATH;
	
	@Value("${server.baseurl}")
	private String BASE_URL;
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final MessageSource messageSource;
	private final EmailSender emailSender;
	private final AccountRecoverRepository accountRecoverRepository;
	
	@Autowired
	public UserServiceImpl(@NonNull UserRepository userRepository, @NonNull PasswordEncoder passwordEncoder, @NonNull RoleRepository roleRepository, 
			@NonNull MessageSource messageSource, @NonNull EmailSender emailSender, @NonNull AccountRecoverRepository accountRecoverRepository){
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.messageSource = messageSource;
		this.emailSender = emailSender;
		this.accountRecoverRepository = accountRecoverRepository;
	}
	
	@Override
	@Transactional
	public User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException {
		User user = userRepository
				.findOneByEmail(email)
				.orElseThrow(() -> new NotFoundUserException("Not found user by email."));
		
		if(passwordEncoder.matches(password, user.getPassword())){
			return user;
		}
		
		log.info("Not found user by email {}, and provided password.", email);
		throw new NotFoundUserException("Wrong credential.");
	}

	@Override
	@Transactional
	public Optional<User> findUserByFacebookId(String facebookId) throws NotFoundUserException {
		return userRepository.findOneByFacebookId(facebookId);
	}
	
	@Override
	@Transactional
	public Optional<User> findUserByGoogleId(String googleId){
		return userRepository.findOneByGoogleId(googleId);
	}

	@Override
	@Transactional
	public User registerExternalUser(User user, String externalAccessToken) throws RegisterExternalServiceUserException {
		Role userRole = roleRepository
				.findOneByRoleName("ROLE_USER")
				.orElseThrow(() -> new RegisterExternalServiceUserException(String.format("Could not find role: %s.", "ROLE_USER")));
		
		user.setPassword(passwordEncoder.encode("externaluser"));
		user.setRoles(Arrays.asList(userRole));
		user.setActive(true);
		
		user = userRepository.save(user).get();
		
		return user;
	}

	@Override
	@Transactional
	public User updateUserInfo(User userToUpdate, User userWithNewInformation) {
		userToUpdate.setFirstname(userWithNewInformation.getFirstname());
		userToUpdate.setLastname(userWithNewInformation.getLastname());
		userToUpdate.setEmail(userWithNewInformation.getEmail());
		userToUpdate.setAvatarUrl(userWithNewInformation.getAvatarUrl());
		return userRepository.save(userToUpdate).get();
	}

	@Override
	@Transactional
	public User findExternalServiceUser(User externalUser) throws NotFoundUserException {
		User user = null;
		
		if(!StringUtils.isEmpty(externalUser.getFacebookId()))
			user = userRepository
					.findOneByFacebookId(externalUser.getFacebookId())
					.orElse(null);
		
		if(!StringUtils.isEmpty(externalUser.getGoogleId()))
			user = userRepository
					.findOneByGoogleId(externalUser.getGoogleId())
					.orElse(null);
		
		if(user == null)
			throw new NotFoundUserException("Could not find external user.");
				
		return user;
	}

	@Override
	@Transactional
	public User registerUser(User user, Locale locale) throws RegisterUserException {
		
		Optional<User> userByEmailOptional = userRepository.findOneByEmail(user.getEmail());
		
		if(userByEmailOptional.isPresent())
			throw new RegisterUserException(messageSource.getMessage("error.register.emailInUse", null, locale));
		
		Role userRole = roleRepository
				.findOneByRoleName("ROLE_USER")
				.orElseThrow(() -> new RegisterUserException("Could not find role: " + "ROLE_USER"));
				
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(userRole));
		user.setActive(true);
		
		user = userRepository.save(user).get();
		
		user.setAvatarUrl(String.format("%s%s%d.jpg", BASE_URL, "/user/avatar/", user.getId())); //This will be save without call .save() because method is inside transaction.
		
		return user;
	}

	@Override
	@Transactional
	public Optional<User> findUserById(Long id) {
		return userRepository.findOneById(id);
	}

	@Override
	@Transactional
	public void saveAvatar(byte[] avatarBytes, long userId) throws IOException {
		FileOutputStream fos = new FileOutputStream(String.format("%s\\%d.jpg", AVATARS_PATH , userId)); 
		
		try {
		    fos.write(avatarBytes);
		}
		finally {
		    fos.close();
		}
	}

	@Override
	@Transactional
	public String setInternalAvatarUrl(User user) {
		user.setAvatarUrl(String.format(String.format("%s%s%d.jpg", BASE_URL, "/user/avatar/", user.getId())));
		userRepository.save(user);
		return user.getAvatarUrl();
	}

	@Override
	@Transactional
	public byte[] getUserAvatar(long userId) throws NotFoundUserException, IOException {
		
		if(!userRepository.exists(userId))
			throw new NotFoundUserException("Not found user: " + userId);
		
		File fileImage = new File(String.format("%s\\%d.jpg", AVATARS_PATH , userId));
		byte[] imageBytes;
		
		if(fileImage.exists()){
			imageBytes = Files.readAllBytes(fileImage.toPath());
		}
		else{
			File anonymousUserFile = new File(String.format("%s\\%s.jpg", AVATARS_PATH , "anonymous"));
			imageBytes = Files.readAllBytes(anonymousUserFile.toPath());
		}
		
		return imageBytes;
	}

	@Override
	public boolean exists(Long id) {
		return userRepository.exists(id);
	}

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
			.append("/views/user/recover/accept?guid=")
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
	public Optional<AccountRecover> findOneByGuid(String guid) {
		return accountRecoverRepository.findOneByGuid(guid);
	}

	@Override
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
