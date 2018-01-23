package com.polibuda.pbl.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.exception.InvalidPlaceException;
import com.polibuda.pbl.exception.InvalidPlaceSearchException;
import com.polibuda.pbl.exception.InvalidWrongPlaceReportException;
import com.polibuda.pbl.exception.NotFoundUserException;
import com.polibuda.pbl.exception.RegisterExternalServiceUserException;
import com.polibuda.pbl.exception.RegisterUserException;
import com.polibuda.pbl.exception.ResetPasswordException;
import com.polibuda.pbl.exception.UpdateUserException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {//extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value= { InvalidPlaceException.class, GeocodingCityException.class, InvalidPlaceSearchException.class,
			IOException.class, ResetPasswordException.class, UpdateUserException.class })
	protected ResponseEntity<RestResponse<Void>> handlePlaceException(Exception ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { InvalidWrongPlaceReportException.class, })
	protected ResponseEntity<RestResponse<Void>> handleWrongReportPlaceException(InvalidWrongPlaceReportException ex, WebRequest request){
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<RestResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException error, WebRequest request)  {
	   return parseErrors(error.getBindingResult());
	}
	
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<?> handleControllerFieldsValidationException(ConstraintViolationException exception, WebRequest request){
		List<String> listOfErrors =  exception
				.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
		
		return new ResponseEntity<List<String>>(listOfErrors, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = NotFoundUserException.class)
	public ResponseEntity<RestResponse<Void>> handleNotFoundUserException(NotFoundUserException ex, WebRequest request)  {
		log.error(ex.getMessage());
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = { RegisterExternalServiceUserException.class, RegisterUserException.class })
	public ResponseEntity<RestResponse<Void>> handleRegisterUserException(Exception ex, WebRequest request){
		log.error(ex.getMessage());
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ResponseEntity<RestResponse<Void>> parseErrors(BindingResult bindingResult){
		return new ResponseEntity<RestResponse<Void>>(HttpStatus.BAD_REQUEST);
	}
}
