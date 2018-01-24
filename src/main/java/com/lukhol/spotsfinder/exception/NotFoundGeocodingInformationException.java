package com.lukhol.spotsfinder.exception;

public class NotFoundGeocodingInformationException extends Exception {
	
	private static final long serialVersionUID = 8659033134351612267L;

	public NotFoundGeocodingInformationException() {
		super();
	}
	
	public NotFoundGeocodingInformationException(String message){
		super(message);
	}
}
