package com.selvesperer.knoeien.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.domain.JobInterested;
import com.selvesperer.knoeien.data.repository.JobInterestedRepository;
import com.selvesperer.knoeien.data.repository.JobRepository;
import com.selvesperer.knoeien.service.CompanyService;
import com.selvesperer.knoeien.service.JobInterestedService;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

public class JobInterestedServiceImpl implements JobInterestedService{

	@Inject
	private JobInterestedRepository jobInterestedRepository;
	
	@Override
	public JobInterested saveJobInterested(JobInterestedModel jobInterestedModel) {
		JobInterested jobInterested = null;
		if(StringUtils.isNotBlank(jobInterestedModel.getJobId())){
			jobInterested = jobInterestedRepository.findJobById(jobInterestedModel.getJobId());
		}else{
			jobInterested = new JobInterested();
		}
		
		jobInterested = jobInterested.setJobInterested(jobInterestedModel);
		return jobInterestedRepository.saveAndFlush(jobInterested);
	}

	@Override
	public List<JobInterested> findJobInterestedUserId(String Id) {
		// TODO Auto-generated method stub
		List<JobInterested> jobInterested=new ArrayList<>(jobInterestedRepository.findJobByInterestedUserId(Id));
		return jobInterested;
	}

	@Override
	public List<JobInterested> showLowestBidAmount() {
		// TODO Auto-generated method stub
		//List<JobInterested> jobInterested = new ArrayList<>(jobInterestedRepository.findLowestBidAmount());
		return null;
	}

}
