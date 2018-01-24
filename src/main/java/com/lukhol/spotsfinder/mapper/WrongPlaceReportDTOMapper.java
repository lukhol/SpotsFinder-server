package com.lukhol.spotsfinder.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lukhol.spotsfinder.dto.WrongPlaceReportDTO;
import com.lukhol.spotsfinder.model.WrongPlaceReport;

import lombok.NonNull;

@Component
public class WrongPlaceReportDTOMapper {

	private final ModelMapper modelMapper;
	
	@Autowired
	public WrongPlaceReportDTOMapper(@NonNull ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	public WrongPlaceReport mapToWrongPlaceReport(WrongPlaceReportDTO wrongPlaceReportDto){
		return modelMapper.map(wrongPlaceReportDto, WrongPlaceReport.class);
	}
	
	public WrongPlaceReportDTO mapToWrongPlaceReportDTO(WrongPlaceReport wrongPlaceReport){
		return modelMapper.map(wrongPlaceReport, WrongPlaceReportDTO.class);
	}
}
