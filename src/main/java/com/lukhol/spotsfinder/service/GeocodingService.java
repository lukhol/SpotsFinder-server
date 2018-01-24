package com.lukhol.spotsfinder.service;

import com.lukhol.spotsfinder.exception.NotFoundGeocodingInformationException;
import com.lukhol.spotsfinder.model.GeocodingInformation;

public interface GeocodingService {
	GeocodingInformation findBySearchingPhrase(String searchingPhrase) throws NotFoundGeocodingInformationException;
	GeocodingInformation save(GeocodingInformation geocodingInformation);
}
