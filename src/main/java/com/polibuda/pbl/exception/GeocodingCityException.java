package com.polibuda.pbl.exception;

public class GeocodingCityException extends Exception {

	private static final long serialVersionUID = -82272483365595035L;

	private String message;
	
	public GeocodingCityException() {}
	
	public GeocodingCityException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
