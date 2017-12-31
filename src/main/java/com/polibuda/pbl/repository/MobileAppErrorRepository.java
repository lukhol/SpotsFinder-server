package com.polibuda.pbl.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.polibuda.pbl.model.MobileAppError;

@Transactional
public interface MobileAppErrorRepository extends Repository<MobileAppError, Long> {
	void save(MobileAppError mobileAppError);
}
