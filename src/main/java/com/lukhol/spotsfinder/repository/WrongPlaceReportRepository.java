package com.lukhol.spotsfinder.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.model.WrongPlaceReport;

@Transactional
public interface WrongPlaceReportRepository extends Repository<WrongPlaceReport, Long>{
	WrongPlaceReport save(WrongPlaceReport wrongPlaceRepository);
	WrongPlaceReport findOneById(Long wrongPlaceReportId);
	WrongPlaceReport findOneByDeviceIdAndPlace(String deviceId, Place place);
}
