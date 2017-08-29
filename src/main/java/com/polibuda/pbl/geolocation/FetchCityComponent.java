package com.polibuda.pbl.geolocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchCityComponent {

	@Autowired
	private RestTemplate rest;
	
	private static final String GOOGLE_MAPS_GEOCODING_API_KEY = "AIzaSyCScQ7EgUqnPkOcxCvf_X7qOOEHIV0t74o";
	
	private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	
	public double[] fetchCityCoordinates(String cityName) {
		
		String url = BASE_URL + cityName + "&key=" + GOOGLE_MAPS_GEOCODING_API_KEY;
		
		rest.getForObject(url, Object.class); // TODO
		
		return null;
	}
}
