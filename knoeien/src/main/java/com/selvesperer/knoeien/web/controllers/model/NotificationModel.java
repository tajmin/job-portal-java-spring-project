package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.Calendar;

import com.selvesperer.knoeien.data.domain.Notification;

public class NotificationModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6486045860668870614L;
	
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Calendar expireDate) {
		this.expireDate = expireDate;
	}

	private String description;
	private Calendar expireDate;
	
	public NotificationModel(){}
	
	public NotificationModel(Notification notification){
		super();
		
		this.setName(notification.getName());
		this.setDescription(notification.getDescription());
		this.setExpireDate(notification.getExpireDate());
		
	}

}
