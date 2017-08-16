package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.SkateSpotDTO;

public interface SkateSpotService {

	List<SkateSpotDTO> getAll();

	SkateSpotDTO save(SkateSpotDTO skateSpotDto);

	boolean exists(Long skateSpotId);
	
	SkateSpotDTO getById(Long skateSpotId);

	void delete(Long skateSpotId);

}
