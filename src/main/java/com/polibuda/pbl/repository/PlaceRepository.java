package com.polibuda.pbl.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.model.User;

@Transactional
public interface PlaceRepository extends Repository<Place, Long>, CustomPlaceRepository {

	void delete(Long id);
	boolean exists(Long id);
	List<Place> findAll();
	Optional<Place> findOneById(Long id);
	Place save(Place place);
	List<Place> findByOwner(User owner);
}
