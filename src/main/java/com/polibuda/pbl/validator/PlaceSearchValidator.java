package com.polibuda.pbl.validator;

import org.springframework.stereotype.Component;

import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.exception.InvalidPlaceSearchException;

@Component
public class PlaceSearchValidator {

	public void validate(PlaceSearchDTO search) throws InvalidPlaceSearchException {
		checkCondition(search.getType() == null, "Type should not be null");
		checkCondition(search.getType().length == 0, "Type should not be empty");
		for(int i = 0; i < search.getType().length; i++) {
			checkCondition(search.getType()[i] < 0 || search.getType()[i] > 2,"Type value should be 0, 1 or 2");
		}
		checkCondition(search.getLocation() == null, "Location should not be null");
		checkCondition(search.getLocation().getCity() == null && 
				( search.getLocation().getLatitude() == null || search.getLocation().getLongitude() == null), "Location should not be null");
		checkCondition(search.getLocation().getCity() != null && 
				( search.getLocation().getLatitude() != null || search.getLocation().getLongitude() != null), "City or coordinates should be specified (not both).");
	}
	
	private void checkCondition(boolean condition, String message) throws InvalidPlaceSearchException {
		if(condition) {
			throw new InvalidPlaceSearchException(message);
		}
	}
}
