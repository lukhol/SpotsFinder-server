package com.polibuda.pbl.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.polibuda.pbl.model.SkatePark;

@Transactional
public interface SkateParkRepository extends Repository<SkatePark, Long> {

	void delete(Long id);
	
	boolean exists(Long id);
	
	List<SkatePark> findAll();
	
	Optional<SkatePark> findOne(Long id);
	
	SkatePark save(SkatePark skatePark);
}
