package com.polibuda.pbl.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public WrongPlaceReportDTO save(WrongPlaceReportDTO wrongPlaceReportDto) throws InvalidWrongPlaceReportException {
		long placeIdFromDTO = wrongPlaceReportDto.getPlaceId();
		long placeVersionFromDto = wrongPlaceReportDto.getPlaceVersion();
		String deviceIdFromDto = wrongPlaceReportDto.getDeviceId();
		String reasonCommentFromDto = wrongPlaceReportDto.getReasonComment();
		boolean isNotSkateboardPlaceFromDto = wrongPlaceReportDto.isNotSkateboardPlace();
		
		WrongPlaceReport wrongPlaceReport = wrongPlaceReportDTOMapper.mapToWrongPlaceReport(wrongPlaceReportDto);
		
		Place place = placeRepository.findOneById(placeIdFromDTO);
		
		if(place == null)
			throw new InvalidWrongPlaceReportException(String.format("Place with id %d does not exist.", wrongPlaceReportDto.getPlaceId()));
		
		if(place.getVersion() > placeVersionFromDto)
			throw new InvalidWrongPlaceReportException("Place has aleready changed. Please reload place and check if you realy want report it.");
		
		if(place.getVersion() < placeVersionFromDto)
			throw new InvalidWrongPlaceReportException("Waaait. Are you trying to report place not from our app? ");
		
		wrongPlaceReport.setPlace(place);
		wrongPlaceReport.setUser(null);
		
		WrongPlaceReport existPlaceReportForThisDeviceAndPlace = wrongPlaceReportRepository.findOneByDeviceIdAndPlace(deviceIdFromDto, place);
		
		if(existPlaceReportForThisDeviceAndPlace != null){
			existPlaceReportForThisDeviceAndPlace.setReasonComment(reasonCommentFromDto);
			existPlaceReportForThisDeviceAndPlace.setNotSkateboardPlace(isNotSkateboardPlaceFromDto);
			wrongPlaceReport = existPlaceReportForThisDeviceAndPlace;
		}
		
		wrongPlaceReport = wrongPlaceReportRepository.save(wrongPlaceReport);
		
		return wrongPlaceReportDTOMapper.mapToWrongPlaceReportDTO(wrongPlaceReport);
	}

}
