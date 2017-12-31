package com.polibuda.pbl.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.polibuda.pbl.model.MobileAppError;

public class MobileAppErrorRepositoryImpl implements MobileAppErrorRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(MobileAppError mobileAppError) {
		entityManager.persist(mobileAppError);
	}

}
