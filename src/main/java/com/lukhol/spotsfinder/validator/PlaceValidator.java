package com.lukhol.spotsfinder.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.exception.InvalidPlaceException;

@Component
public class PlaceValidator {
	
	public void validate(HeavyPlaceDTO place) throws InvalidPlaceException {
		checkCondition(place.getType() < 0 || place.getType() > 2,"Type value should be 0, 1 or 2");
		
		checkCondition(place.getLocation() == null, "Location should not be null!");
		
		checkCondition(place.getImages() == null, "List of images should not be null!");
		
		checkCondition(StringUtils.isEmpty(place.getName()), "Name should not be empty!");
	
		checkCondition(StringUtils.isEmpty(place.getDescription()), "Description should not be empty!");
		
		checkCondition(place.getImages().size() > 5, "Images count should be at most 5!");
		
		checkCondition(place.getImages().size() < 1, "There should be at least 1 image!");		
	}
	
	private void checkCondition(boolean condition, String message) throws InvalidPlaceException {
		if(condition) {
			throw new InvalidPlaceException(message);
		}
	}
}
