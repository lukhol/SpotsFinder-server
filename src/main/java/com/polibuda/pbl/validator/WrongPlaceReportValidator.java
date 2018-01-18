package com.polibuda.pbl.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.polibuda.pbl.dto.WrongPlaceReportDTO;
import com.polibuda.pbl.exception.InvalidWrongPlaceReportException;

@Component
public class WrongPlaceReportValidator {
	public void validate(WrongPlaceReportDTO wrongPlaceReportDto) throws InvalidWrongPlaceReportException {
		checkCondition(StringUtils.isEmpty(wrongPlaceReportDto.getDeviceId()), "DeviceId cannot be empty or null.");
		checkCondition(wrongPlaceReportDto.getPlaceVersion() <= 0, "PlaceVersion cannot be empty or null.");
		checkCondition(wrongPlaceReportDto.getPlaceId() <= 0, "PlaceId cannot be less or equal 0.");
	}
	
	private void checkCondition(boolean condition, String message) throws InvalidWrongPlaceReportException {
		if(condition) {
			throw new InvalidWrongPlaceReportException(message);
		}
	}
}
