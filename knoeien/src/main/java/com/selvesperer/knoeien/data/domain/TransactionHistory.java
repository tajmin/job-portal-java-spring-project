package com.selvesperer.knoeien.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.selvesperer.knoeien.web.controllers.model.TransactionHistoryModel;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory extends AuditableEntity {

	private static final long serialVersionUID = 6898334057428990766L;
	
	@Column(name = "from_user_id", nullable = false, length = 100)
	private String fromUserId;
	
	@Column(name = "to_user_id", nullable = false, length = 100)
	private String toUserId;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "job_id", nullable = false, length = 100)
	private String jobId;
	
	public TransactionHistory() {
	}
	
	public TransactionHistory(TransactionHistoryModel transactionHistoryModel) {
		super();
		this.setFromUserId(transactionHistoryModel.getFromUserId());
		this.setToUserId(transactionHistoryModel.getToUserId());
		this.setAmount(transactionHistoryModel.getAmount());
		this.setJobId(transactionHistoryModel.getJobId());
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
	
}
