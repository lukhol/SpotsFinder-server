package com.lukhol.spotsfinder.endpoint;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.lukhol.spotsfinder.dto.WrongPlaceReportDTO;
import com.lukhol.spotsfinder.exception.InvalidWrongPlaceReportException;
import com.lukhol.spotsfinder.service.WrongPlaceReportService;
import com.lukhol.spotsfinder.validator.WrongPlaceReportValidator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ReportPlaceRestEndpoint {

	private final WrongPlaceReportValidator wrongPlaceReportValidator;
	private final WrongPlaceReportService wrongPlaceReportService;
	
	@Autowired
	public ReportPlaceRestEndpoint(@NonNull WrongPlaceReportValidator wrongPlaceReportValidator,
			@NonNull WrongPlaceReportService wrongPlaceReportService) {
		super();
		this.wrongPlaceReportValidator = wrongPlaceReportValidator;
		this.wrongPlaceReportService = wrongPlaceReportService;
	}

	@PostMapping("/places/report")
	public ResponseEntity<WrongPlaceReportDTO> report(@RequestHeader(value="Accept-Language") String acceptLanguage, 
			@Valid @RequestBody WrongPlaceReportDTO wrongPlaceReportDto) throws InvalidWrongPlaceReportException {
		log.debug("Post /places/report. PlaceId = {}, PlaceVersion = {}, UserId = {}",  wrongPlaceReportDto.getPlaceId(),
				wrongPlaceReportDto.getPlaceVersion(), wrongPlaceReportDto.getUserId());
		
		//Walkaroung my default location problem:
		if(!acceptLanguage.contains("en") && !acceptLanguage.contains("pl"))
			acceptLanguage = "en";
			
		wrongPlaceReportValidator.validate(wrongPlaceReportDto);
		WrongPlaceReportDTO result = wrongPlaceReportService.save(wrongPlaceReportDto, Locale.forLanguageTag(acceptLanguage));
		
		if(result == null)
			return new ResponseEntity<WrongPlaceReportDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<WrongPlaceReportDTO>(result, HttpStatus.OK);
	}
}
