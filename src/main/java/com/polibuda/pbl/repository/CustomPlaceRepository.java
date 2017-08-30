package com.polibuda.pbl.repository;

import java.util.List;

import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.model.Place;

public interface CustomPlaceRepository {
	
	public List<Place> search(PlaceSearchDTO searchCriteria) throws GeocodingCityException;
}
