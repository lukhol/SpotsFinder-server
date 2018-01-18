package com.polibuda.pbl.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.polibuda.pbl.exception.NotFoundGeocodingInformationException;
import com.polibuda.pbl.model.GeocodingInformation;
import com.polibuda.pbl.repository.GeocodingRepository;

@RunWith(JUnit4.class)
public class GeocodingServiceTests {

	private GeocodingService geocodingService;
	private GeocodingInformation geocodingInformation;
	
	@Mock
	private GeocodingRepository geocodingRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		geocodingService = new GeocodingServiceImpl(geocodingRepository);
		
		geocodingInformation = GeocodingInformation.builder()
				.geocodingId(1l)
				.latitude(51)
				.longitude(19)
				.oryginalCityName("£Ûdü")
				.searchingPhrase("≥Ûdü")
				.build();
	}
	
	@Test
	public void canFindBySearchingPhrase() throws NotFoundGeocodingInformationException {
		
		Mockito
			.when(geocodingRepository.findBySearchingPhraseIgnoringCase("≥Ûdü"))
			.thenReturn(geocodingInformation);
		
		GeocodingInformation returnedGI = geocodingService.findBySearchingPhrase("≥Ûdü");
		
		assertReturnedGeocodingInformation(returnedGI);
	}
	
	@Test
	public void canFindGeocodingInformation() throws NotFoundGeocodingInformationException {
		
		setupGeocodingRepository(null, geocodingInformation);
			
		GeocodingInformation returnedGI = geocodingService.findBySearchingPhrase("≥Ûdü");
		
		assertReturnedGeocodingInformation(returnedGI);
		
		Mockito
			.verify(geocodingRepository, Mockito.times(1))
			.findByOryginalCityNameIgnoringCase("≥Ûdü");
	}
	
	@Test(expected = NotFoundGeocodingInformationException.class)
	public void test() throws NotFoundGeocodingInformationException {
		
		setupGeocodingRepository(null, null);
		geocodingService.findBySearchingPhrase("≥Ûdü");
	}
	
	private void setupGeocodingRepository(GeocodingInformation giOne, GeocodingInformation giTwo) {
		Mockito
			.when(geocodingRepository.findBySearchingPhraseIgnoringCase("≥Ûdü"))
			.thenReturn(giOne);

		Mockito
			.when(geocodingRepository.findByOryginalCityNameIgnoringCase("≥Ûdü"))
			.thenReturn(giTwo);
	}
	
	private void assertReturnedGeocodingInformation(GeocodingInformation returnedGI) {
		assert returnedGI != null;
		assert returnedGI.getLatitude() == 51;
		assert returnedGI.getLongitude() == 19;
		assert returnedGI.getOryginalCityName().equals("£Ûdü");
		assert returnedGI.getSearchingPhrase().equals("≥Ûdü");
		
		Mockito
			.verify(geocodingRepository, Mockito.times(1))
			.findBySearchingPhraseIgnoringCase("≥Ûdü");
	}
}
