package com.lukhol.spotsfinder.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.model.Place;

import lombok.NonNull;

@Component
public class PlaceDTOMapper {

	private final ModelMapper modelMapper;
	
	@Autowired
	public PlaceDTOMapper(@NonNull ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	public HeavyPlaceDTO mapToHeavyDTO(Place place) {
		return modelMapper.map(place, HeavyPlaceDTO.class);
	}
	
	public Place mapHeavyToModel(HeavyPlaceDTO placeDto) {
		return modelMapper.map(placeDto, Place.class);
	}
	
	public LightPlaceDTO mapToLightDTO(Place place) {
		return modelMapper.map(place, LightPlaceDTO.class);
	}
}
