package com.uri.anatomizer.exception;

public class URIAnatomizeException  extends Exception{

	private static final long serialVersionUID = 1L;

	public URIAnatomizeException(String component) {
		super("The following component is malformed or missing : " + component);
	}

}
