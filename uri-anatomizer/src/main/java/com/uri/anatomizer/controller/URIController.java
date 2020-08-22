package com.uri.anatomizer.controller;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uri.anatomizer.exception.URIAnatomizeException;
import com.uri.anatomizer.model.URIModel;
import com.uri.anatomizer.service.URIService;

@RestController
@RequestMapping(value = "/uri/anatomize", produces = MediaType.APPLICATION_JSON_VALUE)
public class URIController {
	
	@Autowired
	private URIService uriSvc;
	
	Map<String, String> resultMap;
	@PostMapping("/add")
	public Map<String,String> addUri(@Valid @RequestBody URIModel newURI)throws URIAnatomizeException {
		resultMap = new LinkedHashMap<>();
		try {
			resultMap.putAll(uriSvc.parseAndDisplayComponents(newURI.getUriValue()));
		} catch (URISyntaxException exception) {
			resultMap.put("error", "Malformed URI");
			return resultMap;
		}
		
		resultMap.put("status", "success");
		return resultMap;
		
	}
	
	@ExceptionHandler(URIAnatomizeException.class)
    public ResponseEntity<Map> handleException(URIAnatomizeException exception) {
	     resultMap = new LinkedHashMap<>();
	     resultMap.put("ERR_COMPONENT", exception.getMessage());
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
    }
}
