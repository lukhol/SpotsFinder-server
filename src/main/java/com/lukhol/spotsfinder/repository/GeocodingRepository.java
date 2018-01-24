package com.lukhol.spotsfinder.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.lukhol.spotsfinder.model.GeocodingInformation;

@Transactional
public interface GeocodingRepository extends Repository<GeocodingInformation, Long> {
	GeocodingInformation save(GeocodingInformation geocodingInformation);
	GeocodingInformation findBySearchingPhraseIgnoringCase(String searchingPhrase);
	GeocodingInformation findByOryginalCityNameIgnoringCase(String oryginalCityName);
}
