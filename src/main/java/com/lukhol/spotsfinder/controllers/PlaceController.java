package com.lukhol.spotsfinder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/views/places")
public class PlaceController {

	@RequestMapping(value = "/random", method = RequestMethod.GET)
	public String randomPlace() {
		return "placePage";
	}
}
