package com.polibuda.pbl.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
        Root<Place> r = query.from(Place.class);        
        Predicate predicate = builder.conjunction();
 
        for (SearchCriteria param : params) {
            if (param.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(predicate, 
                  builder.greaterThanOrEqualTo(r.get(param.getKey()), 
                  param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate, 
                  builder.lessThanOrEqualTo(r.get(param.getKey()), 
                  param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase(":")) {
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, 
                      builder.like(r.get(param.getKey()), 
                      "%" + param.getValue() + "%"));
                } else {
                    predicate = builder.and(predicate, 
                      builder.equal(r.get(param.getKey()), param.getValue()));
                }
            }
        }
        query.where(predicate);
 
        List<Place> result = entityManager.createQuery(query).getResultList();
        return result;
	}

}
