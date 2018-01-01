package com.polibuda.pbl.service;

import com.polibuda.pbl.exception.NotFoundGeocodingInformation;
import com.polibuda.pbl.model.GeocodingInformation;

public interface GeocodingService {
	GeocodingInformation findBySearchingPhrase(String searchingPhrase) throws NotFoundGeocodingInformation;
	void save(GeocodingInformation geocodingInformation);
}
