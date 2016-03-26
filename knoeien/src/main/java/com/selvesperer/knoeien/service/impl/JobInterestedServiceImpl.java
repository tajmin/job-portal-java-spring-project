package com.selvesperer.knoeien.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.JobInterested;
import com.selvesperer.knoeien.data.repository.JobInterestedRepository;
import com.selvesperer.knoeien.service.JobInterestedService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;

@Service("jobInterestedService")
@Scope(ScopeType.SINGLETON)
public class JobInterestedServiceImpl implements JobInterestedService{

	@Inject
	private JobInterestedRepository jobInterestedRepository;
	
	@Override
	public JobInterested saveJobInterested(JobInterestedModel jobInterestedModel) {
		JobInterested jobInterested = null;
		if(StringUtils.isNotBlank(jobInterestedModel.getId())){
			jobInterested = jobInterestedRepository.findById(jobInterestedModel.getJobId());
		}else{
			jobInterested = new JobInterested();
		}
		
		jobInterested = jobInterested.setJobInterested(jobInterestedModel);
		return jobInterestedRepository.saveAndFlush(jobInterested);
	}

	@Override
	public List<JobInterested> findJobInterestedUserId(String Id) {
		List<JobInterested> jobInterested=new ArrayList<>(jobInterestedRepository.findJobByInterestedUserId(Id));
		return jobInterested;
	}

	@Override
	public List<JobInterested> showLowestBidAmount() {
		//List<JobInterested> jobInterested = new ArrayList<>(jobInterestedRepository.findLowestBidAmount());
		return null;
	}

	@Override
	public JobInterested findJobInterestDetailsByInterestUserId(String jobId, String interestUserId) {
		return jobInterestedRepository.findJobInterestDetailsByInterestUserId(jobId, interestUserId);
	}

}
