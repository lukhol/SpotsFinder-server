package com.lukhol.spotsfinder.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.model.PlaceJson;

//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

@Transactional
public interface PlaceJsonRepository extends Repository<PlaceJson, Long> {
	void save(PlaceJson placeJson);
}
