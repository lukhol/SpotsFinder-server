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

import org.springframework.stereotype.Repository;

import com.polibuda.pbl.dto.AddressDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.model.Place;

@Repository
public class CustomPlaceRepositoryImpl implements CustomPlaceRepository {

	@PersistenceContext
    private EntityManager entityManager;
	private CriteriaBuilder builder;
	private CriteriaQuery<Place> criteria;
	private Root<Place> root;

	@Override
	public List<Place> search(PlaceSearchDTO searchCriteria) {
		builder = entityManager.getCriteriaBuilder();
		criteria = builder.createQuery(Place.class);
		root = criteria.from( Place.class );
		criteria.select( root );
		
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
		
		Optional.ofNullable(where("bowl", searchCriteria.getBowl())).ifPresent(conditions::add);
		Optional.ofNullable(where("bank", searchCriteria.getBank())).ifPresent(conditions::add);
		Optional.ofNullable(where("corners", searchCriteria.getCorners())).ifPresent(conditions::add);
		Optional.ofNullable(where("curb", searchCriteria.getCurb())).ifPresent(conditions::add);
		Optional.ofNullable(where("downhill", searchCriteria.getDownhill())).ifPresent(conditions::add);
		Optional.ofNullable(where("gap", searchCriteria.getGap())).ifPresent(conditions::add);
		Optional.ofNullable(where("handrail", searchCriteria.getHandrail())).ifPresent(conditions::add);
		Optional.ofNullable(where("ledge", searchCriteria.getLedge())).ifPresent(conditions::add);
		Optional.ofNullable(where("manualpad", searchCriteria.getManualpad())).ifPresent(conditions::add);
		Optional.ofNullable(where("openYourMind", searchCriteria.getOpenYourMind())).ifPresent(conditions::add);
		Optional.ofNullable(where("pyramid", searchCriteria.getPyramid())).ifPresent(conditions::add);
		Optional.ofNullable(where("rail", searchCriteria.getRail())).ifPresent(conditions::add);
		Optional.ofNullable(where("stairs", searchCriteria.getStairs())).ifPresent(conditions::add);
		Optional.ofNullable(where("wallride", searchCriteria.getWallride())).ifPresent(conditions::add);
		
		return conditions.toArray(new Predicate[conditions.size()]);
	}
	
	private Predicate where(String fieldName, Boolean value) {
		if(value != null) {
			return builder.equal( root.get( fieldName ), value.booleanValue() );
		}
		return null;
	}
	
	private Predicate addLocationSelections(AddressDTO address, int distance) {
		// TODO
		return null;
	}

	private Predicate addTypeSelections(int[] type) {
		// TODO
		return null;
	}
}
