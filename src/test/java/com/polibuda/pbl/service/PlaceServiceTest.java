package com.polibuda.pbl.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.imageconverter.ImageConverter;
import com.polibuda.pbl.mapper.PlaceDTOMapper;
import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.repository.PlaceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceServiceTest {

	@Mock private PlaceRepository placeRepository;
	@Mock private ImageConverter imageConverter;
	@Autowired private PlaceDTOMapper placeDTOMapper;
	
	@Autowired
	private PlaceService placeService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		placeService = new PlaceServiceImpl(placeRepository, placeDTOMapper, imageConverter);
	}
	
	@Test
	public void testGetAll() {
		
		List<Place> places = new ArrayList<>();
		
		for(int i = 0; i < 10; i++) {
			Place place = Place.builder()
					.corners(true)
					.name("name " + i)
					.description("opis" + i)
					.type(1)
					.latitude(1.1234 + i)
					.longitude(2.9876 * i)
					.build();
			
			places.add(place);
		}
		
		when(placeRepository.findAll()).thenReturn(places);
		
		List<LightPlaceDTO> placesDTO = placeService.getAll();
		assert placesDTO != null;
		assert placesDTO.size() == 10;
		
	}
}
