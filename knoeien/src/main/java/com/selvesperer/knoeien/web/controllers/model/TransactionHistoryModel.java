package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.selvesperer.knoeien.data.domain.TransactionHistory;

public class TransactionHistoryModel implements Serializable{

	private static final long serialVersionUID = 630404806150067892L;

	private String fromUserId;
	
	private String toUserId;
	
	private BigDecimal amount= BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	private String jobId;
	
	private String userId;
	
	
	public TransactionHistoryModel() {}
	
	public TransactionHistoryModel(TransactionHistory transactionHistory) {
		super();		
		this.setAmount(transactionHistory.getAmount());
		this.setFromUserId(transactionHistory.getFromUserId());
		this.setToUserId(transactionHistory.getToUserId());
		this.setJobId(transactionHistory.getJobId());
		this.setUserId(transactionHistory.getUserId());		
	}
	
	public List<TransactionHistoryModel> getTransactionHistoryModelList(List<TransactionHistory> transactionHistoryList) {
		List<TransactionHistoryModel> transactionHistoryModelList = new ArrayList<>();
		for(TransactionHistory transactionHistory : transactionHistoryList ) {
			TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel(transactionHistory);
			transactionHistoryModelList.add(transactionHistoryModel);
		}
		return transactionHistoryModelList;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
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
	
	
	
	

	
	
}
