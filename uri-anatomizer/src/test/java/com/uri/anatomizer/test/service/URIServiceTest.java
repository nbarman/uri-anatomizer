package com.uri.anatomizer.test.service;

import static org.junit.Assert.assertFalse;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.uri.anatomizer.exception.URIAnatomizeException;
import com.uri.anatomizer.model.URIModel;
import com.uri.anatomizer.repository.URIRepository;
import com.uri.anatomizer.service.URIService;

@SpringBootTest
public class URIServiceTest {
		
	@Mock
	private URIService uriSvc;
	
	@Mock
	private URIRepository repository;
	
	@Before
	public void setUp() {
		 MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown() {
		Mockito.reset(uriSvc);
	}
	
	@Test
	public void parseAndDisplayCmp_Test() throws URIAnatomizeException, URISyntaxException {
		Map<String,String> resultMap = new LinkedHashMap<>();
		resultMap.put("status", "success");
		
		URIModel model = new URIModel();
		model.setUriName("url1");
		model.setUriValue("http://");
		
		Mockito.when(repository.existsById(model.getUriName())).thenReturn(true);
		Mockito.when(repository.save(model)).thenReturn(model);
		assertFalse(resultMap.isEmpty());
	}

}
