package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.JobInterested;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobInterestedService {
	public JobInterested saveJobInterested(JobInterestedModel jobInterestedModel);	
	public double findLowestBidAmount(String jobId);	
	public JobInterested findJobInterestDetailsByInterestUserId(String jobId, String userId);
	public List<JobModel> findAllJobInterestedByUserId(String userId, int page, int limit);
}
