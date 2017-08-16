package com.polibuda.pbl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.dto.SkateSpotDTO;
import com.polibuda.pbl.model.SkateSpot;
import com.polibuda.pbl.repository.SkateSpotRepository;

@Service
public class SkateSpotServiceImpl implements SkateSpotService {

	@Autowired
	private SkateSpotRepository skateSpotRepository;
	
	@Override
	public List<SkateSpotDTO> getAll() {
		List<SkateSpot> skateSpots = skateSpotRepository.findAll();
		// TODO Convert to DTO
		return null;
	}

	@Override
	public SkateSpotDTO save(SkateSpotDTO skateSpotDto) {
		 // SkateSpot skateSpot = skateSpotRepository.save(skateSpot);
		// TODO Convert to DTO
		return null;
	}

	@Override
	public boolean exists(Long skateSpotId) {		
		return skateSpotRepository.exists(skateSpotId);
	}

	@Override
	public SkateSpotDTO getById(Long skateSpotId) {
		SkateSpot skateSpot = skateSpotRepository.findOne(skateSpotId).get();
		// TODO Convert to DTO
		return null;
	}

	@Override
	public void delete(Long skateSpotId) {
		skateSpotRepository.delete(skateSpotId);		
	}
}
