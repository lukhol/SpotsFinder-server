package com.polibuda.pbl.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.polibuda.pbl.model.SkatePark;

@Transactional
public interface SkateParkRepository extends CrudRepository<SkatePark, Long> {

}
