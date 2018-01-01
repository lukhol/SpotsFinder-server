package com.polibuda.pbl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GeocodingInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String searchingPhrase;
	private String oryginalCityName;
	private double longitude;
	private double latitude;
	
	@Column(columnDefinition="TEXT")
	private String oryginalGoogleResponseJson;

	public GeocodingInformation() {}
	
	public GeocodingInformation(
			String searchingPhrase, 
			String oryginalCityName, 
			String oryginalGoogleResponseJson, 
			double longitude, 
			double latitude){
		this.searchingPhrase = searchingPhrase;
		this.oryginalCityName = oryginalCityName;
		this.oryginalGoogleResponseJson = oryginalGoogleResponseJson;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearchingPhrase() {
		return searchingPhrase;
	}

	public void setSearchingPhrase(String searchingPhrase) {
		this.searchingPhrase = searchingPhrase;
	}

	public String getOryginalCityName() {
		return oryginalCityName;
	}

	public void setOryginalCityName(String oryginalCityName) {
		this.oryginalCityName = oryginalCityName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getOryginalGoogleResponseJson() {
		return oryginalGoogleResponseJson;
	}

	public void setOryginalGoogleResponseJson(String oryginalGoogleResponseJson) {
		this.oryginalGoogleResponseJson = oryginalGoogleResponseJson;
	}
}
