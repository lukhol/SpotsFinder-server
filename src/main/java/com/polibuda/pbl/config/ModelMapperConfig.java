package com.polibuda.pbl.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.polibuda.pbl.dto.LocationDTO;
import com.polibuda.pbl.dto.PlaceDTO;
import com.polibuda.pbl.model.Place;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper getModelMapperBean() {
		ModelMapper mapper = new ModelMapper();

		
		PropertyMap<Place, PlaceDTO> placeMap = new PropertyMap<Place, PlaceDTO>() {		
			@Override
			protected void configure() {
				map().setLocation(new LocationDTO());
				map().getLocation().setLatitude(source.getLatitude());
				map().getLocation().setLongitude(source.getLongitude());
			}
		};
		mapper.addMappings(placeMap);
		
		
		PropertyMap<PlaceDTO, Place> placeDTOMap = new PropertyMap<PlaceDTO, Place>() {		
			@Override
			protected void configure() {
				map().setLatitude(source.getLocation().getLatitude());
				map().setLongitude(source.getLocation().getLongitude());
			}
		};
		mapper.addMappings(placeDTOMap);
		return mapper;
	}
}
