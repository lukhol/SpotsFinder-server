package com.lukhol.spotsfinder.exception;

import org.springframework.validation.BindingResult;

public class RegisterUserException extends Exception implements ServiceValidationException {

	private static final long serialVersionUID = -8371920161486325810L;
	private BindingResult bindingResult;

	public RegisterUserException() {
		super();
	}
	
	public RegisterUserException(String message){
		super(message);
	}
	
	public RegisterUserException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}
	
	@Override
	public BindingResult getBindingResult() {
		return bindingResult;
	}
}
