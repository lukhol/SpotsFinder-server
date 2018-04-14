package com.lukhol.spotsfinder.service.place;

import java.util.Locale;

import com.lukhol.spotsfinder.dto.WrongPlaceReportDTO;
import com.lukhol.spotsfinder.exception.InvalidWrongPlaceReportException;

public interface WrongPlaceReportService {
	WrongPlaceReportDTO save(WrongPlaceReportDTO wrongPlaceReportDto, Locale locale) throws InvalidWrongPlaceReportException ;
}
