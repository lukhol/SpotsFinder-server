package com.lukhol.spotsfinder.service;

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

import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.imageconverter.ImageConverter;
import com.lukhol.spotsfinder.mapper.PlaceDTOMapper;
import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.repository.PlaceJsonRepository;
import com.lukhol.spotsfinder.repository.PlaceRepository;
import com.lukhol.spotsfinder.repository.UserRepository;
import com.lukhol.spotsfinder.service.PlaceService;
import com.lukhol.spotsfinder.service.PlaceServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceServiceTest {

	@Mock private PlaceRepository placeRepository;
	@Mock private ImageConverter imageConverter;
	@Mock private UserRepository userRepository;
	@Mock private PlaceJsonRepository placeJsonRepository; 
	@Autowired private PlaceDTOMapper placeDTOMapper;
	
	@Autowired
	private PlaceService placeService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		placeService = new PlaceServiceImpl(placeRepository, userRepository, placeDTOMapper, imageConverter, placeJsonRepository);
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
