package com.polibuda.pbl.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.polibuda.pbl.dto.PlaceDTO;
import com.polibuda.pbl.exception.InvalidPlaceException;

@Component
public class PlaceValidator {
	
	public void validate(PlaceDTO place) throws InvalidPlaceException {
		if(place.getImages() == null) {
			throw new InvalidPlaceException("List of images should not be null!");
		}
		if(StringUtils.isEmpty(place.getName())) {
			throw new InvalidPlaceException("Name should not be empty!");
		}
		if(StringUtils.isEmpty(place.getDescription())) {
			throw new InvalidPlaceException("Description should not be empty!");
		}
		if(place.getImages().size() > 5) {
			throw new InvalidPlaceException("Images count should be at most 5!");
		}
		if(place.getImages().size() < 1) {
			throw new InvalidPlaceException("There should be at least 1 image!");
		}
	}
}
