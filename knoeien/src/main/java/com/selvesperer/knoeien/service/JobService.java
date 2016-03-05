package com.selvesperer.knoeien.service;

import java.util.List;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobService {
	
	public Job findJobById(String id);

	public Job saveJob(JobModel jobModel);
	
	public List<Job> showLatestJob();
	
	public List<Job> showBestPaidJob();
	
	public List<Job> showEarliestDeadlineJob();
	
}
