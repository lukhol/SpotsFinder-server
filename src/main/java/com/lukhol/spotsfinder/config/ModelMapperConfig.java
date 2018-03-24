package com.lukhol.spotsfinder.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lukhol.spotsfinder.dto.CoordinatesDTO;
import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.dto.WrongPlaceReportDTO;
import com.lukhol.spotsfinder.model.Image;
import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.model.WrongPlaceReport;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper getModelMapperBean() {
		ModelMapper mapper = new ModelMapper();

		PropertyMap<Place, HeavyPlaceDTO> heavyPlaceDTOMap = new PropertyMap<Place, HeavyPlaceDTO>() {		
			@Override
			protected void configure() {
				map().setUserId(source.getOwner().getId());
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
		
		Converter<Place, LightPlaceDTO> placeConverter = new AbstractConverter<Place, LightPlaceDTO>() {
		    @Override
		    protected LightPlaceDTO convert(Place source) {
		    	LightPlaceDTO destination = new LightPlaceDTO();
		        destination.setLocation(new CoordinatesDTO());
		        
		        destination.setId(source.getId());
		        destination.setVersion(source.getVersion());
		        destination.setName(source.getName());
		        destination.setDescription(source.getDescription());
		        destination.setType(source.getType());
		        destination.getLocation().setLatitude(source.getLatitude());
		        destination.getLocation().setLongitude(source.getLongitude());
		        destination.setMainPhoto(source.getMainPhoto());

		        return destination;
		    }
		};
		mapper.addConverter(placeConverter);
		
		Converter<WrongPlaceReport, WrongPlaceReportDTO> wrongPlaceReportConverter =  new AbstractConverter<WrongPlaceReport, WrongPlaceReportDTO>(){
			@Override 
			protected WrongPlaceReportDTO convert(WrongPlaceReport source){
				WrongPlaceReportDTO destination = new WrongPlaceReportDTO();
				
				if(source.getPlace() != null)
					destination.setPlaceId(source.getPlace().getId());
				else 
					destination.setPlaceId(0);
				
				destination.setPlaceVersion(source.getReportedPlaceVersion());
				destination.setDeviceId(source.getDeviceId());
				destination.setReasonComment(source.getReasonComment());
				destination.setNotSkateboardPlace(source.isNotSkateboardPlace());
					
				return destination;
			}
		};
		mapper.addConverter(wrongPlaceReportConverter);
		
		Converter<WrongPlaceReportDTO, WrongPlaceReport> wrongPlaceReportDtoConverter =  new AbstractConverter<WrongPlaceReportDTO, WrongPlaceReport>(){
			@Override
			protected WrongPlaceReport convert(WrongPlaceReportDTO source){
				WrongPlaceReport destination = new WrongPlaceReport();
				
				destination.setId(null);
				destination.setUser(null);
				destination.setPlace(null);
				destination.setDeviceId(source.getDeviceId());
				destination.setNotSkateboardPlace(source.isNotSkateboardPlace());
				destination.setReasonComment(source.getReasonComment());
				destination.setReportedPlaceVersion(source.getPlaceVersion());
				
				return destination;
			}
		};
		mapper.addConverter(wrongPlaceReportDtoConverter);
		
		return mapper;
	}
}
