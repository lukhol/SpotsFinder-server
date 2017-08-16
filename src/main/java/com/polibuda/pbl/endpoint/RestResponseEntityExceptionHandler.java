package com.polibuda.pbl.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.polibuda.pbl.exception.SkateSpotException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value= {SkateSpotException.class})
	protected ResponseEntity<RestResponse<Void>> handleUnknownException(SkateSpotException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);

		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
}
