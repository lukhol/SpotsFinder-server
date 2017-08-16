package com.polibuda.pbl.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.dto.SkateSpotDTO;
import com.polibuda.pbl.model.SkateSpot;
import com.polibuda.pbl.repository.SkateSpotRepository;

@Service
public class SkateSpotServiceImpl implements SkateSpotService {

	@Autowired
	private SkateSpotRepository skateSpotRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<SkateSpotDTO> getAll() {
		return skateSpotRepository.findAll()
			.stream()
			.map(spot -> convertToDTO(spot))
			.collect(Collectors.toList());
	}

	@Override
	public SkateSpotDTO save(SkateSpotDTO skateSpotDto) {
		SkateSpot skateSpot = skateSpotRepository.save(convertToModel(skateSpotDto));
		return convertToDTO(skateSpot);
	}

	@Override
	public boolean exists(Long skateSpotId) {		
		return skateSpotRepository.exists(skateSpotId);
	}

	@Override
	public SkateSpotDTO getById(Long skateSpotId) {
		SkateSpot skateSpot = skateSpotRepository.findOne(skateSpotId).get();
		return convertToDTO(skateSpot);
	}

	@Override
	public void delete(Long skateSpotId) {
		skateSpotRepository.delete(skateSpotId);		
	}
	
	private SkateSpotDTO convertToDTO(SkateSpot skateSpot) {
		return modelMapper.map(skateSpot, SkateSpotDTO.class);
	}
	
	private SkateSpot convertToModel(SkateSpotDTO skateSpotDto) {
		return modelMapper.map(skateSpotDto, SkateSpot.class);
	}
}
