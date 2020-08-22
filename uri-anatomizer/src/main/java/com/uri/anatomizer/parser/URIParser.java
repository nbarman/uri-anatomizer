package com.uri.anatomizer.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.uri.anatomizer.exception.URIAnatomizeException;

@Component
public class URIParser {
	private static final String REGEX_SC="/^[a-zA-Z0-9](.*[a-zA-Z0-9])?$/";
	private String protocol;
	private String hostAddr;
	private String path;
	private String query;
	private String fragment; //Optional
	private String userInfo;
	private String port; //Optional
	private String userName;
	private String password;
	
	Map<String,String> uriComponentsMap;
	MultiValueMap<String,String> queryParams;
	boolean malformedURI;
	
	//All available protocols (Maybe modified in the future)
	private enum Protocols{
			http,https,ftp,sftp
	};
	
	
	public Map<String,String> parseUri(String uriStr) throws URISyntaxException,URIAnatomizeException {
		
		malformedURI= false;
		URI uri = new URI(uriStr);
		uriComponentsMap = new LinkedHashMap<>();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(uri);
		UriComponents uriComponents = uriComponentsBuilder.build();
		protocol = uriComponents.getScheme();
		hostAddr = uriComponents.getHost();
		userInfo = uriComponents.getUserInfo();
		path = uriComponents.getPath();
		port = new Integer(uriComponents.getPort()).toString();
		queryParams = uriComponents.getQueryParams();
		fragment = uriComponents.getFragment();
		if(protocol==null || hostAddr==null || path==null 
				|| protocol.isEmpty() || hostAddr.isEmpty() || path.isEmpty() || queryParams.isEmpty()) {
			malformedURI = true;
		} else if(isMalformedURI("protocol", protocol)){
			malformedURI = true;
		} else {		
					retrieveUserNamePassword(userInfo);
			
		}
		// After passing validations, output the components
		if(!malformedURI) {
		uriComponentsMap.put("protocol", protocol);
		uriComponentsMap.put("userName", userName);
		uriComponentsMap.put("password", password);
		uriComponentsMap.put("host", hostAddr);
		if(!port.equals("-1")) {
			uriComponentsMap.put("port", port);
		}
		uriComponentsMap.put("path", path);
		//Get all query params (Lambda)
		StringBuilder queryParams = new StringBuilder();
		uriComponents.getQueryParams().forEach((k,v) ->{
			
			queryParams.append(k);
			queryParams.append(":");
			queryParams.append(v);
			queryParams.append("|");
		});
		uriComponentsMap.put("queryParams",  queryParams.toString());
		if(fragment!=null && !fragment.isEmpty()) {
				uriComponentsMap.put("fragment", fragment);
			}
		} else {
			throw new URISyntaxException(uriStr, "Malformed URI");
		}
		return uriComponentsMap;
		
	}
	
	//Validates and retrieves the usename and password from the component
	private void retrieveUserNamePassword(String userInfo) throws URIAnatomizeException {
		//Check for userInfo
		String userInfoDetails[] = userInfo.split(":");
		if(userInfoDetails.length==2) {
			userName = userInfoDetails[0].trim();
			password = userInfoDetails[1].trim();
			if(userName.isEmpty() || password.isEmpty()) {
				throw new URIAnatomizeException("userName || Password");
			} 
		}
		
	}
	
	//Checks for malformed characters in the various components
	private boolean isMalformedURI(String cName, String component) {
		
		if(cName.equalsIgnoreCase("protocol")) {
			if(Arrays.stream(Protocols.values()).anyMatch((t) -> t.name().equalsIgnoreCase(component))) {
				return false;
			}}
		return true;
	}

}
