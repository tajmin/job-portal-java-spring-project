package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.selvesperer.knoeien.data.domain.TransactionHistory;
import com.selvesperer.knoeien.utils.DateFormatUtils;

public class TransactionHistoryModel implements Serializable{

	private static final long serialVersionUID = 630404806150067892L;

	private String fromUserId;
	
	private String toUserId;
	
	private Double amount;
	
	private String jobId;
	
	private String userId;
	
	private String jobTitle;
	
	private String createdByName;
	
	private String createdDate;
	
	public TransactionHistoryModel() {}
	
	public TransactionHistoryModel(TransactionHistory transactionHistory) {
		super();		
		this.setAmount(transactionHistory.getAmount());
		this.setFromUserId(transactionHistory.getFromUserId());
		this.setToUserId(transactionHistory.getToUserId());
		this.setJobId(transactionHistory.getJobId());
		this.setUserId(transactionHistory.getUserId());		
	}
	
	public List<TransactionHistoryModel> getModelList(List<Object[]> List) {
		List<TransactionHistoryModel> modelObjectList = new ArrayList<>();
		for(Object[] objs : List ) {
			TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel();
			transactionHistoryModel.setAmount((Double) objs[0]);
			transactionHistoryModel.setCreatedByName((String) objs[1]);
			transactionHistoryModel.setCreatedDate(DateFormatUtils.getShortFormattedDateString((Timestamp) objs[2]));
			transactionHistoryModel.setJobTitle((String) objs[3]);
			
			modelObjectList.add(transactionHistoryModel);
		}
		return modelObjectList;
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
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
