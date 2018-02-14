package com.lukhol.spotsfinder.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.model.User;

@Transactional
public interface PlaceRepository extends Repository<Place, Long>, CustomPlaceRepository {

	void delete(Long id);
	boolean exists(Long id);
	List<Place> findAll();
	Optional<Place> findOneById(Long id);
	List<Place> findByOwner(User owner);
	Place save(Place place);
}
