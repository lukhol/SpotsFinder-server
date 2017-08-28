package com.polibuda.pbl.endpoint;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.polibuda.pbl.exception.InvalidPlaceException;
import com.polibuda.pbl.exception.InvalidPlaceSearchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value= {InvalidPlaceException.class})
	protected ResponseEntity<RestResponse<Void>> handlePlaceException(InvalidPlaceException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);

		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value= {InvalidPlaceSearchException.class})
	protected ResponseEntity<RestResponse<Void>> handleSearchException(InvalidPlaceSearchException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);

		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value= {IOException.class})
	protected ResponseEntity<RestResponse<Void>> handleIOException(IOException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		log.error("Error while scaling image.");
		
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
}
