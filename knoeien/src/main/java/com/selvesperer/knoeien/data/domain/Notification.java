package com.selvesperer.knoeien.data.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selvesperer.knoeien.web.controllers.model.NotificationModel;

@Entity
@Table(name = "notification")
public class Notification extends AuditableEntity {

	private static final long serialVersionUID = -3145959798135200435L;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name ="description", length = 250)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_date")
	@JsonIgnore
	private Calendar expireDate;
	
	@Column(name ="job_id", length = 250)
	private String jobId;
	
	@Column(name ="to_user_id", length = 250)
	private String toUserId;
	
	@Column(name ="from_user_id", length = 250)
	private String fromUserId;
	
	@Column(name = "has_seen", nullable = false, length = 1)
	private boolean hasSeen = false;
	
	public Notification() {
		
	}
	
	public Notification(NotificationModel notificationModel){
		super();
		this.setName(notificationModel.getName());
		this.setExpireDate(notificationModel.getExpireDate());
		this.setDescription(notificationModel.getDescription());
		
		this.setJobId(notificationModel.getJobId());
		this.setToUserId(notificationModel.getToUserId());
		this.setFromUserId(notificationModel.getFromUserId());
		this.setHasSeen(notificationModel.isHasSeen());
	}
	
	public Notification setNotification(NotificationModel notificationModel){
		this.setName(notificationModel.getName());
		this.setExpireDate(notificationModel.getExpireDate());
		this.setDescription(notificationModel.getDescription());
		
		this.setJobId(notificationModel.getJobId());
		this.setToUserId(notificationModel.getToUserId());
		this.setFromUserId(notificationModel.getFromUserId());
		this.setHasSeen(notificationModel.isHasSeen());
		return this;
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
