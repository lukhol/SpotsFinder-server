package com.polibuda.pbl.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.polibuda.pbl.model.SkateSpot;

@Transactional
public interface SkateSpotRepository extends Repository<SkateSpot, Long> {

	void delete(Long id);
	
	boolean exists(Long id);
	
	List<SkateSpot> findAll();
	
	Optional<SkateSpot> findOne(Long id);
	
	SkateSpot save(SkateSpot skateSpot);
}
