package com.polibuda.pbl.endpoint;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.exception.InvalidPlaceException;
import com.polibuda.pbl.service.PlaceService;
import com.polibuda.pbl.validator.PlaceValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/places")
public class PlaceRestEndpoint {

	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private PlaceValidator placeValidator;
	
	@GetMapping
	public List<LightPlaceDTO> getAll() {
		log.debug("GET /places");
		
		return placeService.getAll();
	}
	
	@GetMapping(value="/{placeId}")
	public ResponseEntity<HeavyPlaceDTO> getById(@PathVariable Long placeId) {
		log.debug("GET /places/{}", placeId);
		
		boolean exists = placeService.exists(placeId);
		if(exists) {
			HeavyPlaceDTO place = placeService.getById(placeId);
			return new ResponseEntity<HeavyPlaceDTO>(place, HttpStatus.OK);
		}
		log.warn("No place with id = {}", placeId);
		return new ResponseEntity<HeavyPlaceDTO>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	public ResponseEntity<HeavyPlaceDTO> addPlace(@RequestBody HeavyPlaceDTO placeDto) throws InvalidPlaceException, IOException {
		log.debug("POST /places body: {}", placeDto);
		
		placeValidator.validate(placeDto);
		HeavyPlaceDTO place = placeService.save(placeDto);
		return new ResponseEntity<HeavyPlaceDTO>(place, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{placeId}")
	public ResponseEntity<HeavyPlaceDTO> replacePlace(@RequestBody HeavyPlaceDTO placeDto, @PathVariable Long placeId) throws InvalidPlaceException, IOException {
		log.debug("PUT /places/{} body: {}", placeId, placeDto);
		
		placeValidator.validate(placeDto);
		HeavyPlaceDTO place = placeService.save(placeDto);
		return new ResponseEntity<HeavyPlaceDTO>(place, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{placeId}")
	public ResponseEntity<String> delete(@PathVariable Long placeId) {
		log.debug("DELETE /places/{}", placeId);
		
		boolean exists = placeService.exists(placeId);
		if(!exists) {
			log.warn("No place with id = {}", placeId);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		placeService.delete(placeId);
		log.debug("Place with id = {} deleted succesfully.", placeId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
