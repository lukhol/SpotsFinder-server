package com.lukhol.spotsfinder.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lukhol.spotsfinder.model.WrongPlaceReport;

@Repository
public class WrongPlaceReportRepositoryImpl implements CustomWrongPlaceReportRepository{

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void persist(WrongPlaceReport wrongPlaceReport) {
		entityManager.persist(wrongPlaceReport);
	}

	@Override
	public WrongPlaceReport findOneByDeviceIdAndPlace(String deviceId, Long placeId) {
		WrongPlaceReport wpr = (WrongPlaceReport)entityManager
				.createQuery("select wpr from WrongPlaceReport wpr WHERE wpr.deviceId = :deviceId and wpr.place.id = :placeId")
				.setParameter("deviceId", deviceId)
				.setParameter("placeId", placeId)
				.getSingleResult();
		
		return wpr;
	}
}
