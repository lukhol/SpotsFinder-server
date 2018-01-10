package com.polibuda.pbl.exception;

public class RegisterFacebookUserException extends Exception {

	private static final long serialVersionUID = 1353819172353630146L;

	public RegisterFacebookUserException(){
		super();
	}
	
	public RegisterFacebookUserException(String message){
		super(message);
	}
}
