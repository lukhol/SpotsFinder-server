package com.polibuda.pbl.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.polibuda.pbl.model.GeocodingInformation;

@Transactional
public interface GeocodingRepository extends Repository<GeocodingInformation, Long> {
	GeocodingInformation save(GeocodingInformation geocodingInformation);
	GeocodingInformation findBySearchingPhraseIgnoringCase(String searchingPhrase);
	GeocodingInformation findByOryginalCityNameIgnoringCase(String oryginalCityName);
}
