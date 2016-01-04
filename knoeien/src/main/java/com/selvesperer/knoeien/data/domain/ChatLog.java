package com.selvesperer.knoeien.data.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "chat_log")
public class ChatLog extends AuditableEntity {

	private static final long serialVersionUID = -8696993602370773115L;
	
	@Column(name = "from_user_id", nullable = false, length = 100)
	private String fromUserId;
	
	@Column(name = "to_user_id", nullable = false, length = 100)
	private String toUserId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateTime")
	@JsonIgnore
	private Calendar dateTime;
	
	@Column(name = "chat", length = 1000)
	private String chat;
	
	public ChatLog() {
		super();
	}
	
	public String getFromUserId() {
		return fromUserId;
	}
	
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	public String getToUserId() {
		return toUserId;
	}
	
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	
	public Calendar getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
	}
	
	public String setChat() {
		return chat;
	}
	
	public void getChat(String chat) {
		this.chat = chat;
	}

	@Override
	public String getObjCode() {
		// TODO Auto-generated method stub
		return "chlg";
	}

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

}
