package com.lukhol.spotsfinder.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.model.Role;

@Transactional
public interface RoleRepository extends Repository<Role, Long> {
	Optional<Role> findOneByRoleName(String roleName);
}
