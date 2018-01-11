package com.polibuda.pbl.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.polibuda.pbl.model.User;

@Transactional
public interface UserRepository extends Repository<User, Long>{
	Optional<User> findOneByEmail(String email);
	Optional<User> findOneByFacebookId(String facebookId);
	Optional<User> findOneByGoogleId(String googleId);
	Optional<User> findOneByEmailOrFacebookId(String email, String facebookId);
	Optional<User> findOneById(Long id);
	User save(User user);
}
