package com.lukhol.spotsfinder.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.lukhol.spotsfinder.dto.WrongPlaceReportDTO;
import com.lukhol.spotsfinder.exception.InvalidWrongPlaceReportException;
import com.lukhol.spotsfinder.validator.WrongPlaceReportValidator;

@RunWith(JUnit4.class)
public class WrongPlaceReportValidatorTests {

	private WrongPlaceReportValidator wrongPlaceReportValidator;
	private WrongPlaceReportDTO properWrongPlaceReportDTO;
	
	@Before
	public void setUp() {
		wrongPlaceReportValidator = new WrongPlaceReportValidator();
		properWrongPlaceReportDTO = WrongPlaceReportDTO
				.builder()
				.deviceId("deviceId123")
				.placeVersion(987654321l)
				.placeId(10l)
				.build();
	}
	
	@Test(expected = InvalidWrongPlaceReportException.class)
	public void cannotValidate_placeIdIsLessThanOne() throws InvalidWrongPlaceReportException {
		properWrongPlaceReportDTO.setPlaceId(0);
		wrongPlaceReportValidator.validate(properWrongPlaceReportDTO);
	}
	
	@Test(expected = InvalidWrongPlaceReportException.class)
	public void cannotValidate_deviceIdIsNotProvided() throws InvalidWrongPlaceReportException {
		properWrongPlaceReportDTO.setDeviceId("");
		wrongPlaceReportValidator.validate(properWrongPlaceReportDTO);
	}
	
	@Test(expected = InvalidWrongPlaceReportException.class)
	public void cannotValidate_wrongPlaceVersion() throws InvalidWrongPlaceReportException {
		properWrongPlaceReportDTO.setPlaceVersion(-5l);
		wrongPlaceReportValidator.validate(properWrongPlaceReportDTO);
	}
	
	@Test
	public void canValidate_properWrongPlaceReport() throws InvalidWrongPlaceReportException {
		wrongPlaceReportValidator.validate(properWrongPlaceReportDTO);
	}
}
