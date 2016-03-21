package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RestResponse implements Serializable {
	private static final long serialVersionUID = -2363000710319990666L;

	@JsonInclude(Include.NON_EMPTY)
	private boolean success = true;
	
	@JsonInclude(Include.NON_NULL)
	private Object response;
	
	@JsonInclude(Include.NON_EMPTY)
	private String message;

	@JsonInclude(Include.NON_EMPTY)
	private ArrayList<RestMessage> messages;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<RestMessage> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<RestMessage> messages) {
		this.messages = messages;
	}

}
