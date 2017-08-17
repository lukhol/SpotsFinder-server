package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.PlaceDTO;
import com.polibuda.pbl.repository.SearchCriteria;

public interface PlaceService {

	List<PlaceDTO> getAll();

	PlaceDTO save(PlaceDTO placeDto);

	boolean exists(Long placeId);
	
	PlaceDTO getById(Long placeId);

	void delete(Long placeId);

	List<PlaceDTO> getByFilter(List<SearchCriteria> params);

}
