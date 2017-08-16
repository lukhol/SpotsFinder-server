package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.SkateSpotDTO;

public interface SkateSpotService {

	List<SkateSpotDTO> getAll();

	String add(SkateSpotDTO skateSpotDto);

	boolean exists(String skateSpotId);
	
	SkateSpotDTO getById(String skateSpotId);

	void delete(String skateSpotId);

}
