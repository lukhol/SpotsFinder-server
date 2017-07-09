package com.polibuda.pbl.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.dto.SkateParkDto;
import com.polibuda.pbl.service.SkateParkService;

@RestController
@RequestMapping("/skateparks")
public class SkateParktRestEndpoint {
	
	@Autowired
	private SkateParkService skateParkService; 
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<SkateParkDto> getAll() {
		return skateParkService.getAll();
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public SkateParkDto addSkatePark(@RequestBody SkateParkDto skateParkDto) {
		return skateParkService.add(skateParkDto);
	}
}
