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

import com.polibuda.pbl.dto.SkateParkDto;
import com.polibuda.pbl.service.SkateParkService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/skateparks")
public class SkateParktRestEndpoint {
	
	@Autowired
	private SkateParkService skateParkService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<SkateParkDto> getAll() {
		log.debug("GET /skateparks");
		return skateParkService.getAll();
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public SkateParkDto addSkatePark(@RequestBody SkateParkDto skateParkDto) {
		log.debug("POST /skateparks body: {}", skateParkDto);
		return skateParkService.add(skateParkDto);
	}
	
	@RequestMapping(value="/{skateParkId}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String skateParkId) {
		log.debug("DELETE /skateparks/{}", skateParkId);
		boolean exists = skateParkService.exists(skateParkId);
		if(exists) {
			String body = "Nie istnieje skate park o podanym id... ";
			log.debug(body + skateParkId);
			return new ResponseEntity<String>(body, HttpStatus.NO_CONTENT);
		}
		skateParkService.delete(skateParkId);
		log.debug("Usunieto skate park o id rownym {}", skateParkId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
