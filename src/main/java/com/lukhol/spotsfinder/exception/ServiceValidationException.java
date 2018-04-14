package com.lukhol.spotsfinder.exception;

import org.springframework.validation.BindingResult;

public class ServiceValidationException extends RuntimeException {

	private static final long serialVersionUID = 7984245554303334160L;

	private final BindingResult bindingResult;
	
	public ServiceValidationException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}
}
