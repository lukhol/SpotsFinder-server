package com.lukhol.spotsfinder.repository;

import com.lukhol.spotsfinder.model.PlaceRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<PlaceRate, Long> {

    @Query("select r from PlaceRate r where r.place.id=:placeId and r.user.id=:userId")
    Optional<PlaceRate> findOneByPlaceAndUser(@Param("placeId") long placeId, @Param("userId")long userId);

    @Query("select (sum(pr.rate * 1.0) / count(pr)) from PlaceRate pr where pr.place.id=:placeId")
    double getTotalRateForPlace(@Param("placeId") long placeId);

    @Query("select count(pr) from PlaceRate pr where pr.place.id=:placeId")
    int getRateCount(@Param("placeId") long placeId);
}