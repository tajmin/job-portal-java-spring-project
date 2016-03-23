package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;

import com.selvesperer.knoeien.data.domain.User;

public class EmployeerModel implements Serializable {

	private static final long serialVersionUID = 2361372848872111233L;

	private String id;
	private String address;
	private String backgroundImageUrl;
	private String fullName;
	
	public EmployeerModel() {}
	
	public EmployeerModel(User user) {
		super();
		this.setId(user.getId());
		this.setFullName(user.getFullName());
		this.setAddress(user.getAddress());
		this.setBackgroundImageUrl(user.getBackgroundImageUrl());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	public void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}		
}
