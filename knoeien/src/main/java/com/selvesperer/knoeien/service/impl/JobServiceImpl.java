package com.selvesperer.knoeien.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.repository.JobRepository;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
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
	public Job findJobById(String jobID) {
		return jobRepository.findJobById(jobID);
	}
	
	@Override
	public Job saveJob(JobModel jobModel) {
		Job job = null;
		if(StringUtils.isNotBlank(jobModel.getId())){
			job = jobRepository.findJobById(jobModel.getId());
		}else{
			job = new Job();
		}
		
		job = job.setJob(jobModel);
		return jobRepository.saveAndFlush(job);
	}

	@Override
	public List<JobModel> findjobs(String type, int page, int limit) {
		List<JobModel> jobs = jobRepository.findJobs(type, page, limit);
		return jobs;
	}
	
	@Override
	public List<JobModel> findJobByAssignedUserId(String id, int page, int limit) {
		List<JobModel> jobs = jobRepository.findJobByAssignedUserId(id, page, limit);
		return jobs;
	}

	@Override
	public List<JobModel> findJobByCreatedUserId(String id, int page, int limit) {
		List<JobModel> jobs = jobRepository.findJobByCreatedUserId(id, page, limit);
		return jobs;
	}

	@Override
	public Job updateJob(String id) {
		Job job = jobRepository.findJobById(id);
		JobModel jobmodel = new JobModel();
		
		if (job == null) {
			throw new AuthenticationFailedException("error.jobnotfound.text");
		}
		
		job.setDraft(false);
		jobRepository.saveAndFlush(job);
		
		return null;
	}

	@Override
	public Object[] makeStripePayment(String id) {
		return jobRepository.makePaymentByJobId(id);
	}
	
}
