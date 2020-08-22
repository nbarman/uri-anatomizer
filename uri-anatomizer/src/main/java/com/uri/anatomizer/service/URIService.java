package com.uri.anatomizer.service;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uri.anatomizer.exception.URIAnatomizeException;
import com.uri.anatomizer.parser.URIParser;
import com.uri.anatomizer.repository.URIRepository;

@Service
public class URIService {
	
	@Autowired
	private URIRepository uriRepo;
	
	@Autowired
	private URIParser parser;
	Map<String,String> uriComponents;
	
	public Map<String,String> parseAndDisplayComponents(String uri) throws URISyntaxException, URIAnatomizeException{
		return parser.parseUri(uri);
	}

}
