package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RestMessage implements Serializable {
	private static final long serialVersionUID = -4434893704202372838L;

	@JsonInclude(Include.NON_EMPTY)
	private String field;

	@JsonInclude(Include.NON_EMPTY)
	private String message;

	@JsonInclude(Include.NON_EMPTY)
	private String type;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
