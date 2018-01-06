package com.polibuda.pbl.repository;

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
import org.springframework.util.StringUtils;

import com.polibuda.pbl.dto.AddressDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.exception.GeocodingCityException;
import com.polibuda.pbl.geolocation.FetchCityComponent;
import com.polibuda.pbl.model.Place;

public class PlaceRepositoryImpl implements CustomPlaceRepository {

	@PersistenceContext
    private EntityManager entityManager;
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Place> criteria;
	private Root<Place> root;
	
	@Autowired
	private FetchCityComponent fetchCity;

	@Override
	public List<Place> search(PlaceSearchDTO searchCriteria) throws GeocodingCityException {
		builder = entityManager.getCriteriaBuilder();
		criteria = builder.createQuery(Place.class);
		root = criteria.from(Place.class);
		criteria.select(root);
		
		Predicate booleanPredicates = builder.and(addBooleanSelections(searchCriteria));
		
		Predicate locationPredicates = addLocationSelections(searchCriteria.getLocation(), searchCriteria.getDistance());
		Predicate typePredicates = addTypeSelections(searchCriteria.getType());
		
		criteria.where(builder.and(
				booleanPredicates,
				locationPredicates,
				typePredicates
				));
		
		List<Place> places = entityManager.createQuery( criteria ).getResultList();
		
		return places != null ? places : Collections.emptyList();
	}

	private Predicate[] addBooleanSelections(PlaceSearchDTO searchCriteria) {
		
		List<Predicate> conditions = new ArrayList<Predicate>();
		
		Optional.ofNullable(where("bowl", searchCriteria.isBowl())).ifPresent(conditions::add);
		Optional.ofNullable(where("bank", searchCriteria.isBank())).ifPresent(conditions::add);
		Optional.ofNullable(where("corners", searchCriteria.isCorners())).ifPresent(conditions::add);
		Optional.ofNullable(where("curb", searchCriteria.isCurb())).ifPresent(conditions::add);
		Optional.ofNullable(where("downhill", searchCriteria.isDownhill())).ifPresent(conditions::add);
		Optional.ofNullable(where("gap", searchCriteria.isGap())).ifPresent(conditions::add);
		Optional.ofNullable(where("handrail", searchCriteria.isHandrail())).ifPresent(conditions::add);
		Optional.ofNullable(where("ledge", searchCriteria.isLedge())).ifPresent(conditions::add);
		Optional.ofNullable(where("manualpad", searchCriteria.isManualpad())).ifPresent(conditions::add);
		Optional.ofNullable(where("openYourMind", searchCriteria.isOpenYourMind())).ifPresent(conditions::add);
		Optional.ofNullable(where("pyramid", searchCriteria.isPyramid())).ifPresent(conditions::add);
		Optional.ofNullable(where("rail", searchCriteria.isRail())).ifPresent(conditions::add);
		Optional.ofNullable(where("stairs", searchCriteria.isStairs())).ifPresent(conditions::add);
		Optional.ofNullable(where("wallride", searchCriteria.isWallride())).ifPresent(conditions::add);
		
		return conditions.toArray(new Predicate[conditions.size()]);
	}
	
	private Predicate where(String fieldName, boolean value) {
		if(value) {
			return builder.equal( root.get( fieldName ), value);
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
		
		Predicate latitudePredicate = builder.between( root.get("latitude"), coordinates[0] - latitudeDelta, coordinates[0] + latitudeDelta );
		Predicate longitudePredicate = builder.between( root.get("longitude"), coordinates[1] - longitudeDelta, coordinates[1] + longitudeDelta );
		
		return builder.and(latitudePredicate, longitudePredicate);
	}

	private Predicate addTypeSelections(int[] types) {
		List<Predicate> conditions = new ArrayList<Predicate>();
		for(int type : types) {
			conditions.add( builder.equal( root.get("type"), type) );
		}
		if(conditions.size() > 1) {
			return builder.or(conditions.toArray(new Predicate[conditions.size()]));
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
