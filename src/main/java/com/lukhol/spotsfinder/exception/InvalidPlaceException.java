package com.lukhol.spotsfinder.exception;

import org.springframework.validation.BindingResult;

public class InvalidPlaceException extends Exception implements IServiceValidationException {

	private static final long serialVersionUID = 2164821908471678571L;

	private String message;
	private BindingResult bindingResult;
	
	public InvalidPlaceException() {}
	
	public InvalidPlaceException(String message) {
		this.message = message;
	}
	
	public InvalidPlaceException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public BindingResult getBindingResult() {
		return this.bindingResult;
	}
}
