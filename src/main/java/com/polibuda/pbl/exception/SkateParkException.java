package com.polibuda.pbl.exception;

public class SkateParkException extends Exception {
	
	private static final long serialVersionUID = 5507493619823240116L;
	
	private String message;
	
	public SkateParkException() {}
	
	public SkateParkException(String message) {
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
