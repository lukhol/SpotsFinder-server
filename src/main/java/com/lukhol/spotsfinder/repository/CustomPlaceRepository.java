package com.lukhol.spotsfinder.repository;

import java.util.List;
import java.util.Optional;

import com.lukhol.spotsfinder.dto.PlaceSearchDTO;
import com.lukhol.spotsfinder.exception.GeocodingCityException;
import com.lukhol.spotsfinder.model.Place;

public interface CustomPlaceRepository {
	List<Place> search(PlaceSearchDTO searchCriteria) throws GeocodingCityException;
	Place getReference(Long id);
}
