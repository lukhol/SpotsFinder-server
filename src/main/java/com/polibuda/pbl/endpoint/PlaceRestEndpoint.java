package com.polibuda.pbl.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.dto.PlaceDTO;
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
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<PlaceDTO> getAll() {
		log.debug("GET /places");
		
		return placeService.getAll();
	}
	
	@RequestMapping(value="/{placeId}", method=RequestMethod.GET)
	public ResponseEntity<PlaceDTO> getById(@PathVariable Long placeId) {
		log.debug("GET /places/{}", placeId);
		
		boolean exists = placeService.exists(placeId);
		if(exists) {
			PlaceDTO place = placeService.getById(placeId);
			return new ResponseEntity<PlaceDTO>(place, HttpStatus.OK);
		}
		log.warn("No place with id = {}", placeId);
		return new ResponseEntity<PlaceDTO>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<PlaceDTO> addSkateSpot(@RequestBody PlaceDTO placeDto) throws InvalidPlaceException {
		log.debug("POST /places body: {}", placeDto);
		
		placeValidator.validate(placeDto);
		PlaceDTO place = placeService.save(placeDto);
		return new ResponseEntity<PlaceDTO>(place, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{placeId}", method=RequestMethod.PUT)
	public ResponseEntity<PlaceDTO> replaceSkateSpot(@RequestBody PlaceDTO placeDto, @PathVariable Long placeId) throws InvalidPlaceException {
		log.debug("PUT /places/{} body: {}", placeId, placeDto);
		
		placeValidator.validate(placeDto);
		PlaceDTO place = placeService.save(placeDto);
		return new ResponseEntity<PlaceDTO>(place, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{placeId}", method=RequestMethod.DELETE)
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
