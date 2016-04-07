package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.math.BigInteger;
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
	
	
	private String toUserId;
	private String fromUserId;
	private String userMessage;
	private String jobId;
	private Calendar sendDateTime;
	
	private String price;
	private String bidAmount;
	private String firstname;
	private String lastname;
	private String userId;
	private String messageId;
	private String backgroundImageUrl;
	private BigInteger countMessageId;
	private String jobTitle;
	private String jobDescription;
	
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
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String id) {
		this.userId = id;
	}
	
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	
	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}
	public void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}
	
	
	public BigInteger getCountMessageId() {
		return countMessageId;
	}
	public void setCountMessageId(BigInteger countMessageId) {
		this.countMessageId = countMessageId;
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(String bidAmount) {
		this.bidAmount = bidAmount;
	}
	
	
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
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
