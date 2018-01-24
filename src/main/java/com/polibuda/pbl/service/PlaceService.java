package com.polibuda.pbl.service;

import java.io.IOException;
import java.util.List;

import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.exception.NotFoundUserException;

public interface PlaceService {
	HeavyPlaceDTO save(HeavyPlaceDTO placeDto) throws IOException, NotFoundUserException;
	HeavyPlaceDTO update(HeavyPlaceDTO placeDTO) throws IOException, NotFoundUserException;
	
	void delete(Long placeId);
	boolean exists(Long placeId);
	
	List<LightPlaceDTO> getAll();
	HeavyPlaceDTO getById(Long placeId);
	
	List<LightPlaceDTO> search(PlaceSearchDTO placeDto) throws GeocodingCityException;
	List<LightPlaceDTO> searchByUserId(long userId) throws NotFoundUserException;
}
