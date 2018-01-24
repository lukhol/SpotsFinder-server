package com.lukhol.spotsfinder.exception;

public class InvalidWrongPlaceReportException extends Exception {
	
	private static final long serialVersionUID = 4761946887543231477L;

	public InvalidWrongPlaceReportException() {
		super();
	}
	
	public InvalidWrongPlaceReportException(String message){
		super(message);
	}
}
