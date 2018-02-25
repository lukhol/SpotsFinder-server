package com.lukhol.spotsfinder.service;

import java.io.IOException;
import java.util.List;

import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.dto.PlaceSearchDTO;
import com.lukhol.spotsfinder.exception.GeocodingCityException;
import com.lukhol.spotsfinder.exception.NotFoundUserException;

public interface PlaceService {
	HeavyPlaceDTO save(HeavyPlaceDTO placeDto) throws IOException, NotFoundUserException;
	HeavyPlaceDTO update(HeavyPlaceDTO placeDTO) throws IOException, NotFoundUserException;
	
	void delete(Long placeId);
	boolean exists(Long placeId);
	boolean existAndBelongToUser(Long placeId, Long userId);
	
	List<LightPlaceDTO> getAll();
	HeavyPlaceDTO getById(Long placeId);
	
	List<LightPlaceDTO> getRecentlyAdded(int start, int count);
	List<LightPlaceDTO> search(PlaceSearchDTO placeDto) throws GeocodingCityException;
	List<LightPlaceDTO> searchByUserId(long userId) throws NotFoundUserException;
}
