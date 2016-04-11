package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.selvesperer.knoeien.data.domain.Notification;

public class NotificationModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6486045860668870614L;
	
	private String id;
	
	private String name;
	
	private String description;
	
	private Calendar expireDate;
	
	private String jobId;
	
	private String toUserId;
	
	private String fromUserId;
	
	private boolean hasSeen;

	public NotificationModel(){}
	
	public NotificationModel(Notification notification){
		super();
		this.setName(notification.getName());
		this.setDescription(notification.getDescription());
		this.setExpireDate(notification.getExpireDate());		
		this.setJobId(notification.getJobId());
		this.setToUserId(notification.getToUserId());
		this.setFromUserId(notification.getFromUserId());
		this.setHasSeen(notification.isHasSeen());
	}
	
	public List<NotificationModel> getNotificationModelList(List<Notification> notificationList){
		List<NotificationModel> notificationModelList=new ArrayList<>();
		for(Notification notification:notificationList) {
			NotificationModel notificationModel=new NotificationModel(notification);
			notificationModelList.add(notificationModel);
		}
		return notificationModelList; 
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
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

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public boolean isHasSeen() {
		return hasSeen;
	}

	public void setHasSeen(boolean hasSeen) {
		this.hasSeen = hasSeen;
	}

}
