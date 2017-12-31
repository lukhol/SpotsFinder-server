package com.polibuda.pbl.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polibuda.pbl.model.MobileAppError;
import com.polibuda.pbl.service.MobileAppErrorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/errors")
public class ErrorRestEndpoint {
	
	@Autowired
	MobileAppErrorService errorService;

	@PostMapping
	public void LogError(@RequestBody MobileAppError mobileAppError){
		log.info("POST /errors body: {}", mobileAppError);
		errorService.save(mobileAppError);
	}
}
