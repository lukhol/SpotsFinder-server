package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;

public interface PlaceService {

	List<LightPlaceDTO> getAll();

	HeavyPlaceDTO save(HeavyPlaceDTO placeDto);

	boolean exists(Long placeId);
	
	HeavyPlaceDTO getById(Long placeId);

	void delete(Long placeId);

	List<LightPlaceDTO> search(PlaceSearchDTO placeDto);
}
