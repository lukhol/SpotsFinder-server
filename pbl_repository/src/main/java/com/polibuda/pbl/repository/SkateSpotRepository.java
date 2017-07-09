package com.polibuda.pbl.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.polibuda.pbl.model.SkateSpot;

@Transactional
public interface SkateSpotRepository extends CrudRepository<SkateSpot, Long> {

}
