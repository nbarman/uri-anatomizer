package com.uri.anatomizer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class URIModel {
	
	@Id
	@NotNull(message = "Please pass the URI Name")
	@NotBlank(message = "uriName cannot be blank")
	@JsonProperty("name")
	private String uriName;
	
	@NotNull(message = "Please pass a URI value")
	@NotBlank(message = "Cannot be blank")
	@JsonProperty("uri")
	private String uriValue;

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
	}

	public String getUriValue() {
		return uriValue;
	}

	public void setUriValue(String uriValue) {
		this.uriValue = uriValue;
	}

}
