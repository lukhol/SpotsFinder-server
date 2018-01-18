package com.polibuda.pbl.endpoints;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
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

import com.polibuda.pbl.dto.CoordinatesDTO;
import com.polibuda.pbl.dto.LightPlaceDTO;
import com.polibuda.pbl.dto.PlaceSearchDTO;
import com.polibuda.pbl.endpoint.PlaceSearchRestEndpoint;
import com.polibuda.pbl.service.PlaceService;
import com.polibuda.pbl.validator.PlaceSearchValidator;

@RunWith(SpringRunner.class)
@WebMvcTest({PlaceSearchRestEndpoint.class, PlaceSearchValidator.class})
public class PlaceSearchRestEndpointTests {

	@Autowired
	private PlaceSearchRestEndpoint placeSearchRestEndpoint;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlaceSearchValidator placeSearchValidator;
	
	@MockBean
	private PlaceService placeService;
	
	private String placeSearchDtoJson = "{\"type\":[0,1,2],\"location\":{\"city\":\"\u0142\u00F3d\u017A\",\"longitude\":null,\"latitude\":null},\"distance\":10,\"gap\":false,\"stairs\":false,\"rail\":false,\"ledge\":false,\"handrail\":false,\"corners\":false,\"manualpad\":false,\"wallride\":false,\"downhill\":false,\"openYourMind\":false,\"pyramid\":false,\"curb\":false,\"bank\":false,\"bowl\":false,\"hubba\":false}";
	private String expectedLightPlacesListJson = "[{\"id\":1,\"version\":1234,\"mainPhoto\":\"base64mainphotoaco\",\"name\":\"Spot name\",\"description\":\"Spot description\",\"location\":{\"longitude\":19.461916834115982,\"latitude\":51.75316724600984},\"type\":1}]";
	private List<LightPlaceDTO> expectedLightPlaceDTOList;
	
	@Before
	public void setUp(){
		expectedLightPlaceDTOList = new ArrayList<LightPlaceDTO>();
		LightPlaceDTO lightPlaceDTO = LightPlaceDTO
											.builder()
											.id(1l)
											.version(1234l)
											.mainPhoto("base64mainphotoaco")
											.description("Spot description")
											.name("Spot name")
											.type(1)
											.location(CoordinatesDTO.builder().longitude(19.461916834115982).latitude(51.75316724600984).build())
											.build();
		
		expectedLightPlaceDTOList.add(lightPlaceDTO);	
	}
	
	@Test
	public void canCreatePlaceSearchRestEndpoint(){
		assert placeSearchRestEndpoint != null;
	}
	
	@Test(expected = NullPointerException.class)
	public void cannotCreatePlaceSearchRestEndpoint_nullDependencies(){
		placeSearchRestEndpoint = new PlaceSearchRestEndpoint(null, null);
	}
	
	//@Test
	public void test() throws Exception{
		
		Mockito
			.when(placeService.search(Mockito.any(PlaceSearchDTO.class)))
			.thenReturn(expectedLightPlaceDTOList);	
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/places/searches")
				.header("Authorization", "Basic c3BvdGZpbmRlcjpzcG90ZmluZGVyU2VjcmV0")
				.accept(MediaType.APPLICATION_JSON)
				.content(placeSearchDtoJson)
				.contentType(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String placesResponse = response.getContentAsString();
		
		assert expectedLightPlacesListJson.equals(placesResponse);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		Mockito
			.verify(placeSearchValidator, Mockito.times(1))
			.validate(Mockito.any(PlaceSearchDTO.class));
		
		Mockito
			.verify(placeService, Mockito.times(1))
			.search(Mockito.argThat(search -> {
				PlaceSearchDTO placeSearch = (PlaceSearchDTO)search;
				return Arrays.equals(placeSearch.getType(), new int[] {0, 1, 2}) &&
						placeSearch.getLocation().getCity().equals("≥Ûdü") &&
						placeSearch.getLocation().getLatitude() == null &&
						placeSearch.getLocation().getLongitude() == null &&
						placeSearch.getDistance() == 10 &&
						placeSearch.isCorners() == false;
			}));
	}
}
