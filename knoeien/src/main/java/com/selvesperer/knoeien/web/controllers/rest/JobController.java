package com.selvesperer.knoeien.web.controllers.rest;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.JobService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.IdGenerator;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;
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
		return 1;
	}
	
	@RequestMapping(value = "/addjob", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> addJob(@RequestBody JobModel jobModel) {
		Job job = null;
		
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
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(new JobModel(job), LocalizationUtil.findLocalizedString((jobModel.isDraft())? "jobdraftsuccess.text" : "jobpostsuccess.text")),HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(job), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/jobDetailsById", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> jobDetailsById(@RequestParam(value = "jobID", required = true) String jobID) {
		RestResponse restResponse = null;
		try {
			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			Job job = jobService.findJobById(jobID);
			JobModel jobModel = new JobModel(job);

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobModel, LocalizationUtil.findLocalizedString("jobdetailssuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/uploadimage", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<RestResponse> uploadImage(HttpServletRequest request, @RequestParam(value = "file", required = true) CommonsMultipartFile[] file) throws Exception {
		RestResponse restResponse = null;
		String imagePath = "";
		try {
			String saveDirectory = ConfigurationUtil.config().getString("document.jobImageDoc");
			String imageName = IdGenerator.generate("jobimage");
			if (file != null && file.length > 0) {
				for (CommonsMultipartFile aFile : file) {
					if (!aFile.getOriginalFilename().equals("")) {
						String ext = aFile.getFileItem().getName().split("\\.")[1];
						saveDirectory = saveDirectory + imageName + "." + ext;
						imagePath = imageName + "." + ext;
						aFile.transferTo(new File(saveDirectory));
					}
				}
			}
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(imagePath, LocalizationUtil.findLocalizedString("imageuploadsuccess.text")),HttpStatus.OK);			
		} catch (Throwable t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findjobs", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> latestJob(@RequestParam(value="page", required=true) Integer page, @RequestParam(value="type", required=true) String type) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Job Info");
		try {
			if(page == null) page = 1;
			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			List<JobModel> jobs = jobService.findjobs(type, page, Constants.JOB_LATEST_SIZE);			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobs, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/getJobByAssignedUserId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getJobByAssignedUserId(@RequestParam(value="page", required=true) Integer page) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Job Using Assigned User id");
		
		try {
			if(page == null) page = 1;
			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			String assignedUserId = SecurityManager.getCurrentUserId();
			List<JobModel> jobs = jobService.findJobByAssignedUserId(assignedUserId, page, Constants.RESULT_LIMIT);
			
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(jobs, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getJobByCreatedUserId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getJobByCreatedUserId(@RequestParam(value="page", required=true) Integer page) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Job Using created User id");
		
		try {
			if(page == null) page = 1;
			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			String createdUserId = SecurityManager.getCurrentUserId();
			List<JobModel> jobs = jobService.findJobByCreatedUserId(createdUserId, page, Constants.RESULT_LIMIT);

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobs, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/postReviewMessageAndRating", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> postReviewMessageAndRating(@RequestBody JobModel jobModel) {
		Job job = null;

		try {
			RestResponse restResponse = null;

			if (StringUtils.isBlank(jobModel.getReviewMessage())) {
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
	
	@RequestMapping(value = "/postsavedjob", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> postSavedJob(@RequestParam(value = "jobID", required = true) String jobID) {
		RestResponse restResponse = null;
		try {
			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			jobService.updateJob(jobID);			

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("jobpostsuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findNearestjobs", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> findNearestjobs() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Find nearest Job Info");
		try {
			String userLatitude="23.6226398";
			String userLongitude="90.49979729999995";
			int page=1;
			JobService jobService = ApplicationBeanFactory.getBean(JobService.class);
			List<JobModel> jobs = jobService.findNearestjobs(userLatitude, userLongitude,page,Constants.JOB_LATEST_SIZE);
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobs, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}	
	
}
