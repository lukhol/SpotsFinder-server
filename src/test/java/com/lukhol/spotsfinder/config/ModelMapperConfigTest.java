package com.lukhol.spotsfinder.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.lukhol.spotsfinder.config.ModelMapperConfig;
import com.lukhol.spotsfinder.dto.CoordinatesDTO;
import com.lukhol.spotsfinder.dto.HeavyPlaceDTO;
import com.lukhol.spotsfinder.dto.LightPlaceDTO;
import com.lukhol.spotsfinder.dto.WrongPlaceReportDTO;
import com.lukhol.spotsfinder.model.Image;
import com.lukhol.spotsfinder.model.Place;
import com.lukhol.spotsfinder.model.User;
import com.lukhol.spotsfinder.model.WrongPlaceReport;

/**
 * @author Lukasz
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ModelMapperConfig.class, loader=AnnotationConfigContextLoader.class)
public class ModelMapperConfigTest {

	@Autowired
	private ModelMapper mapper;
	
	@Test
	public void testPlaceToHeavyDtoConversion() {
		Place place = Place.builder()
				.corners(true)
				.name("name")
				.description("opis")
				.type(1)
				.latitude(50.1234)
				.longitude(45.9876)
				.build();
		
		HeavyPlaceDTO placeDTO = mapper.map(place, HeavyPlaceDTO.class);
		
		assert placeDTO.getName().equals(place.getName());
		assert placeDTO.getDescription().equals(place.getDescription());
		assert placeDTO.isCorners() == place.isCorners();
		assert placeDTO.getLocation().getLatitude() == place.getLatitude();
		assert placeDTO.getLocation().getLongitude() == place.getLongitude();
		assert placeDTO.getType() == place.getType();
	}
	
	@Test
	public void testPlaceDtoToModelConversion() {
		HeavyPlaceDTO placeDTO = HeavyPlaceDTO.builder()
				.corners(true)
				.name("nazwa")
				.description("description")
				.type(0)
				.location(new CoordinatesDTO(50.1234, 45.9462))
				.build();
		
		Place place = mapper.map(placeDTO, Place.class);
		
		assert placeDTO.getName().equals(place.getName());
		assert placeDTO.getDescription().equals(place.getDescription());
		assert placeDTO.isCorners() == place.isCorners();
		assert placeDTO.getLocation().getLatitude() == place.getLatitude();
		assert placeDTO.getLocation().getLongitude() == place.getLongitude();
		assert placeDTO.getType() == place.getType();
	}
	
	@Test
	public void testMultiConversion() {
		ArrayList<HeavyPlaceDTO> places = new ArrayList<HeavyPlaceDTO>();
		for(int i = 0; i < 10; i++) {
			Place place = Place.builder()
					.corners(true)
					.name("name " + i)
					.description("opis" + i)
					.type(1)
					.latitude(1.1234 + i)
					.longitude(2.9876 * i)
					.build();
				
			HeavyPlaceDTO placeDTO = mapper.map(place, HeavyPlaceDTO.class);
			places.add(placeDTO);
			
			assert placeDTO.getName().equals(place.getName());
			assert placeDTO.getDescription().equals(place.getDescription());
			assert placeDTO.isCorners() == place.isCorners();
			assert placeDTO.getLocation().getLatitude() == place.getLatitude();
			assert placeDTO.getLocation().getLongitude() == place.getLongitude();
			assert placeDTO.getType() == place.getType();
		}
		for(HeavyPlaceDTO p : places) 
		System.out.println(p);
	}
	
	@Test
	public void testPlaceToLightDtoConversion() {
		Place place = Place.builder()
				.corners(true)
				.name("name")
				.description("opis")
				.type(1)
				.latitude(50.1234)
				.longitude(45.9876)
				.images(Arrays.asList(new Image(123l, "photo1"), new Image(6678l, "image2")))
				.mainPhoto("Main photo base64string")
				.build();
		
		LightPlaceDTO placeDTO = mapper.map(place, LightPlaceDTO.class);
		
		assert placeDTO.getName().equals(place.getName());
		assert placeDTO.getDescription().equals(place.getDescription());
		assert placeDTO.getLocation().getLatitude() == place.getLatitude();
		assert placeDTO.getLocation().getLongitude() == place.getLongitude();
		assert placeDTO.getType() == place.getType();
		assert placeDTO.getMainPhoto().equals(place.getMainPhoto());
	}
	
	@Test
	public void canConvertWrongPlaceReportDtoToCore(){
		WrongPlaceReportDTO wrongPlaceReportDTO = 
				WrongPlaceReportDTO
					.builder()
					.placeId(1l)
					.placeVersion(12345)
					.userId(0)
					.deviceId("12345")
					.reasonComment("Not a skateboard place!")
					.isNotSkateboardPlace(false)
					.build();
		
		WrongPlaceReport wrongPlaceReport = mapper.map(wrongPlaceReportDTO, WrongPlaceReport.class);
		
		assert wrongPlaceReport.getPlace() == null;
		assert wrongPlaceReport.getUser() == null;
		assert wrongPlaceReport.getId() == null;
		assert wrongPlaceReport.getDeviceId().equals(wrongPlaceReportDTO.getDeviceId());
		assert wrongPlaceReport.getReasonComment().equals(wrongPlaceReportDTO.getReasonComment());
		assert wrongPlaceReport.isNotSkateboardPlace() == wrongPlaceReportDTO.isNotSkateboardPlace();
		assert wrongPlaceReport.getReportedPlaceVersion() == wrongPlaceReportDTO.getPlaceVersion();	
	}
	
	@Test 
	public void canConvertWrongPlaceReportCoreToDto_nullPlaceAndUserVersion(){
		WrongPlaceReport wrongPlaceReport = 
				WrongPlaceReport
					.builder()
					.place(null)
					.user(null)
					.reportedPlaceVersion(123456l)
					.deviceId("deviceId")
					.reasonComment("Reason commnet.")
					.isNotSkateboardPlace(true)
					.build();
		
		WrongPlaceReportDTO wrongPlaceReportDTO = mapper.map(wrongPlaceReport, WrongPlaceReportDTO.class);
		
		assert wrongPlaceReportDTO.getPlaceId() == 0;
		assert wrongPlaceReportDTO.getUserId() == 0;
		assertSimpleFieldOnWrongPlace(wrongPlaceReportDTO, wrongPlaceReport);
	}
	
	@Test
	public void canConvertWrongPlaceReportCoreToDto_notNullPlaceAndUserVersion(){
		WrongPlaceReport wrongPlaceReport = 
				WrongPlaceReport
					.builder()
					.place(Place.builder().id(1l).build())
					.user(User.builder().id(12l).build())
					.reportedPlaceVersion(123456l)
					.deviceId("DeviceId")
					.reasonComment("Reason comment.")
					.isNotSkateboardPlace(false)
					.build();
		
		WrongPlaceReportDTO wrongPlaceReportDTO = mapper.map(wrongPlaceReport, WrongPlaceReportDTO.class);
		
		assert wrongPlaceReportDTO.getPlaceId() == wrongPlaceReport.getPlace().getId();
		assertSimpleFieldOnWrongPlace(wrongPlaceReportDTO, wrongPlaceReport);
	}
	
	private void assertSimpleFieldOnWrongPlace(WrongPlaceReportDTO wrongPlaceReportDTO, WrongPlaceReport wrongPlaceReport){
		assert wrongPlaceReportDTO.getReasonComment().equals(wrongPlaceReport.getReasonComment());
		assert wrongPlaceReportDTO.getDeviceId().equals(wrongPlaceReport.getDeviceId());
		assert wrongPlaceReportDTO.isNotSkateboardPlace() == wrongPlaceReport.isNotSkateboardPlace();
		assert wrongPlaceReportDTO.getPlaceVersion() == wrongPlaceReport.getReportedPlaceVersion();
	}
}
