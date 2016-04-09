package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.omnifaces.util.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.data.domain.JobInterested;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.JobInterestedService;
import com.selvesperer.knoeien.service.JobOutService;
import com.selvesperer.knoeien.service.JobService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.JobInterestedModel;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;


@Controller
@RequestMapping(value = "/api/v1/jobOut")
public class JobOutController extends AbstractController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5883325019280616658L;
	
	
	
	@RequestMapping(value = "/getJobOutListById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> getJobOutListById(@RequestParam(value="jobId") String jobId) {
		RestResponse restResponse = null;
		try {
			//TODO
			JobOutService jobOutService = ApplicationBeanFactory.getBean(JobOutService.class);
			String Id=SecurityManager.getCurrentUserId();
			List<MessageModel> jobOutLists = jobOutService.showJobOutListsById(Id,jobId);
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(jobOutLists, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		}catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}
	
	
	
	
	
	

}
