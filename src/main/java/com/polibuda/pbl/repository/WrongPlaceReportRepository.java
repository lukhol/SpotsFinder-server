package com.polibuda.pbl.repository;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.model.WrongPlaceReport;

@Transactional
public interface WrongPlaceReportRepository extends Repository<WrongPlaceReport, Long>{
	WrongPlaceReport save(WrongPlaceReport wrongPlaceRepository);
	WrongPlaceReport findOneById(Long wrongPlaceReportId);
	WrongPlaceReport findOneByDeviceIdAndPlace(String deviceId, Place place);
}
