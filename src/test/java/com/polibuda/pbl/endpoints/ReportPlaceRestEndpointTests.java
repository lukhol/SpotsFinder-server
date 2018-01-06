package com.polibuda.pbl.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.polibuda.pbl.dto.WrongPlaceReportDTO;
import com.polibuda.pbl.endpoint.ReportPlaceRestEndpoint;
import com.polibuda.pbl.interceptors.SecureEndpointsInterceptor;
import com.polibuda.pbl.service.WrongPlaceReportService;
import com.polibuda.pbl.validator.WrongPlaceReportValidator;

@RunWith(SpringRunner.class)
@WebMvcTest({ReportPlaceRestEndpoint.class, SecureEndpointsInterceptor.class , WrongPlaceReportValidator.class })
public class ReportPlaceRestEndpointTests {

	@Autowired
	private ReportPlaceRestEndpoint reportPlaceRestEndpoint;
	
	@MockBean
	private WrongPlaceReportService wrongPlaceReportService;
	
	@MockBean
	private WrongPlaceReportValidator wrongPlaceReportValidator;
	
	@Autowired
	private MockMvc mockMvc;
	
	private String exampleWrongPlaceReportJson = "{\"placeId\":1,\"placeVersion\":12345,\"userId\":0,\"deviceId\":\"deviceID\",\"reasonComment\":\"Notskateboardplace!\",\"isNotSkateboardPlace\":false}";

	private WrongPlaceReportDTO wrongPlaceReportDTO;
	
	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException{		
		wrongPlaceReportDTO = WrongPlaceReportDTO
				.builder()
				.placeId(Long.valueOf(1))
				.placeVersion(12345l)
				.userId(0l)
				.deviceId("deviceID")
				.reasonComment("Notskateboardplace!")
				.isNotSkateboardPlace(false)
				.build();
	}
	
	@Test
	public void canCreateReportPlaceRestEndpoint(){
		assertThat(reportPlaceRestEndpoint).isNotNull();
	}
	
	@Test
	public void canReportWrongPlace() throws Exception{
		
		Locale requestLocale = Locale.forLanguageTag("pl-PL");
		
		Mockito
			.when(wrongPlaceReportService.save(Matchers.refEq(wrongPlaceReportDTO), Matchers.refEq(requestLocale)))
			.thenReturn(wrongPlaceReportDTO);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Accept-Language", "pl-PL");
		httpHeaders.add("Authorization", "Basic c3BvdDpmaW5kZXI=");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/places/report")
				.headers(httpHeaders)
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleWrongPlaceReportJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		Mockito
			.verify(wrongPlaceReportValidator, Mockito.times(1))
			.validate(Matchers.refEq(wrongPlaceReportDTO));
		
		Mockito
			.verify(wrongPlaceReportService, Mockito.times(1))
			.save(Matchers.refEq(wrongPlaceReportDTO), Matchers.refEq(requestLocale));
	}
}
