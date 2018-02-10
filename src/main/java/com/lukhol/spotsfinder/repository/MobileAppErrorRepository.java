package com.lukhol.spotsfinder.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.lukhol.spotsfinder.model.MobileAppError;

@Transactional
public interface MobileAppErrorRepository extends Repository<MobileAppError, Long> {
	MobileAppError save(MobileAppError mobileAppError);
}
