package com.lukhol.spotsfinder.endpoint;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.exception.InvalidPlaceException;
import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.service.PlaceService;
import com.lukhol.spotsfinder.validator.PlaceValidator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/places")
public class PlaceRestEndpoint {

	private PlaceService placeService;
	private PlaceValidator placeValidator;
	
	@Autowired
	public PlaceRestEndpoint(@NonNull PlaceService placeService, @NonNull PlaceValidator placeValidator) {
		super();
		this.placeService = placeService;
		this.placeValidator = placeValidator;
	}

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
	//@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<HeavyPlaceDTO> addPlace(@RequestBody HeavyPlaceDTO placeDto) throws InvalidPlaceException, IOException, NotFoundUserException {
		log.debug("POST /places body: {}", placeDto);
		
		placeValidator.validate(placeDto);
		HeavyPlaceDTO place = placeService.save(placeDto);
		
		return new ResponseEntity<HeavyPlaceDTO>(place, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{placeId}")
	//@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Long> replacePlace(@RequestBody HeavyPlaceDTO placeDto, @PathVariable Long placeId, Authentication authentication) throws InvalidPlaceException, IOException, NotFoundUserException {
		log.debug("PUT /places/{} body: {}", placeId, placeDto);
		
		placeValidator.validate(placeDto);
		
		String userIdString = authentication.getName();
		long userId = Long.parseLong(userIdString);
		boolean exists = placeService.existAndBelongToUser(placeId, userId);
		
		if(!exists) {
			new ResponseEntity<Long>(0l, HttpStatus.BAD_REQUEST); 
		}
		
		HeavyPlaceDTO place = placeService.save(placeDto);
		placeService.clearImagesWithoutPlace();
		
		return new ResponseEntity<Long>(place.getVersion(), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{placeId}")
	//@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> delete(@PathVariable Long placeId, Authentication authentication) {
		log.debug("DELETE /places/{}", placeId);
		
		String userIdString = authentication.getName();
		long userId = Long.parseLong(userIdString);
		
		boolean exists = placeService.existAndBelongToUser(placeId, userId);
		if(!exists) {
			log.warn("No place with id = {} belongs to user with id = {}", placeId, userId);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		placeService.delete(placeId);
		
		log.debug("Place with id = {} deleted succesfully.", placeId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:7777")
	@RequestMapping(value = "/recentlyAdded", method = RequestMethod.GET)
	public List<LightPlaceDTO> randomPlace(@RequestParam("start") int start, @RequestParam("count") int count) {
		return placeService.getRecentlyAdded(start, count);
	}
}
