package com.polibuda.pbl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polibuda.pbl.model.MobileAppError;
import com.polibuda.pbl.repository.MobileAppErrorRepository;

@Service
public class MobileAppErrorServiceImpl implements MobileAppErrorService {

	@Autowired
	MobileAppErrorRepository mobileAppErrorRepository;

	@Override
	public void save(MobileAppError mobileAppError) {
		mobileAppErrorRepository.save(mobileAppError);
	}
}
