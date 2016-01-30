package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.JobService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;


@Controller
@RequestMapping(value = "/api/v1/job")
public class JobController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -3671631965054846154L;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(JobController.class);

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Integer getData() {
		System.out.println("hay i amd here in get data for you.................................");
		return 1;
	}
	
	@RequestMapping(value = "/addjob", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> addJob(@RequestBody JobModel jobModel) {
		Job job = null;
		System.out.println(jobModel.getDescription());
		
		try {
			RestResponse restResponse = null;

			if (StringUtils.isBlank(jobModel.getDescription())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString(""));
			}

			if (restResponse != null) {
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
			}

			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			job = jobService.saveJob(jobModel);		
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("jobpostsuccess.text")),HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(job), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/showjob", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> showJob() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Job Info");		
		try {
			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			String id = "90000-244-344";
			Job job = jobService.showJobInfo(id);
			JobModel jobModel = new JobModel(job);

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobModel, LocalizationUtil.findLocalizedString("signupsuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
}