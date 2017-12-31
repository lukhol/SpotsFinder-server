package com.polibuda.pbl.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.polibuda.pbl.dto.CoordinatesDTO;
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
		
		PropertyMap<HeavyPlaceDTO, Place> placeMap = new PropertyMap<HeavyPlaceDTO, Place>() {		
			@Override
			protected void configure() {
				map().setLatitude(source.getLocation().getLatitude());
				map().setLongitude(source.getLocation().getLongitude());
			}
		};
		mapper.addMappings(placeMap);
		
		Converter<Place, LightPlaceDTO> converter = new AbstractConverter<Place, LightPlaceDTO>() {
		    @Override
		    protected LightPlaceDTO convert(Place source) {
		    	LightPlaceDTO destination = new LightPlaceDTO();
		        destination.setLocation(new CoordinatesDTO());
		        
		        destination.setId(source.getId());
		        destination.setName(source.getName());
		        destination.setDescription(source.getDescription());
		        destination.setType(source.getType());
		        destination.getLocation().setLatitude(source.getLatitude());
		        destination.getLocation().setLongitude(source.getLongitude());
		        destination.setMainPhoto(source.getMainPhoto());

		        return destination;
		    }
		};
		mapper.addConverter(converter);
		
		return mapper;
	}
}
