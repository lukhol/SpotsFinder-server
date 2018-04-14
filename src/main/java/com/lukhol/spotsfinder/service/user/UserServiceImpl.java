package com.lukhol.spotsfinder.service.user;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.UpdateUserException;
import com.lukhol.spotsfinder.model.Role;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.RoleRepository;
import com.lukhol.spotsfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;	
	
	@Override
	@Transactional
	public User findUserByEmailAndPassword(String email, String password) throws NotFoundUserException {
		User user = userRepository
				.findOneByEmail(email)
				.orElseThrow(() -> new NotFoundUserException(String.format("Not found user with provided email: %s.", email)));
		
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
	public User updateUserInfo(User userToUpdate, User userWithNewInformation) {
		userToUpdate.setFirstname(userWithNewInformation.getFirstname());
		userToUpdate.setLastname(userWithNewInformation.getLastname());
		userToUpdate.setEmail(userWithNewInformation.getEmail());
		userToUpdate.setAvatarUrl(userWithNewInformation.getAvatarUrl());
		return userRepository.save(userToUpdate);
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
			throw new NotFoundUserException(externalUser);
				
		return user;
	}

	@Override
	@Transactional
	public Optional<User> findUserById(Long id) {
		return userRepository.findOneById(id);
	}

	@Override
	public boolean exists(Long id) {
		return userRepository.exists(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existByEmail(email);
	}

	@Override
	@Transactional
	public User updateUser(User user) throws UpdateUserException {
		
		User userFromDb;
		
		Optional<User> optionalUserByEamil = userRepository
				.findOneByEmail(user.getEmail());
		
		if(optionalUserByEamil.isPresent()) {
			if(optionalUserByEamil.get().getId() != user.getId())
				throw new UpdateUserException("Email is occupied by another user!");
			else
				userFromDb = optionalUserByEamil.get();
		}
		else {
			userFromDb = userRepository
					.findOneById(user.getId())
					.orElseThrow(() -> new UpdateUserException("Could not find user with provided id!"));
		}

		userFromDb.setFirstname(user.getFirstname());
		userFromDb.setLastname(user.getLastname());
		userFromDb.setEmail(user.getEmail());
		
		return userRepository.save(userFromDb);
	}
	
	@PostConstruct
	public void userRoleInit() {
		Role userRole = Role
				.builder()
				.description("Role for standard app users.")
				.roleName("ROLE_USER")
				.build();
		
		Role adminRole = Role
				.builder()
				.description("Role for admin users.")
				.roleName("ROLE_ADMIN")
				.build();
		
		if(!roleRepository.existByRoleName(userRole.getRoleName()))
			roleRepository.save(userRole);
		
		if(!roleRepository.existByRoleName(adminRole.getRoleName()))
			roleRepository.save(adminRole);
		
		log.info("Roles created!");
	}
}
