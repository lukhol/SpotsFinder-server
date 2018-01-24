package com.lukhol.spotsfinder.exception;

public class RegisterUserException extends Exception {

	private static final long serialVersionUID = -8371920161486325810L;

	public RegisterUserException() {
		super();
	}
	
	public RegisterUserException(String message){
		super(message);
	}
}
