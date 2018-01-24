package com.lukhol.spotsfinder.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukhol.spotsfinder.email.EmailSender;
import com.lukhol.spotsfinder.model.MobileAppError;
import com.lukhol.spotsfinder.service.MobileAppErrorService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/errors")
public class ErrorRestEndpoint {
	
	private final MobileAppErrorService errorService;
	private final EmailSender emailSender;
	
	@Autowired
	public ErrorRestEndpoint(@NonNull MobileAppErrorService errorService, @NonNull EmailSender emailSender){
		this.errorService = errorService;
		this.emailSender = emailSender;
	}
	
	@PostMapping
	public ResponseEntity<Void> logError(@RequestBody MobileAppError mobileAppError) throws JsonProcessingException{
		log.info("POST /errors body: {}", mobileAppError);
		
		errorService.save(mobileAppError);
		sendEmail(mobileAppError);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	private void sendEmail(MobileAppError mobileAppError) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		String errorAsString = objectMapper.writeValueAsString(mobileAppError);
		emailSender.sendEmail("lukasz.holdrowicz@gmail.com", "Error json:" , errorAsString);
	}
}
