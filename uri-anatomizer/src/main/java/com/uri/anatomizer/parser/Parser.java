package com.uri.anatomizer.parser;

import java.net.URISyntaxException;
import java.util.Map;

import com.uri.anatomizer.exception.URIAnatomizeException;

@FunctionalInterface
public interface Parser {
	public Map<String,String> parse(String uriStr) throws URISyntaxException,URIAnatomizeException;
}
