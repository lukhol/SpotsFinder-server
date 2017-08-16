package com.polibuda.pbl.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.dto.SkateParkDTO;
import com.polibuda.pbl.model.SkatePark;
import com.polibuda.pbl.repository.SkateParkRepository;

@Service
public class SkateParkServiceImpl implements SkateParkService {

	@Autowired
	private SkateParkRepository skateParkRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<SkateParkDTO> getAll() {
		return skateParkRepository.findAll()
			.stream()
			.map(spot -> convertToDTO(spot))
			.collect(Collectors.toList());
	}

	@Override
	public SkateParkDTO save(SkateParkDTO skateParkDto) {
		SkatePark skatePark = skateParkRepository.save(convertToModel(skateParkDto));
		return convertToDTO(skatePark);
	}

	@Override
	public boolean exists(Long skateParkId) {		
		return skateParkRepository.exists(skateParkId);
	}

	@Override
	public SkateParkDTO getById(Long skateParkId) {
		SkatePark skatePark = skateParkRepository.findOne(skateParkId).get();
		return convertToDTO(skatePark);
	}

	@Override
	public void delete(Long skateParkId) {
		skateParkRepository.delete(skateParkId);		
	}
	
	private SkateParkDTO convertToDTO(SkatePark skatePark) {
		return modelMapper.map(skatePark, SkateParkDTO.class);
	}
	
	private SkatePark convertToModel(SkateParkDTO skateParkDto) {
		return modelMapper.map(skateParkDto, SkatePark.class);
	}

}
