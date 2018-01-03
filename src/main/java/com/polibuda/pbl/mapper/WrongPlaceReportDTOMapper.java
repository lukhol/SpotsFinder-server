package com.polibuda.pbl.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polibuda.pbl.dto.WrongPlaceReportDTO;
import com.polibuda.pbl.model.WrongPlaceReport;

@Component
public class WrongPlaceReportDTOMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public WrongPlaceReport mapToWrongPlaceReport(WrongPlaceReportDTO wrongPlaceReportDto){
		return modelMapper.map(wrongPlaceReportDto, WrongPlaceReport.class);
	}
	
	public WrongPlaceReportDTO mapToWrongPlaceReportDTO(WrongPlaceReport wrongPlaceReport){
		return modelMapper.map(wrongPlaceReport, WrongPlaceReportDTO.class);
	}
}
