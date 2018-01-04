package com.polibuda.pbl.service;

import java.util.Locale;

import com.polibuda.pbl.dto.WrongPlaceReportDTO;
import com.polibuda.pbl.exception.InvalidWrongPlaceReportException;

public interface WrongPlaceReportService {
	WrongPlaceReportDTO save(WrongPlaceReportDTO wrongPlaceReportDto, Locale locale) throws InvalidWrongPlaceReportException ;
}
