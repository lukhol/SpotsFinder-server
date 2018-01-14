package com.polibuda.pbl.service;

import java.io.IOException;
import java.util.List;

import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.exception.NotFoundUserException;

public interface PlaceService {

	List<LightPlaceDTO> getAll();

	HeavyPlaceDTO save(HeavyPlaceDTO placeDto) throws IOException, NotFoundUserException;

	boolean exists(Long placeId);
	
	HeavyPlaceDTO getById(Long placeId);

	void delete(Long placeId);

	List<LightPlaceDTO> search(PlaceSearchDTO placeDto) throws GeocodingCityException;
	
	List<LightPlaceDTO> searchByUserId(long userId) throws NotFoundUserException;
}
