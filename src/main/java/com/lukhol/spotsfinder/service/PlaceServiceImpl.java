package com.lukhol.spotsfinder.service;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.dto.PlaceSearchDTO;
import com.lukhol.spotsfinder.exception.GeocodingCityException;
import com.lukhol.spotsfinder.exception.NotFoundUserException;
import com.lukhol.spotsfinder.imageconverter.ImageConverter;
import com.lukhol.spotsfinder.mapper.PlaceDTOMapper;
import com.lukhol.spotsfinder.model.Image;
import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.model.PlaceJson;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.repository.PlaceJsonRepository;
import com.lukhol.spotsfinder.repository.PlaceRepository;
import com.lukhol.spotsfinder.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlaceServiceImpl implements PlaceService {

	private final PlaceRepository placeRepository;
	private final UserRepository userRepository;
	private final PlaceDTOMapper placeMapper;
	private final ImageConverter imageConverter;
	private final PlaceJsonRepository placeJsonRepository;

	@Override
	public List<LightPlaceDTO> getAll() {
		return placeRepository
				.findAll()
				.stream()
				.map(place -> placeMapper.mapToLightDTO(place))
				.collect(Collectors.toList());
	}

	@Override
	public HeavyPlaceDTO save(HeavyPlaceDTO placeDto) throws IOException, NotFoundUserException {
		Place placeToSave = placeMapper.mapHeavyToModel(placeDto);
		
		User userOwner = userRepository
				.findOneById(placeDto.getUserId())
				.orElseThrow(() -> new NotFoundUserException(placeDto.getUserId()));
		
		placeToSave.setOwner(userOwner);
		
		if(placeToSave.getMainPhoto() == null) {
			Image firstPhoto = placeToSave.getImages().get(0);
			String miniature = imageConverter.createMiniature(firstPhoto);
			
			placeToSave.setMainPhoto(miniature);
		}
		
		for(Image img : placeToSave.getImages()) {
			img.setImage(imageConverter.scaleToMaxOneMbFileSize(img.getImage()));
			img.setPlace(placeToSave);
		}
		
		Place place = placeRepository.save(placeToSave);
		return placeMapper.mapToHeavyDTO(place);
	}

	@Override
	public HeavyPlaceDTO update(HeavyPlaceDTO placeDTO) throws IOException, NotFoundUserException {
		Place placeToEdit = placeMapper.mapHeavyToModel(placeDTO);
		boolean userExists = userRepository.exists(placeDTO.getUserId());
		
		if(!userExists)
			throw new NotFoundUserException("Not foud user with id: " + placeDTO.getUserId());
		
		placeRepository.removeAllImagesForPlace(placeToEdit.getId());
		
		Image firstPhoto = placeToEdit.getImages().get(0);
		String miniature = imageConverter.createMiniature(firstPhoto);
		placeToEdit.setMainPhoto(miniature);
		
		for(Image img : placeToEdit.getImages())
			img.setPlace(placeToEdit);
		
		//Better using getReference:
		placeToEdit.setOwner(userRepository.findOne(placeDTO.getUserId()));
		
		Place place = placeRepository.save(placeToEdit);
		clearImagesWithoutPlace();
		
		return placeMapper.mapToHeavyDTO(place);
	}
	
	@Override
	public boolean exists(Long placeId) {		
		return placeRepository.exists(placeId);
	}

	@Override
	public boolean existAndBelongToUser(Long placeId, Long userId) {
		return placeRepository.existByIdAndUserId(placeId, userId);
	}
	
	@Override
	public HeavyPlaceDTO getById(Long placeId) {
		Place place = placeRepository.findOneById(placeId).get();
		return placeMapper.mapToHeavyDTO(place);
	}

	@Override
	public void delete(Long placeId) {
		placeRepository.delete(placeId);		
	}

	@Override
	public List<LightPlaceDTO> search(PlaceSearchDTO placeDto) throws GeocodingCityException {
		return placeRepository
				.search(placeDto)
				.stream()
				.map(place -> placeMapper.mapToLightDTO(place))
				.collect(Collectors.toList());
	}

	@Override
	public List<LightPlaceDTO> searchByUserId(long userId) throws NotFoundUserException {
		User user = userRepository
				.findOneById(userId)
				.orElseThrow(() -> new NotFoundUserException(userId));
				
		
		return placeRepository
				.findByOwner(user)
				.stream()
				.map(place -> placeMapper.mapToLightDTO(place))
				.collect(Collectors.toList());
	}

	@Override
	public List<LightPlaceDTO> getRecentlyAdded(int start, int count) {
		if(start < 0)
			throw new IllegalArgumentException("Start index cannot be less than 0");
		
		if(count == 0)
			throw new IllegalAccessError("Count cannot be less or equal 0");
		
		return placeRepository
				.getRecentlyAdded(start,  count)
				.stream()
				.map(place -> placeMapper.mapToLightDTO(place))
				.collect(Collectors.toList());
	}
	
	private void saveAsJson(HeavyPlaceDTO placeDto) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String placeJsonString = objectMapper.writeValueAsString(placeDto);
			
			PlaceJson placeJson = new PlaceJson();
			placeJson.setPlaceJson(placeJsonString);
			
			placeJsonRepository.save(placeJson);
		} catch (Exception e) {
			log.debug("Error during saving place json.");
		}
	}

	@Override
	public void clearImagesWithoutPlace() {
		placeRepository.clearImagesWithoutPlace();
	}
}