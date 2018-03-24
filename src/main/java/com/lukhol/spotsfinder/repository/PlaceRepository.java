package com.lukhol.spotsfinder.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.model.User;

@Transactional
public interface PlaceRepository extends Repository<Place, Long>, CustomPlaceRepository {

	void delete(Long id);
	boolean exists(Long id);
	List<Place> findAll();
	Optional<Place> findOneById(Long id);
	List<Place> findByOwner(User owner);
	Place save(Place place);
	
	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Place p WHERE p.id=:placeId AND p.owner.id=:userId")
	boolean existByIdAndUserId(@Param("placeId") Long placeId, @Param("userId") Long userId);
	
	@Modifying
	@Query("DELETE FROM Image i WHERE i.place IS NULL")
	void clearImagesWithoutPlace();
	
	@Modifying
	@Query("DELETE FROM Image i WHERE i.place = :placeId")
	void removeAllImagesForPlace(@Param("placeId") long placeId);
}
