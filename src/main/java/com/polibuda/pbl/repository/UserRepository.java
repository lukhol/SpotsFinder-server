package com.polibuda.pbl.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.polibuda.pbl.model.User;

@Transactional
public interface UserRepository extends Repository<User, Long>{
	Optional<User> findOneByUsername(String username);
}
