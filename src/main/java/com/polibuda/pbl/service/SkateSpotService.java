package com.polibuda.pbl.service;

import java.util.List;

import com.polibuda.pbl.dto.SkateSpotDto;

public interface SkateSpotService {

	List<SkateSpotDto> getAll();

	SkateSpotDto add(SkateSpotDto skateSpotDto);

	boolean exists(String skateSpotId);

	void delete(String skateSpotId);

}
