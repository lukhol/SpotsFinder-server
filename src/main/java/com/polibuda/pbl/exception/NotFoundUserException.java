package com.polibuda.pbl.exception;

public class NotFoundUserException extends Exception {
	
	private static final long serialVersionUID = 328082720816826736L;

	public NotFoundUserException() {
		super();
	}
	
	public NotFoundUserException(String message){
		super(message);
	}
}
