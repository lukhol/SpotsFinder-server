package com.polibuda.pbl.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.dto.WrongPlaceReportDTO;
import com.polibuda.pbl.exception.InvalidWrongPlaceReportException;
import com.polibuda.pbl.mapper.WrongPlaceReportDTOMapper;
import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.model.WrongPlaceReport;
import com.polibuda.pbl.repository.PlaceRepository;
import com.polibuda.pbl.repository.WrongPlaceReportRepository;

@Service
public class WrongPlaceReportServiceImpl implements WrongPlaceReportService {

	@Autowired
	WrongPlaceReportRepository wrongPlaceReportRepository;
	
	@Autowired
	PlaceRepository placeRepository;
	
	@Autowired
	WrongPlaceReportDTOMapper wrongPlaceReportDTOMapper;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public WrongPlaceReportDTO save(WrongPlaceReportDTO wrongPlaceReportDto, Locale locale) throws InvalidWrongPlaceReportException {
		long placeIdFromDTO = wrongPlaceReportDto.getPlaceId();
		long placeVersionFromDto = wrongPlaceReportDto.getPlaceVersion();
		String deviceIdFromDto = wrongPlaceReportDto.getDeviceId();
		String reasonCommentFromDto = wrongPlaceReportDto.getReasonComment();
		boolean isNotSkateboardPlaceFromDto = wrongPlaceReportDto.isNotSkateboardPlace();
		
		WrongPlaceReport wrongPlaceReport = wrongPlaceReportDTOMapper.mapToWrongPlaceReport(wrongPlaceReportDto);
		
		Place place = placeRepository
				.findOneById(placeIdFromDTO)
				.orElseThrow(() -> new InvalidWrongPlaceReportException(String.format(messageSource.getMessage("error.placeWithIdNotExist", null, locale), wrongPlaceReportDto.getPlaceId())));
		
		if(place.getVersion() > placeVersionFromDto)
			throw new InvalidWrongPlaceReportException(messageSource.getMessage("error.placeHasChanged", null, locale));
		
		if(place.getVersion() < placeVersionFromDto)
			throw new InvalidWrongPlaceReportException(messageSource.getMessage("error.reportNotFromApp", null, locale));
		
		wrongPlaceReport.setPlace(place);
		wrongPlaceReport.setUser(null);
		
		WrongPlaceReport existPlaceReportForThisDeviceAndPlace = wrongPlaceReportRepository.findOneByDeviceIdAndPlace(deviceIdFromDto, place);
		
		if(existPlaceReportForThisDeviceAndPlace != null){
			existPlaceReportForThisDeviceAndPlace.setReasonComment(reasonCommentFromDto);
			existPlaceReportForThisDeviceAndPlace.setNotSkateboardPlace(isNotSkateboardPlaceFromDto);
			wrongPlaceReport = existPlaceReportForThisDeviceAndPlace;
		}
		
		wrongPlaceReportRepository.save(wrongPlaceReport);
		
		return wrongPlaceReportDTOMapper.mapToWrongPlaceReportDTO(wrongPlaceReport);
	}

}
