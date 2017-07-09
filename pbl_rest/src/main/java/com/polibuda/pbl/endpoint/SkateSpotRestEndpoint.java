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

import com.polibuda.pbl.dto.SkateSpotDto;
import com.polibuda.pbl.service.SkateSpotService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/skatespots")
public class SkateSpotRestEndpoint {

	@Autowired
	private SkateSpotService skateSpotService; 
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<SkateSpotDto> getAll() {
		return skateSpotService.getAll();
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public SkateSpotDto addSkateSpot(@RequestBody SkateSpotDto skateSpotDto) {
		return skateSpotService.add(skateSpotDto);
	}
	
	@RequestMapping(value="/{skateSpotId}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String skateSpotId) {
		boolean exists = skateSpotService.exists(skateSpotId);
		if(exists) {
			String body = "Nie istnieje skate spot o podanym id... ";
			log.debug(body + skateSpotId);
			return new ResponseEntity<String>(body, HttpStatus.NO_CONTENT);
		}
		skateSpotService.delete(skateSpotId);
		log.debug("Usunieto skate spot o id rownym " + skateSpotId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
