package com.selvesperer.knoeien.service;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public interface JobService {
	
	public Job findJobById(String id);

	public Job saveJob(JobModel jobModel);

	public Job showJobInfo(String id);
	
	public Job showBestPaidJob();
	
}
