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

@WebMvcTest({ ErrorRestEndpoint.class, SecureEndpointsInterceptor.class })
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
	
	private String exampleErrorJson = "{\"errorId\":9,\"deviceInfo\":{\"idiom\":\"Phone\",\"model\":\"Redmi Note 3\",\"version\":\"6.0.1\",\"versionNumber\":\"6.0.1\",\"platform\":\"Android\"},\"className\":\"Error\",\"message\":\"Exception has been thrown by the target of an invocation.\",\"whereOccurred\":\"AndroidCurrentDomain\",\"stackTraceString\":\"StackTraceString\"}";
	
	@Test
	public void canCreateErrorRestEndpoint(){
		assertThat(errorRestEndpoint).isNotNull();
	}
	
	@Test(expected = NullPointerException.class)
	public void cannotCreateErrorRestEndpoint(){
		errorRestEndpoint = new ErrorRestEndpoint(null,  null);
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
			.sendEmail("lukasz.holdrowicz@gmail.com", "Error json:", exampleErrorJson);
	}
}
