package com.lukhol.spotsfinder.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.lukhol.spotsfinder.dto.AddressDTO;
import com.lukhol.spotsfinder.dto.PlaceSearchDTO;
import com.lukhol.spotsfinder.exception.GeocodingCityException;
import com.lukhol.spotsfinder.geolocation.FetchCityComponent;
import com.lukhol.spotsfinder.model.Place;

import lombok.NonNull;

@Repository
public class PlaceRepositoryImpl implements CustomPlaceRepository {

	@PersistenceContext
    private EntityManager entityManager;
	
	private CriteriaBuilder criteriaBuilder;
	private CriteriaQuery<Place> criteriaQuery;
	private Root<Place> root;
	
	private final FetchCityComponent fetchCity;

	@Autowired
	public PlaceRepositoryImpl(@NonNull FetchCityComponent fetchCity) {
		super();
		this.fetchCity = fetchCity;
	}

	@Override
	public Place getReference(Long id) {
		return entityManager.getReference(Place.class, id);
	}
	
	@Override
	public List<Place> search(PlaceSearchDTO searchCriteria) throws GeocodingCityException {
		criteriaBuilder = entityManager.getCriteriaBuilder();
		criteriaQuery = criteriaBuilder.createQuery(Place.class);
		root = criteriaQuery.from(Place.class);
		
		Predicate booleanPredicates = criteriaBuilder.and(addBooleanSelections(searchCriteria));
		Predicate locationPredicates = addLocationSelections(searchCriteria.getLocation(), searchCriteria.getDistance());
		Predicate typePredicates = addTypeSelections(searchCriteria.getType());
		
		criteriaQuery
			.select(root)
			.where(booleanPredicates, locationPredicates, typePredicates);
		
		List<Place> places = entityManager
				.createQuery(criteriaQuery)
				.getResultList();
		
		return places != null ? places : Collections.emptyList();
	}
	
	private Predicate[] addBooleanSelections(PlaceSearchDTO searchCriteria) {
		
		List<Predicate> conditions = new ArrayList<Predicate>();
		
		Optional.ofNullable(whereBooleanField("bowl", searchCriteria.isBowl())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("bank", searchCriteria.isBank())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("corners", searchCriteria.isCorners())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("curb", searchCriteria.isCurb())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("downhill", searchCriteria.isDownhill())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("gap", searchCriteria.isGap())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("handrail", searchCriteria.isHandrail())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("ledge", searchCriteria.isLedge())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("manualpad", searchCriteria.isManualpad())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("openYourMind", searchCriteria.isOpenYourMind())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("pyramid", searchCriteria.isPyramid())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("rail", searchCriteria.isRail())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("stairs", searchCriteria.isStairs())).ifPresent(conditions::add);
		Optional.ofNullable(whereBooleanField("wallride", searchCriteria.isWallride())).ifPresent(conditions::add);
		
		return conditions.toArray(new Predicate[conditions.size()]);
	}
	
	private Predicate whereBooleanField(String fieldName, boolean value) {
		if(value) {
			return criteriaBuilder.equal( root.get( fieldName ), value);
		}
		return null;
	}
	
	private Predicate addLocationSelections(AddressDTO address, int distance) throws GeocodingCityException {
		
		double[] coordinates = null;
		
		if(!StringUtils.isEmpty( address.getCity() )) {
			coordinates = fetchCity.fetchCityCoordinates(address.getCity());
		} else {
			coordinates = new double[] {
				address.getLatitude().doubleValue(),
				address.getLongitude().doubleValue()
			};
		}
		double latitudeDelta = getLatitudeDelta(distance);
		double longitudeDelta = getLongitudeDelta(distance);
		
		Predicate latitudePredicate = criteriaBuilder.between( root.get("latitude"), coordinates[0] - latitudeDelta, coordinates[0] + latitudeDelta );
		Predicate longitudePredicate = criteriaBuilder.between( root.get("longitude"), coordinates[1] - longitudeDelta, coordinates[1] + longitudeDelta );
		
		return criteriaBuilder.and(latitudePredicate, longitudePredicate);
	}

	private Predicate addTypeSelections(int[] types) {
		List<Predicate> conditions = new ArrayList<Predicate>();
		for(int type : types) {
			conditions.add( criteriaBuilder.equal( root.get("type"), type) );
		}
		if(conditions.size() > 1) {
			return criteriaBuilder.or(conditions.toArray(new Predicate[conditions.size()]));
		}
		return conditions.get(0);
	}
	
	/**
	 * 
	 * @param distance in meters
	 * @return latitude delta
	 */
	private double getLatitudeDelta(double distance) {
		return (distance * 180)/20000;
	}
	
	/**
	 * 
	 * @param distance in meters
	 * @return longitude delta
	 */
	private double getLongitudeDelta(double distance) {
		return (distance * 360)/20000;
	}
}
