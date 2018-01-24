package com.lukhol.spotsfinder.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.lukhol.spotsfinder.model.DeviceInfo;
import com.lukhol.spotsfinder.model.MobileAppError;
import com.lukhol.spotsfinder.repository.MobileAppErrorRepository;
import com.lukhol.spotsfinder.service.MobileAppErrorService;
import com.lukhol.spotsfinder.service.MobileAppErrorServiceImpl;

@RunWith(JUnit4.class)
public class MobileAppErrorServiceTests {
	
	private MobileAppErrorService mobileAppErrorService;
	
	@Mock
	private MobileAppErrorRepository mobileAppErrorRepository;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		mobileAppErrorService = new MobileAppErrorServiceImpl(mobileAppErrorRepository);
	}
	
	@Test(expected=NullPointerException.class)
	public void cannotTestWhenDependecyIsNull(){
		mobileAppErrorService = new MobileAppErrorServiceImpl(null);
	}
	
	@Test
	public void canSaveMobileAppError(){
		MobileAppError mobileAppError = MobileAppError.builder()
				.className("ClassName")
				.message("Message")
				.stackTraceString("Stack trace string.")
				.whereOccurred("Where occured.")
				.deviceInfo(DeviceInfo.builder().model("model").platform("platform").build())
				.build();
		
		mobileAppErrorService.save(mobileAppError);
		
		Mockito
			.verify(mobileAppErrorRepository, Mockito.times(1))
			.save(mobileAppError);
	}
}
