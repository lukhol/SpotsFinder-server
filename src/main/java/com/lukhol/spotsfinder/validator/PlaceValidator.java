package com.lukhol.spotsfinder.validator;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.exception.InvalidPlaceException;
import com.lukhol.spotsfinder.imageconverter.ImageConverter;
import com.lukhol.spotsfinder.model.Image;

import lombok.NonNull;

@Component
public class PlaceValidator {
	
	private final ImageConverter imageConverter;
	
	@Autowired
	public PlaceValidator(@NonNull ImageConverter imageConverter) {
		this.imageConverter = imageConverter;
	}
	
	public void validate(HeavyPlaceDTO place, BindingResult bindingResult) throws InvalidPlaceException, IOException {
		checkCondition(place.getType() < 0 || place.getType() > 2, bindingResult, "type", "error.place.type");
		checkCondition(place.getLocation() == null, bindingResult, "location", "error.place.location");
		checkCondition(place.getImages() == null, bindingResult, "images", "error.place.images");
		checkCondition(StringUtils.isEmpty(place.getName()), bindingResult, "name", "error.place.name");
		checkCondition(StringUtils.isEmpty(place.getDescription()), bindingResult, "description", "error.place.description");
		checkCondition(place.getImages().size() > 5, bindingResult, "images", "error.place.images");
		checkCondition(place.getImages().size() < 1, bindingResult, "images", "error.place.images");	
		
		for(Image img : place.getImages()) {
			try {
				if(imageConverter.isValidImage(img.getImage()) == false) {
					bindingResult.rejectValue("images", "error.place.images");
					break;
				}
			} catch (Exception e) {
				if(e.getClass() == InvalidPlaceException.class)
					throw e;
				
				bindingResult.rejectValue("images", "error.place.images");
				break;
			}
		}
		
		if(bindingResult.hasErrors())
			throw new InvalidPlaceException(bindingResult);
	}
	
	private void checkCondition(boolean condition, BindingResult bindingResult, String code, String message) throws InvalidPlaceException {
		if(!condition)
			return;
		
		bindingResult.rejectValue(code, message);
	}
}
