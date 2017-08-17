package com.polibuda.pbl.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.polibuda.pbl.dto.LocationDTO;
import com.polibuda.pbl.dto.PlaceDTO;
import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.model.Type;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ModelMapperConfig.class, loader=AnnotationConfigContextLoader.class)
public class ModelMapperConfigTest {

	@Autowired
	private ModelMapper mapper;
	
	@Test
	public void testPlaceToDtoConversion() {
		Place place = Place.builder()
				.corners(true)
				.name("name")
				.description("opis")
				.type(Type.SKATEPARK)
				.latitude(50.1234)
				.longitude(45.9876)
				.build();
		
		PlaceDTO placeDTO = mapper.map(place, PlaceDTO.class);
		
		assert placeDTO.getName().equals(place.getName());
		assert placeDTO.getDescription().equals(place.getDescription());
		assert placeDTO.isCorners() == place.isCorners();
		assert placeDTO.getLocation().getLatitude() == place.getLatitude();
		assert placeDTO.getLocation().getLongitude() == place.getLongitude();
		assert placeDTO.getType().equals(place.getType());
	}
	
	@Test
	public void testPlaceDtoToModelConversion() {
		PlaceDTO placeDTO = PlaceDTO.builder()
				.corners(true)
				.name("nazwa")
				.description("description")
				.type(Type.DIY)
				.location(new LocationDTO(50.1234, 45.9462))
				.build();
		
		Place place = mapper.map(placeDTO, Place.class);
		
		assert placeDTO.getName().equals(place.getName());
		assert placeDTO.getDescription().equals(place.getDescription());
		assert placeDTO.isCorners() == place.isCorners();
		assert placeDTO.getLocation().getLatitude() == place.getLatitude();
		assert placeDTO.getLocation().getLongitude() == place.getLongitude();
		assert placeDTO.getType().equals(place.getType());
	}
}
