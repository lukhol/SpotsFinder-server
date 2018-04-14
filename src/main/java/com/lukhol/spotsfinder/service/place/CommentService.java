package com.lukhol.spotsfinder.service.place;

import java.util.List;

import com.lukhol.spotsfinder.dto.CommentDTO;
import com.lukhol.spotsfinder.model.Comment;

public interface CommentService {
	Comment save(CommentDTO commentDTO, String userId);
	List<CommentDTO> getAllByPlaceId(long placeId);
}
