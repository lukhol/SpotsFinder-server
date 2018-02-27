package com.lukhol.spotsfinder.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.service.PlaceService;

import lombok.NonNull;

@Controller
@RequestMapping("/views/places")
public class PlaceController {

	@Value("${server.baseurl}")
	private String BASE_URL;
	
	private final PlaceService placeService;
	
	@Autowired
	public PlaceController(@NonNull PlaceService placeService) {
		this.placeService = placeService;
	}
	
	@RequestMapping(value = "/recentlyAdded", method = RequestMethod.GET)
	public String randomPlace(@RequestParam("start") int start, @RequestParam("count") int count, Model model) {
		List<LightPlaceDTO> recentlyAddedPlaces = placeService.getRecentlyAdded(start, count);
		model.addAttribute("recentlyAddedPlaces", recentlyAddedPlaces);
		model.addAttribute("BASE_URL", BASE_URL);
		return "placeRecentlyAddedPage";
	}
	
	@RequestMapping(value = "/{placeId}", method = RequestMethod.GET)
	public String place(@PathVariable("placeId") long placeId, Model model) {
		HeavyPlaceDTO place = placeService.getById(placeId);
		model.addAttribute("place", place);
		return "placeDetailsPage";
	}
	
	@RequestMapping(value = "/map")
	public String map(Model model) {
		model.addAttribute("BASE_URL", BASE_URL);
		return "placeMap";
	}
}
