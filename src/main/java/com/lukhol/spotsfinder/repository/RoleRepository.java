package com.lukhol.spotsfinder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.model.Role;

@Transactional
public interface RoleRepository extends Repository<Role, Long> {
	Optional<Role> findOneByRoleName(String roleName);
	void save(Role role);
	
	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Role r where r.roleName = :roleName")
	boolean existByRoleName(@Param("roleName") String roleName);
}
