package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
import java.util.List;

import org.omnifaces.util.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.data.domain.JobInterested;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.JobInterestedService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;


@Controller
@RequestMapping(value = "/api/v1/jobInterested")
public class JobInterestedController extends AbstractController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5883325019280616658L;
	
	@RequestMapping(value = "/saveJobInterest", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> saveJobInterest(@RequestBody JobInterestedModel jobInterestedModel) {		
		JobInterested jobInterested  = null;
		try {
			JobInterestedService jobInterestedService = ApplicationBeanFactory.getBean(JobInterestedService.class);
			jobInterestedModel.setJobInterestedUserId(SecurityManager.getCurrentUserId());
			jobInterested = jobInterestedService.saveJobInterested(jobInterestedModel);
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(new JobInterestedModel(jobInterested), LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (Exception ex) {
			Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(jobInterested), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getJobInterestDetailsByInterestUserId", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> getJobInterestDetailsByInterestUserId(@RequestParam(value = "jobID", required = true) String jobID) {		
		JobInterested jobInterested  = null;
		try {
			JobInterestedService jobInterestedService = ApplicationBeanFactory.getBean(JobInterestedService.class);
			jobInterested = jobInterestedService.findJobInterestDetailsByInterestUserId(jobID, SecurityManager.getCurrentUserId());
			JobInterestedModel jobInterestedModel = null;
			if(jobInterested != null){
				jobInterestedModel = new JobInterestedModel(jobInterested);
			}
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobInterestedModel, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (Exception ex) {
			Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(jobInterested), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllJobInterestedByUserId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getAllJobInterestedByUserId(@RequestParam(value="page", required=true) Integer page) {
		RestResponse restResponse = null;
		try {
			JobInterestedService jobInterestedService = ApplicationBeanFactory.getBean(JobInterestedService.class);
			String userId = SecurityManager.getCurrentUserId();
			List<JobModel> jobModel = jobInterestedService.findAllJobInterestedByUserId(userId, page, Constants.JOB_INTEREST_SIZE);
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobModel, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getLowestBidAmount", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getLowestBidAmount(@RequestParam(value = "jobID", required = true) String jobID) {
		RestResponse restResponse = null;
		try {
			JobInterestedService jobInterestedService = ApplicationBeanFactory.getBean(JobInterestedService.class);
			double lowestBid = jobInterestedService.findLowestBidAmount(jobID);
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(lowestBid, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	
	

}
