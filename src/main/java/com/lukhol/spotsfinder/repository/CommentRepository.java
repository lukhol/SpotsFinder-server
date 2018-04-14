package com.lukhol.spotsfinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lukhol.spotsfinder.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	@Query("select c from Comment c where c.place.id=:placeId and c.createdBy.id=:userId and c.content=:content")
	public Comment findByPlaceUserAndContent(@Param("placeId") long placeId, @Param("userId") long userId, @Param("content") String content);
	
	@Query("select c from Comment c where c.place.id=:placeId and c.deleted = false")
	public List<Comment> findAllActiveByPlaceId(@Param("placeId") long placeId);
}