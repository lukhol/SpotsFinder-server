package com.polibuda.pbl.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.polibuda.pbl.model.Place;

@Repository
public class CustomPlaceRepositoryImpl implements CustomPlaceRepository {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Place> findByFilter(List<SearchCriteria> params) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Place> query = builder.createQuery(Place.class);
        
        // TODO: implement search criteria
		
		return null;
	}

}
