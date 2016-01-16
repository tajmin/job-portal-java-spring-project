package com.selvesperer.knoeien.data.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory extends AuditableEntity {

	private static final long serialVersionUID = 6898334057428990766L;
	
	@Column(name = "from_user_id", nullable = false, length = 100)
	private String fromUserId;
	
	@Column(name = "to_user_id", nullable = false, length = 100)
	private String toUserId;
	
	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	@Column(name = "job_id", nullable = false, length = 100)
	private String jobId;
	
	@Column(name = "user_id", nullable = false, length = 100)
	private String userId;
	
	public TransactionHistory() {
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
