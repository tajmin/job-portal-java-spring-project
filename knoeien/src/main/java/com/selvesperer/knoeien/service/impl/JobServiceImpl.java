package com.selvesperer.knoeien.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.repository.JobRepository;
import com.selvesperer.knoeien.service.JobService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

@Service("jobService")
@Scope(ScopeType.SINGLETON)
public class JobServiceImpl implements JobService {
	
	private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

	@Inject
	private JobRepository jobRepository;
	
	@Override
	public Job findJobById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Job saveJob(JobModel jobModel) {		
		Job job = new Job(jobModel);
		return jobRepository.saveAndFlush(job);
	}

	@Override
	public Job showJobInfo(String id) {
		// TODO Auto-generated method stub
		Job job = jobRepository.findJobById(id);
		return job;
	}

	@Override
	public Job showBestPaidJob() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
