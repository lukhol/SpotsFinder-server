package com.polibuda.pbl.endpoint;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.exception.InvalidPlaceException;
import com.polibuda.pbl.exception.InvalidPlaceSearchException;
import com.polibuda.pbl.exception.InvalidWrongPlaceReportException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value= { InvalidPlaceException.class, GeocodingCityException.class, InvalidPlaceSearchException.class, IOException.class})
	protected ResponseEntity<RestResponse<Void>> handlePlaceException(Exception ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { InvalidWrongPlaceReportException.class, })
	protected ResponseEntity<RestResponse<Void>> handleWrongReportPlaceException(InvalidWrongPlaceReportException ex, WebRequest request){
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
}
