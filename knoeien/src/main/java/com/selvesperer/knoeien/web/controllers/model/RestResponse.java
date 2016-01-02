package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;

public class RestResponse implements Serializable{
	private static final long serialVersionUID = -2363000710319990666L;
	
	private Object response;
	
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
}
