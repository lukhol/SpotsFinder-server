package com.polibuda.pbl.exception;

public class SkateSpotException extends Exception {

	private static final long serialVersionUID = 2164821908471678571L;

	private String message;
	
	public SkateSpotException() {}
	
	public SkateSpotException(String message) {
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
