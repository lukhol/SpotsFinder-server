package com.polibuda.pbl.service;

import com.polibuda.pbl.dto.WrongPlaceReportDTO;
import com.polibuda.pbl.exception.InvalidWrongPlaceReportException;

public interface WrongPlaceReportService {
	WrongPlaceReportDTO save(WrongPlaceReportDTO wrongPlaceReportDto) throws InvalidWrongPlaceReportException ;
}
