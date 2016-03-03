package com.selvesperer.knoeien.data.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;

@Entity
@Table(name = "message")
public class Message extends AuditableEntity{

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1256559073596277742L;
	
	
	//member start
	@Column(name = "to_user_id", nullable = false, length = 100)
	private String toUserId;
	
	@Column(name = "from_user_id", nullable = false, length = 100)
	private String fromUserId;
	
	@Column(name = "user_message",length = 250)
	private String userMessage;

	@Column(name = "job_id", nullable = false, length = 100)
	private String jobId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "send_date_time")
	@JsonIgnore
	private Calendar sendDateTime;

	//member ends
	
	
	
	//getter setter
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

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Calendar getSendDateTime() {
		return sendDateTime;
	}

	public void setSendDateTime(Calendar sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	
	//getter setter ends
	
	
	//cons
	
	public Message(){}

	public Message(MessageModel messageModel){
		
		this.setToUserId(messageModel.getToUserId());
		this.setFromUserId(messageModel.getFromUserId());
		this.setUserMessage(messageModel.getUserMessage());
		this.setJobId(messageModel.getJobId());
		this.setSendDateTime(messageModel.getSendDateTime());		
	}
	
	
	
}
