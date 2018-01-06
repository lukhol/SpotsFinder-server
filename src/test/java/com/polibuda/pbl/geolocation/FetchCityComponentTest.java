package com.polibuda.pbl.geolocation;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.exception.NotFoundGeocodingInformationException;
import com.polibuda.pbl.model.GeocodingInformation;
import com.polibuda.pbl.service.GeocodingService;

@RunWith(JUnit4.class)
public class FetchCityComponentTest {
	
	@Mock
	private GeocodingService geocodingService;
	private FetchCityComponent fetchCityComponent;
	double[] expectedWarsaw = new double[] {52.2296756, 21.0122287};
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		fetchCityComponent = new FetchCityComponent(geocodingService);
	}
	
	@Test(expected = NullPointerException.class)
	public void cannotCreatFetchCityComponent_nullInjectionOnConstructor(){
		fetchCityComponent = new FetchCityComponent(null);
	}
	
	@Test
	public void canFetchCityCoordinates_fromGeocodingService() throws GeocodingCityException, NotFoundGeocodingInformationException {
		
		GeocodingInformation geocodingInformation = new GeocodingInformation();
		geocodingInformation.setLatitude(52.2296756);
		geocodingInformation.setLongitude(21.0122287);
		
		when(geocodingService.findBySearchingPhrase("Warszawa")).thenReturn(geocodingInformation);
		
		double[] coordinatesWarsaw = fetchCityComponent.fetchCityCoordinates("Warszawa");
		
		assertTrue(expectedWarsaw[0] == coordinatesWarsaw[0]);
		assertTrue(expectedWarsaw[1] == coordinatesWarsaw[1]);
		
		Mockito
			.verify(geocodingService, Mockito.times(1))
			.findBySearchingPhrase("Warszawa");
		
		Mockito
			.verify(geocodingService, Mockito.times(0))
			.save(Mockito.any(GeocodingInformation.class));
	}
	
	@Test
	public void canFetchCityCoordinates_fromGoogleGeocodingApi() throws NotFoundGeocodingInformationException, GeocodingCityException{
		when(geocodingService.findBySearchingPhrase("Warszawa")).thenThrow(NotFoundGeocodingInformationException.class);
		
		double[] coordinatesWarsaw = fetchCityComponent.fetchCityCoordinates("Warszawa");
		
		assertTrue(expectedWarsaw[0] == coordinatesWarsaw[0]);
		assertTrue(expectedWarsaw[1] == coordinatesWarsaw[1]);
		
		Mockito
			.verify(geocodingService, Mockito.times(1))
			.findBySearchingPhrase("Warszawa");
		
		Mockito
			.verify(geocodingService, Mockito.times(1))
			.save(Mockito.any(GeocodingInformation.class));
	}
}
