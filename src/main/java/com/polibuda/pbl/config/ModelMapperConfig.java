package com.polibuda.pbl.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.model.Place;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper getModelMapperBean() {
		ModelMapper mapper = new ModelMapper();

		PropertyMap<Place, HeavyPlaceDTO> heavyPlaceDTOMap = new PropertyMap<Place, HeavyPlaceDTO>() {		
			@Override
			protected void configure() {
				map().getLocation().setLatitude(source.getLatitude());
				map().getLocation().setLongitude(source.getLongitude());
			}
		};
		mapper.addMappings(heavyPlaceDTOMap);
		
		PropertyMap<Place, LightPlaceDTO> lightPlaceDTOMap = new PropertyMap<Place, LightPlaceDTO>() {		
			@Override
			protected void configure() {
				map().getLocation().setLatitude(source.getLatitude());
				map().getLocation().setLongitude(source.getLongitude());
				map().setMainPhoto(source.getImages().get(0));
			}
		};
		mapper.addMappings(lightPlaceDTOMap);
		
		PropertyMap<HeavyPlaceDTO, Place> placeMap = new PropertyMap<HeavyPlaceDTO, Place>() {		
			@Override
			protected void configure() {
				map().setLatitude(source.getLocation().getLatitude());
				map().setLongitude(source.getLocation().getLongitude());
			}
		};
		mapper.addMappings(placeMap);
		return mapper;
	}
}
