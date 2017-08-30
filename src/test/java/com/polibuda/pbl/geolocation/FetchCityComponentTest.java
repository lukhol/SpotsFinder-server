package com.polibuda.pbl.geolocation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.polibuda.pbl.exception.GeocodingCityException;

@RunWith(JUnit4.class)
public class FetchCityComponentTest {
	
	@Test
	public void testFetchCityCoordinates() throws GeocodingCityException {
		
		FetchCityComponent fetchCityComponent = new FetchCityComponent();
		
		double[] expectedWarsaw = new double[] {52.2296756, 21.0122287};
		double[] coordinatesWarsaw = fetchCityComponent.fetchCityCoordinates("Warszawa");
		
		assertTrue(expectedWarsaw[0] == coordinatesWarsaw[0]);
		assertTrue(expectedWarsaw[1] == coordinatesWarsaw[1]);
	}

}
