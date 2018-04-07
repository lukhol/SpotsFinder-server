package com.lukhol.spotsfinder.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lukhol.spotsfinder.dto.WrongPlaceReportDTO;
import com.lukhol.spotsfinder.exception.InvalidWrongPlaceReportException;
import com.lukhol.spotsfinder.mapper.WrongPlaceReportDTOMapper;
import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.model.WrongPlaceReport;
import com.lukhol.spotsfinder.repository.PlaceRepository;
import com.lukhol.spotsfinder.repository.UserRepository;
import com.lukhol.spotsfinder.repository.WrongPlaceReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WrongPlaceReportServiceImpl implements WrongPlaceReportService {

	private final WrongPlaceReportRepository wrongPlaceReportRepository;
	private final PlaceRepository placeRepository;
	private final WrongPlaceReportDTOMapper wrongPlaceReportDTOMapper;
	private final MessageSource messageSource;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public WrongPlaceReportDTO save(WrongPlaceReportDTO wrongPlaceReportDto, Locale locale) throws InvalidWrongPlaceReportException {
		long placeIdFromDTO = wrongPlaceReportDto.getPlaceId();
		long userIdFromDTo = wrongPlaceReportDto.getUserId();
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
		
		if(userIdFromDTo > 0)
			wrongPlaceReport.setUser(userRepository.getOne(userIdFromDTo));
		
		WrongPlaceReport existPlaceReportForThisDeviceAndPlace = wrongPlaceReportRepository.findOneByDeviceIdAndPlace(deviceIdFromDto, place);
		
		if(existPlaceReportForThisDeviceAndPlace != null){
			existPlaceReportForThisDeviceAndPlace.setReasonComment(reasonCommentFromDto);
			existPlaceReportForThisDeviceAndPlace.setNotSkateboardPlace(isNotSkateboardPlaceFromDto);
			wrongPlaceReport = existPlaceReportForThisDeviceAndPlace;
		}
		
		wrongPlaceReportRepository.persist(wrongPlaceReport);
		
		return wrongPlaceReportDTOMapper.mapToWrongPlaceReportDTO(wrongPlaceReport, userIdFromDTo);
	}

}
