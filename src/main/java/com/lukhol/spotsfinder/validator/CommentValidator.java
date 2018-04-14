package com.lukhol.spotsfinder.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.lukhol.spotsfinder.dto.CommentDTO;
import com.lukhol.spotsfinder.exception.ServiceValidationException;
import com.lukhol.spotsfinder.repository.PlaceRepository;
import com.lukhol.spotsfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentValidator {
	
	private final UserRepository userRepository;
	private final PlaceRepository placeRepository;
	
	public void validate(CommentDTO commentDTO, long userId, BindingResult bindingResult) throws ServiceValidationException {
		checkCondition(userId != commentDTO.getUserId(), bindingResult, "userId", "error.comment.user");
		checkCondition(userRepository.getOne(userId) == null, bindingResult, "userId", "error.comment.user");
		checkCondition(commentDTO.getContent() == null, bindingResult, "content", "error.comment.content");
		checkCondition(!placeRepository.findOneById(commentDTO.getPlaceId()).isPresent(), bindingResult, "placeId", "error.comment.place");
		checkCondition(commentDTO.getPlaceId() <= 0, bindingResult, "placeId", "error.comment.place");
		checkCondition(commentDTO.getId() != null && commentDTO.getId() <= 0, bindingResult, "id", "error.comment.id");
		
		if(bindingResult.hasErrors())
			throw new ServiceValidationException(bindingResult);
	}
	
	private void checkCondition(boolean condition, BindingResult bindingResult, String code, String message) {
		if(!condition)
			return;
		
		bindingResult.rejectValue(code, message);
	}
}