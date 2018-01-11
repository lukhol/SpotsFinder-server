package com.polibuda.pbl.exception;

public class RegisterExternalServiceUserException extends Exception {

	private static final long serialVersionUID = 1353819172353630146L;

	public RegisterExternalServiceUserException(){
		super();
	}
	
	public RegisterExternalServiceUserException(String message){
		super(message);
	}
}
