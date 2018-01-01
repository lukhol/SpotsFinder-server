package com.polibuda.pbl.exception;

public class NotFoundGeocodingInformation extends Exception {
	
	private static final long serialVersionUID = 8659033134351612267L;

	public NotFoundGeocodingInformation() {
		super();
	}
	
	public NotFoundGeocodingInformation(String message){
		super(message);
	}
}
