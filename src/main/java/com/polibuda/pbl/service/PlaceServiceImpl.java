package com.polibuda.pbl.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.dto.HeavyPlaceDTO;
import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.mapper.PlaceDTOMapper;
import com.polibuda.pbl.model.Image;
import com.polibuda.pbl.model.Place;
import com.polibuda.pbl.repository.PlaceRepository;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private PlaceDTOMapper placeMapper;
	
	@Override
	public List<LightPlaceDTO> getAll() {
		return placeRepository.findAll()
			.stream()
			.map(place -> placeMapper.mapToLightDTO(place))
			.collect(Collectors.toList());
	}

	@Override
	public HeavyPlaceDTO save(HeavyPlaceDTO placeDto) {
		Place placeToSave = placeMapper.mapHeavyToModel(placeDto);
		Image mainPhoto = placeToSave.getImages().get(0);
		
		// TODO: scale the mainPhoto
		
		placeToSave.getImages().add(0, mainPhoto);
		
		Place place = placeRepository.save(placeToSave);
		return placeMapper.mapToHeavyDTO(place);
	}

	@Override
	public boolean exists(Long placeId) {		
		return placeRepository.exists(placeId);
	}

	@Override
	public HeavyPlaceDTO getById(Long placeId) {
		Place place = placeRepository.findOne(placeId).get();
		return placeMapper.mapToHeavyDTO(place);
	}

	@Override
	public void delete(Long placeId) {
		placeRepository.delete(placeId);		
	}

	@Override
	public List<LightPlaceDTO> search(PlaceSearchDTO placeDto) {
		// TODO Auto-generated method stub
		return null;
	}
}
