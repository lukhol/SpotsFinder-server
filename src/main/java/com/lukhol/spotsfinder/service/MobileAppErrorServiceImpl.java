package com.lukhol.spotsfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukhol.spotsfinder.model.MobileAppError;
import com.lukhol.spotsfinder.repository.MobileAppErrorRepository;

import lombok.NonNull;

@Service
public class MobileAppErrorServiceImpl implements MobileAppErrorService {

	private final MobileAppErrorRepository mobileAppErrorRepository;

	@Autowired
	public MobileAppErrorServiceImpl(@NonNull MobileAppErrorRepository mobileAppErrorRepository) {
		super();
		this.mobileAppErrorRepository = mobileAppErrorRepository;
	}

	@Override
	public void save(MobileAppError mobileAppError) {
		mobileAppErrorRepository.save(mobileAppError);
	}
}
