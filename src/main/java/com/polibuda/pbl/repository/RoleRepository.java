package com.polibuda.pbl.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.polibuda.pbl.model.Role;

@Transactional
public interface RoleRepository extends Repository<Role, Long> {
	Optional<Role> findOneByRoleName(String roleName);
}
