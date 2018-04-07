package com.lukhol.spotsfinder.endpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.lukhol.spotsfinder.exception.GeocodingCityException;
import com.lukhol.spotsfinder.exception.InvalidPlaceException;
import com.lukhol.spotsfinder.exception.InvalidPlaceSearchException;
import com.lukhol.spotsfinder.exception.InvalidWrongPlaceReportException;
import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.exception.RegisterUserException;
import com.lukhol.spotsfinder.exception.ResetPasswordException;
import com.lukhol.spotsfinder.exception.ServiceValidationException;
import com.lukhol.spotsfinder.exception.UpdateUserException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RestResponseEntityExceptionHandler {

	private final MessageSource messageSource;
	
	@ExceptionHandler(value= {  GeocodingCityException.class, InvalidPlaceSearchException.class,
			IOException.class, ResetPasswordException.class, UpdateUserException.class })
	
	protected ResponseEntity<RestResponse<Void>> handlePlaceException(Exception ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidWrongPlaceReportException.class)
	protected ResponseEntity<RestResponse<Void>> handleWrongReportPlaceException(InvalidWrongPlaceReportException ex, WebRequest request){
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NotFoundUserException.class)
	public ResponseEntity<RestResponse<Void>> handleNotFoundUserException(NotFoundUserException ex, WebRequest request)  {
		log.error(ex.getMessage());
		return new ResponseEntity<RestResponse<Void>>(new RestResponse<Void>(Boolean.FALSE, ex.getMessage(), null), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException error, WebRequest request)  {
	   return parseErrorWithoutMessage(error.getBindingResult());
	}
	
	@ExceptionHandler(value = { RegisterUserException.class, InvalidPlaceException.class })
	public ResponseEntity<?> handleRegisterUserException(ServiceValidationException error, WebRequest request)  {
		String acceptLanguage = request.getHeader("Accept-Language");
		
		if(acceptLanguage == null || !acceptLanguage.contains("en") && !acceptLanguage.contains("pl"))
			acceptLanguage = "en";
		
		return parseErrors(error.getBindingResult(), acceptLanguage);
	}
	
	private ResponseEntity<Map<String, String>> parseErrorWithoutMessage(BindingResult bindingResult) {
		return parseErrors(bindingResult, null);
	}
	
	private ResponseEntity<Map<String, String>> parseErrors(BindingResult bindingResult, String acceptLanguage) {
		Map<String, String> errors = new HashMap<>();
		for(ObjectError objectError : bindingResult.getAllErrors()) {
			FieldError fieldError = (FieldError)objectError;
			if(acceptLanguage != null) {
				try {
					String message = null;
					
					try {
						message = messageSource.getMessage(fieldError.getCode(), null, Locale.forLanguageTag(acceptLanguage));
					} catch (NoSuchMessageException e) {
						message = fieldError.getDefaultMessage();
						
						if(message == null)
							message = "Validation error.";
					}
					
					errors.put(fieldError.getField(), message);
				} catch (NoSuchMessageException e) {
					errors.put(fieldError.getField(), objectError.getDefaultMessage());
				}
			} else {
				errors.put(fieldError.getField(), objectError.getDefaultMessage());
			}
		}
		
		return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
	}
}
