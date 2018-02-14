package com.lukhol.spotsfinder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findOneByEmail(String email);
	Optional<User> findOneByFacebookId(String facebookId);
	Optional<User> findOneByGoogleId(String googleId);
	Optional<User> findOneByEmailOrFacebookId(String email, String facebookId);
	Optional<User> findOneById(Long id);
	
	boolean exists(Long id);
	
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u where u.email=:email")
	boolean existByEmail(@Param("email") String email);
}
