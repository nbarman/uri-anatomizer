package com.uri.anatomizer.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.uri.anatomizer.controller.URIController;
import com.uri.anatomizer.model.URIModel;
import com.uri.anatomizer.service.URIService;


@ExtendWith(SpringExtension.class)
@WebMvcTest(URIController.class)
public class URIControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private URIService uriSvc;
	
	@MockBean
	private URIModel uriModel;
	
	@InjectMocks
    private final URIController uriController = new URIController();
	
	@Before
	public void setUp() {
		 mockMvc = MockMvcBuilders.standaloneSetup(uriController).build();
		 MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown() {
		Mockito.reset(uriSvc);
	}
	
	@Test
	public void addURI_Test() throws Exception{
		URIModel testModel = new URIModel();
		testModel.setUriName("test_url1");
		testModel.setUriValue("http://test:test@test:8080/path/document?testarg1=val1&testarg2=val2#frag");
		Map<String,String> resultMap = new LinkedHashMap<>();
		resultMap.put("status", "success");
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(testModel);
	    
		Mockito.when(uriSvc.saveURI(testModel)).thenReturn(resultMap);
		mockMvc.perform(post("/uri/anatomize/add")
				.contentType("application/json")
				.content(requestJson))	
		.andExpect(status().isOk());	
	}
	
	
	@Test
	public void retrieveURI_Test() throws Exception {
		Map<String,String> resultMap = new LinkedHashMap<>();
		resultMap.put("status", "success");
		String testName="url1";
		
		Mockito.when(uriSvc.retrieveURI(testName)).thenReturn(resultMap);
		mockMvc.perform(get("/uri/anatomize/get/{name}","url1"))
				.andExpect(status().isOk());	
	}
	
	@Test
	public void addURI_DuplicateEntryTest() throws Exception {
		URIModel testModel = new URIModel();
		testModel.setUriName("test_url1");
		testModel.setUriValue("cdcdddcd://test:test@test:8080/path/document?testarg1=val1&testarg2=val2#frag");
		Map<String,String> resultMap = new LinkedHashMap<>();
		resultMap.put("ERR", "A URI with that name already exists");
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(testModel);
	    
	    Mockito.when(uriSvc.saveURI(testModel)).thenReturn(resultMap);
	    mockMvc.perform(post("/uri/anatomize/add")
				.contentType("application/json")
				.content(requestJson))
	    .andReturn();
	    assertEquals("A URI with that name already exists", resultMap.get("ERR"));
	}
	
	@Test
	public void retrieveURI_NegativeURITest() throws Exception{
		Map<String,String> resultMap = new LinkedHashMap<>();
		resultMap.put("status", "success");
		String testName="url2";
		
		Mockito.when(uriSvc.retrieveURI(testName)).thenReturn(resultMap);
		mockMvc.perform(get("/uri//get/{name}","url1"))
				.andExpect(status().isNotFound());	
		
	}

}
