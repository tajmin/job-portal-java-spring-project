package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

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

import com.selvesperer.knoeien.data.domain.Job;
import com.selvesperer.knoeien.data.domain.Message;
import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.JobService;
import com.selvesperer.knoeien.service.NotificationService;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.JobModel;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;
import com.selvesperer.knoeien.web.controllers.model.NotificationModel;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

@Controller
@RequestMapping(value = "/api/v1/notification")
public class NotificationController extends AbstractController implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -9203528350007017705L;
	private static final Logger log = (Logger) LoggerFactory.getLogger(NotificationController.class);
	
	@RequestMapping(value = "/createNotification", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> createNotification(@RequestBody NotificationModel notificationModel) {
		Notification notification = null;

		try {
			RestResponse restResponse = null;

			if (StringUtils.isBlank(notificationModel.getJobId())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString(""));
			}
			
			if (restResponse != null) {
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
			}

			NotificationService notificationService=ApplicationBeanFactory.getBean(NotificationService.class);
			notification = notificationService.saveNotification(notificationModel);		
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("createnotificationsuccess.text")),HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(notification), HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "/getNotifications", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getNotifications(@RequestParam(value="page", required=true) Integer page) {

		RestResponse restResponse = null;
		if (log.isDebugEnabled())
			log.debug("Show Notification ");

		try {
			if(page == null) page = 1;
			NotificationService notificationService=ApplicationBeanFactory.getBean(NotificationService.class);
			//String id = SecurityManager.getCurrentUserId();
			String toUserId="100";
			List<NotificationModel> notificationModelList = notificationService.findNotifications(toUserId, page, Constants.NOTIFICATION_SIZE);		
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(notificationModelList, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
			
			
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/updateSeenNotification", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> updateSeenNotification(@RequestParam(value="id", required=true) String id) {
		try {
			NotificationService notificationService=ApplicationBeanFactory.getBean(NotificationService.class);
			//String id = SecurityManager.getCurrentUserId();
			//String id = "100";
			notificationService.saveSeenNotification(id);
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("")), HttpStatus.OK);
		} catch (Exception ex) {
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(""), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/deleteNotification", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> deleteNotification(@RequestParam(value="id", required=true) String id) {
		if(log.isDebugEnabled())
			log.debug("Delete notification");
		try {
			NotificationService notificationService=ApplicationBeanFactory.getBean(NotificationService.class);
			//String id = SecurityManager.getCurrentUserId();
			//String id = "100";
			notificationService.deleteNotification(id);
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("")), HttpStatus.OK);
		} catch (Exception ex) {
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(""), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/countHasSeeNotification", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> countHasSeeNotification() {

		RestResponse restResponse = null;
		if (log.isDebugEnabled())
			log.debug("Count has seen Notification ");

		try {
			
			NotificationService notificationService=ApplicationBeanFactory.getBean(NotificationService.class);
			//String id = SecurityManager.getCurrentUserId();
			String id="100";
			BigInteger bigInteger=notificationService.countHasSeeNotification(id);	
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(bigInteger, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
			
			
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/setAllHasSeenTrue", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> setAllHasSeenTrue() {
		if(log.isDebugEnabled())
			log.debug("Set All has seen True in notification");
		try {
			String id="100";
			NotificationService notificationService=ApplicationBeanFactory.getBean(NotificationService.class);
			notificationService.setAllHasSeenTrue(id);
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("")), HttpStatus.OK);
		} catch (Exception ex) {
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(""), HttpStatus.BAD_REQUEST);
	}
	
	
}
