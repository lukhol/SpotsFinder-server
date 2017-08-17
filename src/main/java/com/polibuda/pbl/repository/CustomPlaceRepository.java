package com.polibuda.pbl.repository;

import java.util.List;

import com.polibuda.pbl.model.Place;

public interface CustomPlaceRepository {
	List<Place> findByFilter(List<SearchCriteria> params);
}
