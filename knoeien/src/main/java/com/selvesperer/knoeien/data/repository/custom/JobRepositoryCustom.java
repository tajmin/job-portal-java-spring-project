package com.selvesperer.knoeien.data.repository.custom;

import java.util.List;

import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobRepositoryCustom {
	
	public List<JobModel> findJobs(String type, int page, int limit);
	
	public List<JobModel> findJobByCreatedUserId(String id, int page, int limit);
	
	public List<JobModel> findJobByAssignedUserId(String id, int page, int limit);
	
	public List<JobModel> findNearestJobs(String userLatitude, String userLongitude,int page, int limit);
}
