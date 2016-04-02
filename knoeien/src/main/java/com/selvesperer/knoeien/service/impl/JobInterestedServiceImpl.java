package com.selvesperer.knoeien.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.JobInterested;
import com.selvesperer.knoeien.data.repository.JobInterestedRepository;
import com.selvesperer.knoeien.service.JobInterestedService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;
import com.selvesperer.knoeien.web.controllers.model.JobModel;

@Service("jobInterestedService")
@Scope(ScopeType.SINGLETON)
public class JobInterestedServiceImpl implements JobInterestedService{
	
	private static final Logger log = LoggerFactory.getLogger(JobInterestedServiceImpl.class);

	@Inject
	private JobInterestedRepository jobInterestedRepository;
	
	@Override
	public JobInterested saveJobInterested(JobInterestedModel jobInterestedModel) {
		JobInterested jobInterested = null;
		if(StringUtils.isNotBlank(jobInterestedModel.getId())){
			jobInterested = jobInterestedRepository.findJobInterestDetailsByInterestUserId(jobInterestedModel.getJobId(), jobInterestedModel.getJobInterestedUserId());
		}else{
			jobInterested = new JobInterested();
		}
		
		jobInterested = jobInterested.setJobInterested(jobInterestedModel);
		return jobInterestedRepository.saveAndFlush(jobInterested);
	}

	@Override
	public List<JobModel> findAllJobByInterestedUserId(String userId, int page, int limit) {
		List<JobModel> jobInterested= jobInterestedRepository.findAllJobByInterestedUserId(userId, page, limit);
		return jobInterested;
	}

	@Override
	public double findLowestBidAmount(String jobId) {
		return jobInterestedRepository.findLowestBid(jobId);
	}

	@Override
	public JobInterested findJobInterestDetailsByInterestUserId(String jobId, String interestUserId) {
		return jobInterestedRepository.findJobInterestDetailsByInterestUserId(jobId, interestUserId);
	}

}
