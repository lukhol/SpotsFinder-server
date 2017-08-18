package com.polibuda.pbl.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CustomPlaceRepositoryImpl implements CustomPlaceRepository {

	@PersistenceContext
    private EntityManager entityManager;

}
