package com.uri.anatomizer.service;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uri.anatomizer.exception.URIAnatomizeException;
import com.uri.anatomizer.model.URIModel;
import com.uri.anatomizer.parser.URIParser;
import com.uri.anatomizer.repository.URIRepository;

@Service
public class URIService {
	
	
	private URIRepository repository;
	
	private URIParser parser;
	
	@Autowired
	public void setParser(URIParser parser) {
		this.parser = parser;
	}
	
	@Autowired
	public void setRepository(URIRepository repository) {
		this.repository = repository;
	}
	Map<String,String> uriComponents;
	
	
	public Map<String,String> saveURI(URIModel uriModel) throws URIAnatomizeException, URISyntaxException {
		Map<String,String> resultMap = new LinkedHashMap<>();
		if(!repository.existsById(uriModel.getUriName())){
			resultMap = parseAndDisplayComponents(uriModel.getUriValue());
			repository.save(uriModel);
		} else {
			resultMap.put("ERR", "A URI with that name already exists");
		}
		return resultMap;
		
	}
	
	public Map<String,String> retrieveURI(String name) throws URIAnatomizeException,URISyntaxException{
		Map<String,String> notFoundMap = new LinkedHashMap<String, String>();
		if(repository.existsById(name)) {
			Optional<URIModel> opModel = repository.findById(name);
			URIModel model = opModel.get();
			return parseAndDisplayComponents(model.getUriValue());
		} else {
			notFoundMap.put("ERR", "Requested URI Not Found in Database");
			return notFoundMap;
		}
		
	}
	
	private Map<String,String> parseAndDisplayComponents(String uri) throws URISyntaxException, URIAnatomizeException{
		return parser.parse(uri);
	}

}
