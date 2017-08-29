package com.polibuda.pbl.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchCityComponent {

	@Autowired
	private RestTemplate rest;
	
	public double[] fetchCityCoordinates(String cityName) {
		
		// TODO 
		
		// rest.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address={city}");
		
		return null;
	}
}
