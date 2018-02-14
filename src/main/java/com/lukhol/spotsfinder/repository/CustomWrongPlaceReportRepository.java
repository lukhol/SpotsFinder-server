package com.lukhol.spotsfinder.repository;

import com.lukhol.spotsfinder.model.WrongPlaceReport;

public interface CustomWrongPlaceReportRepository {
	void persist(WrongPlaceReport wrongPlaceReport);
	WrongPlaceReport findOneByDeviceIdAndPlace(String deviceId, Long placeId);
}
