package com.lukhol.spotsfinder.repository;

import java.util.List;

import com.lukhol.spotsfinder.dto.PlaceSearchDTO;
import com.lukhol.spotsfinder.exception.GeocodingCityException;
import com.lukhol.spotsfinder.model.Place;

public interface CustomPlaceRepository {
	public List<Place> search(PlaceSearchDTO searchCriteria) throws GeocodingCityException;
}
