package com.lukhol.spotsfinder.service.place;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;

import com.lukhol.spotsfinder.dto.CommentDTO;
import com.lukhol.spotsfinder.model.Comment;
import com.lukhol.spotsfinder.repository.CommentRepository;
import com.lukhol.spotsfinder.repository.PlaceRepository;
import com.lukhol.spotsfinder.validator.CommentValidator;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImpl implements CommentService {

	private final CommentValidator commentValidator;
	private final PlaceRepository placeRepository;
	private final CommentRepository commentRepository;
	
	@Override
	public Comment save(CommentDTO commentDTO, String userIdString) {
		long userId = Long.parseLong(userIdString);
		commentValidator.validate(commentDTO, userId, new BeanPropertyBindingResult(commentDTO, "comment"));
		
		Comment comment = commentRepository.findByPlaceUserAndContent(commentDTO.getPlaceId(), commentDTO.getUserId(), commentDTO.getContent());
		
		if(comment != null)
			return comment;
		
		if(commentDTO.getId() != null) { 
			comment = commentRepository.findOne(commentDTO.getId());
			comment.setContent(commentDTO.getContent());
		} else {
			comment = convertDtoToComment(commentDTO);
		}
		
		return commentRepository.save(comment);
	}
	
	@Override
	public List<CommentDTO> getAllByPlaceId(long placeId) {
		return commentRepository
				.findAllActiveByPlaceId(placeId)
				.stream()
				.map(comment -> covertCommentToDTO(comment))
				.collect(Collectors.toList());
	}
	
	private Comment convertDtoToComment(CommentDTO commentDTO) {
		return Comment.builder()
				.content(commentDTO.getContent())
				.deleted(false)
				.place(placeRepository.getReference(commentDTO.getPlaceId()))
				.build();
	}
	
	private CommentDTO covertCommentToDTO(Comment comment) {
		return CommentDTO.builder()
				.content(comment.getContent())
				.userId(comment.getCreatedBy().getId())
				.placeId(comment.getPlace().getId())
				.id(comment.getId())
				.build();
	}
}