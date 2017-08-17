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
			protected void configure() {
				map().setLocation(new LocationDTO(source.getLongitude(), source.getLatitude()));
			    map(source.getLongitude(), destination.getLocation().getLongitude());
			    map(source.getLatitude(), destination.getLocation().getLatitude());
		  	}
		};

		mapper.addMappings(placeMap);
		return mapper;
	}
}
