package com.lukhol.spotsfinder.exception;

import org.springframework.validation.BindingResult;

public interface IServiceValidationException {
	BindingResult getBindingResult();
}
