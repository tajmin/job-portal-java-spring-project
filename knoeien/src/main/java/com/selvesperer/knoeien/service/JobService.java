package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobService {
	
	public Job findJobById(String id);

	public Job saveJob(JobModel jobModel);
	
	public List<JobModel> findjobs(String type, int page, int limit);	
	
	public List<Job> findJobByAssignedUserId(String assignedUserId);
	
	public List<Job> findJobByCreatedUserId(String createdByUserId);
	
	public Job updateJob(String id);
	
}
