package com.lukhol.spotsfinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class GeocodingInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long geocodingId;

	private String searchingPhrase;
	private String oryginalCityName;
	private double longitude;
	private double latitude;

	@Column(columnDefinition = "TEXT")
	private String oryginalGoogleResponseJson;

	public GeocodingInformation() { }

	public GeocodingInformation(String searchingPhrase, String oryginalCityName, String oryginalGoogleResponseJson,
			double longitude, double latitude) {
		this.searchingPhrase = searchingPhrase;
		this.oryginalCityName = oryginalCityName;
		this.oryginalGoogleResponseJson = oryginalGoogleResponseJson;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
