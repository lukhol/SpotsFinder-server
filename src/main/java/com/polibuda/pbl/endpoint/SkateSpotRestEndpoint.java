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

import com.polibuda.pbl.dto.SkateSpotDTO;
import com.polibuda.pbl.exception.SkateSpotException;
import com.polibuda.pbl.service.SkateSpotService;
import com.polibuda.pbl.validator.SkateSpotValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/skatespots")
public class SkateSpotRestEndpoint {

	@Autowired
	private SkateSpotService skateSpotService;
	
	@Autowired
	private SkateSpotValidator skateSpotValidator;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<SkateSpotDTO> getAll() {
		log.debug("GET /skatespots");
		return skateSpotService.getAll();
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> addSkateSpot(@RequestBody SkateSpotDTO skateSpotDto) {
		log.debug("POST /skatespots body: {}", skateSpotDto);
		
		try {
			skateSpotValidator.validate(skateSpotDto);
			SkateSpotDTO skateSpot = skateSpotService.add(skateSpotDto);
			return new ResponseEntity<String>(Long.toString(skateSpot.getId()), HttpStatus.CREATED);
		} catch (SkateSpotException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/{skateSpotId}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String skateSpotId) {
		log.debug("DELETE /skatespots/{}", skateSpotId);
		boolean exists = skateSpotService.exists(skateSpotId);
		if(exists) {
			String body = "Nie istnieje skate spot o podanym id... ";
			log.debug(body + skateSpotId);
			return new ResponseEntity<String>(body, HttpStatus.NO_CONTENT);
		}
		skateSpotService.delete(skateSpotId);
		log.debug("Usunieto skate spot o id rownym {}", skateSpotId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
