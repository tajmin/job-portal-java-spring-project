package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.domain.Message;
import com.selvesperer.knoeien.utils.DateFormatUtils;

public class MessageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4472403961193063417L;
	
	
	//member var
	private String toUserId;
	private String fromUserId;
	private String userMessage;
	private String jobId;
	private Calendar sendDateTime;
	//member var ends
	
	
	
	//getter -setter
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
	public MessageModel(){}
	
	public MessageModel(Message message){
		
		
		this.setToUserId(message.getToUserId());
		this.setFromUserId(message.getFromUserId());
		this.setUserMessage(message.getUserMessage());
		this.setJobId(message.getJobId());
		this.setSendDateTime(message.getSendDateTime());
		//this.setSendDateTime(DateFormatUtils.getDBFormattedDateString(message.getSendDateTime()));		
	}
	
	//MessageModelList
	public List<MessageModel> getMessageModelList(List<Message> messageList) {
		List<MessageModel> messageModelList = new ArrayList<>();
		for(Message message : messageList) {
			MessageModel messageModel = new MessageModel(message);
			messageModelList.add(messageModel);
		}
		return messageModelList;
	}
	
	
}
