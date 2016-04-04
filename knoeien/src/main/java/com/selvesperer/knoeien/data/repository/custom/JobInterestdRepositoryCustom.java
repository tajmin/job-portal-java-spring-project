package com.selvesperer.knoeien.data.repository.custom;

import java.util.List;

import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobInterestdRepositoryCustom {	
	public double findLowestBid(String jobId);
	public List<JobModel> findAllJobByInterestedUserId(String userId, int page, int limit);
}
