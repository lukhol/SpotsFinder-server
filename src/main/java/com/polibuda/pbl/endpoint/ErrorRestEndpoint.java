package com.polibuda.pbl.endpoint;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polibuda.pbl.email.EmailSender;
import com.polibuda.pbl.model.MobileAppError;
import com.polibuda.pbl.service.MobileAppErrorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/errors")
public class ErrorRestEndpoint {
	
	@NotNull
	private final MobileAppErrorService errorService;
	
	@NotNull
	private final EmailSender emailSender;
	
	@Autowired
	public ErrorRestEndpoint(MobileAppErrorService errorService, EmailSender emailSender){
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
