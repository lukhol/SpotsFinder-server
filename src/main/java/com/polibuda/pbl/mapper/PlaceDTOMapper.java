package com.polibuda.pbl.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.model.Place;

@Component
public class PlaceDTOMapper {

	@Autowired
	private ModelMapper modelMapper;
	
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
