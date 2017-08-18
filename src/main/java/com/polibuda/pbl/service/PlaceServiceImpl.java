package com.polibuda.pbl.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.dto.PlaceDTO;
import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.repository.PlaceRepository;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<PlaceDTO> getAll() {
		return placeRepository.findAll()
			.stream()
			.map(place -> convertToDTO(place))
			.collect(Collectors.toList());
	}

	@Override
	public PlaceDTO save(PlaceDTO placeDto) {
		Place place = placeRepository.save(convertToModel(placeDto));
		return convertToDTO(place);
	}

	@Override
	public boolean exists(Long placeId) {		
		return placeRepository.exists(placeId);
	}

	@Override
	public PlaceDTO getById(Long placeId) {
		Place place = placeRepository.findOne(placeId).get();
		return convertToDTO(place);
	}

	@Override
	public void delete(Long placeId) {
		placeRepository.delete(placeId);		
	}
	
	private PlaceDTO convertToDTO(Place place) {
		return modelMapper.map(place, PlaceDTO.class);
	}
	
	private Place convertToModel(PlaceDTO placeDto) {
		return modelMapper.map(placeDto, Place.class);
	}
}
