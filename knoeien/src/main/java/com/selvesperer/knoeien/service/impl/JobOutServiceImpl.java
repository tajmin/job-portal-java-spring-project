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
import com.selvesperer.knoeien.data.repository.JobOutRepository;
import com.selvesperer.knoeien.service.JobInterestedService;
import com.selvesperer.knoeien.service.JobOutService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;

@Service("jobOutService")
@Scope(ScopeType.SINGLETON)
public class JobOutServiceImpl implements JobOutService{
	
	private static final Logger log = LoggerFactory.getLogger(JobInterestedServiceImpl.class);

	//TODO
	@Inject
	private JobOutRepository jobOutRepository;

	@Override
	public List<MessageModel> showJobOutListsById(String Id,String jobId) {
		// TODO Auto-generated method stub 
		List<MessageModel> jobOutLists= jobOutRepository.showJobOutListsById(Id,jobId);
		return jobOutLists;
	}

	

}
