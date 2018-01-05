package com.polibuda.pbl.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.polibuda.pbl.email.EmailSender;
import com.polibuda.pbl.endpoint.ErrorRestEndpoint;
import com.polibuda.pbl.interceptors.SecureEndpointsInterceptor;
import com.polibuda.pbl.model.MobileAppError;
import com.polibuda.pbl.service.MobileAppErrorService;

//@SpringBootTest(classes = Application.class)
@WebMvcTest({ ErrorRestEndpoint.class, EmailSender.class, SecureEndpointsInterceptor.class })
@RunWith(SpringRunner.class)
public class ErrorRestEndpointTests {
	
	@Autowired
	private ErrorRestEndpoint errorRestEndpoint;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MobileAppErrorService errorService;
	
	@MockBean
	private EmailSender emailSender;
	
	private String exampleErrorJson = "{  \r\n   \"errorId\":11,\r\n   \"deviceInfo\":{  \r\n      \"idiom\":\"Phone\",\r\n      \"model\":\"LG-M200\",\r\n      \"version\":\"7.0\",\r\n      \"versionNumber\":\"7.0\",\r\n      \"platform\":\"Android\"\r\n   },\r\n   \"className\":\"Error\",\r\n   \"message\":\"Error message.\"\r\n}";
	
	@Test
	public void canCreateErrorRestEndpoint(){
		assertThat(errorRestEndpoint).isNotNull();
	}
	
	@Test
	public void canPostError() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/errors")
				.header("Authorization", "Basic c3BvdDpmaW5kZXI=")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleErrorJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		Mockito
			.verify(errorService, Mockito.times(1))
			.save(Mockito.any(MobileAppError.class));
		
		Mockito
			.verify(emailSender, Mockito.times(1))
			.sendEmail(Mockito.matches("lukasz.holdrowicz@gmail.com"), Mockito.matches("Error json:"), Mockito.anyString());
	}
}
