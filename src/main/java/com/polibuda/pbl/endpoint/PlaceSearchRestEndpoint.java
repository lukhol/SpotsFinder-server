package com.polibuda.pbl.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.exception.InvalidPlaceSearchException;
import com.polibuda.pbl.service.PlaceService;
import com.polibuda.pbl.validator.PlaceSearchValidator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/places/searches")
public class PlaceSearchRestEndpoint {

	private final PlaceService placeService;
	private final PlaceSearchValidator searchValidator;
	
	@Autowired
	public PlaceSearchRestEndpoint(@NonNull PlaceService placeService, @NonNull PlaceSearchValidator searchValidator) {
		super();
		this.placeService = placeService;
		this.searchValidator = searchValidator;
	}

	@PostMapping
	public List<LightPlaceDTO> searchPlaces(@RequestBody PlaceSearchDTO searchDto) throws InvalidPlaceSearchException, GeocodingCityException {
		log.debug("POST /places/searches body: {}", searchDto);
		
		searchValidator.validate(searchDto);
		List<LightPlaceDTO> places = placeService.search(searchDto);
		
		return places;
	}
}
