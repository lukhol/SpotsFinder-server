package com.lukhol.spotsfinder.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.dto.PlaceSearchDTO;
import com.lukhol.spotsfinder.exception.GeocodingCityException;
import com.lukhol.spotsfinder.exception.InvalidPlaceSearchException;
import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.service.PlaceService;
import com.lukhol.spotsfinder.validator.PlaceSearchValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/places/searches")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlaceSearchRestEndpoint {

	private final PlaceService placeService;
	private final PlaceSearchValidator searchValidator;

	@PostMapping
	public ResponseEntity<?> searchPlaces(@RequestBody PlaceSearchDTO searchDto) throws InvalidPlaceSearchException, GeocodingCityException {
		log.info("POST /places/searches body: {}", searchDto);
		
		searchValidator.validate(searchDto);
		List<LightPlaceDTO> places = placeService.search(searchDto);
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		if(places == null || places.size() == 0)
			httpStatus = HttpStatus.NOT_FOUND;
		
		return new ResponseEntity<List<LightPlaceDTO>>(places, httpStatus);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> searchUserPlaces(@PathVariable long userId) throws NotFoundUserException {
		log.info("GET /places/searches/{}", userId);
		
		List<LightPlaceDTO> places = placeService.searchByUserId(userId);
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		if(places == null || places.isEmpty())
			httpStatus = HttpStatus.NOT_FOUND;
		
		return new ResponseEntity<List<LightPlaceDTO>>(places, httpStatus);
	}
}
