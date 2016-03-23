package com.selvesperer.knoeien.web.controllers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.selvesperer.knoeien.data.domain.JobInterested;

public class JobInterestedModel  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2054948275236797349L;
	
	private String id;
	private String jobId;
	private String jobCreatedById;
	private String jobInterestedUserId;
	private Double bidAmount;
	
	public JobInterestedModel() {}
	
	public JobInterestedModel(JobInterested jobInterested) {
		this.setJobId(jobInterested.getJobId());
		this.setJobCreatedById(jobInterested.getJobCreatedById());
		this.setJobInterestedUserId(jobInterested.getJobInterestedUserId());
		this.setBidAmount(jobInterested.getBidAmount());
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<JobInterestedModel> getJobModelList(List<JobInterested> jobInterestedList) {
		List<JobInterestedModel> jobInterestedModelList = new ArrayList<>();
		for(JobInterested jobInterested : jobInterestedList) {
			JobInterestedModel jobInterestedModel = new JobInterestedModel(jobInterested);
			jobInterestedModelList.add(jobInterestedModel);
		}
		return jobInterestedModelList;
	}
	

}
