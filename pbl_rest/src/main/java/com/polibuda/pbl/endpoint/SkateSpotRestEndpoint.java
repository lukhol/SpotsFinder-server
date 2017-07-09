package com.polibuda.pbl.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.dto.SkateSpotDto;
import com.polibuda.pbl.service.SkateSpotService;

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
}
