package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobService {
	
	public Job findJobById(String id);

	public Job saveJob(JobModel jobModel);
	
	public List<JobModel> findLatestJobs(int page, int limit);
	
	public List<Job> showBestPaidJob();
	
	public List<Job> showEarliestDeadlineJob();
	
	public List<Job> findJobByAssignedUserId(String assignedUserId);
	
	public List<Job> findJobByCreatedUserId(String createdByUserId);
	
}
