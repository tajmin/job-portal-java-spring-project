package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.domain.JobInterested;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;

public interface JobInterestedService {

	
	public JobInterested saveJobInterested(JobInterestedModel jobInterestedModel);
	
	public List<JobInterested> findJobInterestedUserId(String Id);
	
	public List<JobInterested> showLowestBidAmount();
	
}
