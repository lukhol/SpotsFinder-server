package com.polibuda.pbl.service;

import com.polibuda.pbl.exception.NotFoundGeocodingInformationException;
import com.polibuda.pbl.model.GeocodingInformation;

public interface GeocodingService {
	GeocodingInformation findBySearchingPhrase(String searchingPhrase) throws NotFoundGeocodingInformationException;
	void save(GeocodingInformation geocodingInformation);
}
