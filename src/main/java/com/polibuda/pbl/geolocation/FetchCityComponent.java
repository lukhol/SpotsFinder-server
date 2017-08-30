package com.polibuda.pbl.geolocation;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.polibuda.pbl.exception.GeocodingCityException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FetchCityComponent {

	private static final String GOOGLE_MAPS_GEOCODING_API_KEY = "AIzaSyCScQ7EgUqnPkOcxCvf_X7qOOEHIV0t74o";
	
	public double[] fetchCityCoordinates(String cityName) throws GeocodingCityException {
		
		GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_MAPS_GEOCODING_API_KEY);
		
		GeocodingResult[] results;
		try {
			results = GeocodingApi.newRequest(context).address(cityName).await();
			double latitude = results[0].geometry.location.lat;
			double longitude = results[0].geometry.location.lng;
			
			return new double[] { latitude, longitude };
		} catch (ApiException | InterruptedException | IOException e) {
			log.error("Error while searching location coordinates of given city");
			log.error(e.getMessage());
			
			throw new GeocodingCityException(e.getMessage());
		}
			
	}
}
