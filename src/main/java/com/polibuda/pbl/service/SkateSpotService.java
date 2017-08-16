package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.SkateSpotDTO;

public interface SkateSpotService {

	List<SkateSpotDTO> getAll();

	SkateSpotDTO add(SkateSpotDTO skateSpotDto);

	boolean exists(String skateSpotId);

	void delete(String skateSpotId);

}
