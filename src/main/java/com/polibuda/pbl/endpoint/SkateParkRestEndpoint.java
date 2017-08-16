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

import com.polibuda.pbl.dto.SkateParkDTO;
import com.polibuda.pbl.exception.SkateParkException;
import com.polibuda.pbl.service.SkateParkService;
import com.polibuda.pbl.validator.SkateParkValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/skateparks")
public class SkateParkRestEndpoint {
	
	@Autowired
	private SkateParkService skateParkService;
	
	@Autowired
	private SkateParkValidator skateParkValidator;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<SkateParkDTO> getAll() {
		log.debug("GET /skatespots");
		return skateParkService.getAll();
	}
	
	@RequestMapping(value="/{skateParkId}", method=RequestMethod.GET)
	public ResponseEntity<SkateParkDTO> getById(@PathVariable Long skateParkId) {
		log.debug("GET /skatespots/{}", skateParkId);
		
		boolean exists = skateParkService.exists(skateParkId);
		if(exists) {
			SkateParkDTO skatePark = skateParkService.getById(skateParkId);
			return new ResponseEntity<SkateParkDTO>(skatePark, HttpStatus.OK);
		}
		return new ResponseEntity<SkateParkDTO>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<SkateParkDTO> addSkatePark(@RequestBody SkateParkDTO skateParkDto) throws SkateParkException {
		log.debug("POST /skatespots body: {}", skateParkDto);
		
		skateParkValidator.validate(skateParkDto);
		SkateParkDTO skatePark = skateParkService.save(skateParkDto);
		return new ResponseEntity<SkateParkDTO>(skatePark, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{skateParkId}", method=RequestMethod.PUT)
	public ResponseEntity<SkateParkDTO> replaceSkatePark(@RequestBody SkateParkDTO skateParkDto, @PathVariable Long skateParkId) throws SkateParkException {
		log.debug("PUT /skatespots/{} body: {}", skateParkId, skateParkDto);
		
		skateParkValidator.validate(skateParkDto);
		SkateParkDTO skatePark = skateParkService.save(skateParkDto);
		return new ResponseEntity<SkateParkDTO>(skatePark, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{skateParkId}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long skateParkId) {
		log.debug("DELETE /skatespots/{}", skateParkId);
		boolean exists = skateParkService.exists(skateParkId);
		if(!exists) {
			log.debug("Nie istnieje skate spot o id rownym {}", skateParkId);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
		skateParkService.delete(skateParkId);
		log.debug("Usunieto skate spot o id równym {}", skateParkId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
