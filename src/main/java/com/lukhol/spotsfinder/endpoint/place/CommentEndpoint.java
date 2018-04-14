package com.lukhol.spotsfinder.endpoint.place;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lukhol.spotsfinder.dto.CommentDTO;
import com.lukhol.spotsfinder.model.Comment;
import com.lukhol.spotsfinder.service.place.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/places/comment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentEndpoint {
	
	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO, Authentication auth) {
		String userId = auth.getName();
		Comment comment = commentService.save(commentDTO, userId);
		commentDTO.setId(comment.getId());
		return new ResponseEntity<CommentDTO>(commentDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CommentDTO>> getComments(@RequestParam("placeId") long placeId) {
		return new ResponseEntity<List<CommentDTO>>(commentService.getAllByPlaceId(placeId), HttpStatus.OK);
	}
}