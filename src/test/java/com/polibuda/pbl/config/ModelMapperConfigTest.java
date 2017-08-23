package com.polibuda.pbl.config;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.polibuda.pbl.dto.CoordinatesDTO;
import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.model.Place;

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
				.type(1)
				.latitude(50.1234)
				.longitude(45.9876)
				.build();
		
		HeavyPlaceDTO placeDTO = mapper.map(place, HeavyPlaceDTO.class);
		
		assert placeDTO.getName().equals(place.getName());
		assert placeDTO.getDescription().equals(place.getDescription());
		assert placeDTO.isCorners() == place.isCorners();
		assert placeDTO.getLocation().getLatitude() == place.getLatitude();
		assert placeDTO.getLocation().getLongitude() == place.getLongitude();
		assert placeDTO.getType() == place.getType();
	}
	
	@Test
	public void testPlaceDtoToModelConversion() {
		HeavyPlaceDTO placeDTO = HeavyPlaceDTO.builder()
				.corners(true)
				.name("nazwa")
				.description("description")
				.type(0)
				.location(new CoordinatesDTO(50.1234, 45.9462))
				.build();
		
		Place place = mapper.map(placeDTO, Place.class);
		
		assert placeDTO.getName().equals(place.getName());
		assert placeDTO.getDescription().equals(place.getDescription());
		assert placeDTO.isCorners() == place.isCorners();
		assert placeDTO.getLocation().getLatitude() == place.getLatitude();
		assert placeDTO.getLocation().getLongitude() == place.getLongitude();
		assert placeDTO.getType() == place.getType();
	}
	
	@Test
	public void testMultiConversion() {
		ArrayList<HeavyPlaceDTO> places = new ArrayList<HeavyPlaceDTO>();
		for(int i = 0; i < 10; i++) {
			Place place = Place.builder()
					.corners(true)
					.name("name " + i)
					.description("opis" + i)
					.type(1)
					.latitude(1.1234 + i)
					.longitude(2.9876 * i)
					.build();
				
			HeavyPlaceDTO placeDTO = mapper.map(place, HeavyPlaceDTO.class);
			places.add(placeDTO);
			
			assert placeDTO.getName().equals(place.getName());
			assert placeDTO.getDescription().equals(place.getDescription());
			assert placeDTO.isCorners() == place.isCorners();
			assert placeDTO.getLocation().getLatitude() == place.getLatitude();
			assert placeDTO.getLocation().getLongitude() == place.getLongitude();
			assert placeDTO.getType() == place.getType();
		}
		for(HeavyPlaceDTO p : places) 
		System.out.println(p);
	}
}
