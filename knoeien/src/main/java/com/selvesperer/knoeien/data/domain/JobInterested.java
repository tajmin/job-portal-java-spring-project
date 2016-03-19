package com.selvesperer.knoeien.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;

@Entity
@Table(name = "job_interested")
public class JobInterested extends AuditableEntity {

	private static final long serialVersionUID = 5454522042314035295L;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name ="description", length = 250)
	private String description;
	
	@Column(name ="job_id", nullable = false, length = 250)
	private String jobId;
	
	@Column(name ="job_created_by_id", nullable = false, length = 250)
	private String jobCreatedById;
	
	@Column(name ="job_interested_user_id", nullable = false, length = 250)
	private String jobInterestedUserId;
	
	
	@Column(name ="bid_amount")
	private Double bidAmount;
	
	public JobInterested() {}
	
	public JobInterested setJobInterested(JobInterestedModel jobInterestedModel) {
		
		this.setName(jobInterestedModel.getName());
		this.setDescription(jobInterestedModel.getDescription());
		this.setBidAmount(jobInterestedModel.getBidAmount());
		this.setJobCreatedById(jobInterestedModel.getJobCreatedById());
		this.setJobId(jobInterestedModel.getJobId());
		this.setJobInterestedUserId(jobInterestedModel.getJobInterestedUserId());
		
		
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

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobCreatedById() {
		return jobCreatedById;
	}

	public void setJobCreatedById(String jobCreatedById) {
		this.jobCreatedById = jobCreatedById;
	}

	public String getJobInterestedUserId() {
		return jobInterestedUserId;
	}

	public void setJobInterestedUserId(String jobInterestedUserId) {
		this.jobInterestedUserId = jobInterestedUserId;
	}

	public Double getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(Double bidAmount) {
		this.bidAmount = bidAmount;
	}
	
	
}
