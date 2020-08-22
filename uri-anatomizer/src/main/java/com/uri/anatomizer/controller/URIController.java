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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public Map<String,String> addURI(@Valid @RequestBody URIModel newURI)throws URISyntaxException, URIAnatomizeException {
		resultMap = new LinkedHashMap<>();
		resultMap.putAll(uriSvc.saveURI(newURI));	
		resultMap.put("status", "success");
		return resultMap;	
	}
	
	@GetMapping("/get/{name}")
	public Map<String,String> retrieveURI(@PathVariable String name)throws URISyntaxException, URIAnatomizeException{
		return uriSvc.retrieveURI(name);
	}
	
	
	@ExceptionHandler(URIAnatomizeException.class)
    public ResponseEntity<Map> handleURIAnatomizeException(URIAnatomizeException exception) {
	     resultMap = new LinkedHashMap<>();
	     resultMap.put("ERR_COMPONENT", exception.getMessage());
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
    }
	
	@ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<Map> handleURISyntaxException(URISyntaxException exception) {
	     resultMap = new LinkedHashMap<>();
	     resultMap.put("error", "Malformed URI");
	     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
    }
}
