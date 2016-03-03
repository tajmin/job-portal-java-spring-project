package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.selvesperer.knoeien.data.domain.Notification;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.NotificationService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.NotificationModel;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;

@Controller
@RequestMapping(value = "/api/v1/notification")
public class NotificationController extends AbstractController implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -9203528350007017705L;
	private static final Logger log = (Logger) LoggerFactory.getLogger(NotificationController.class);
	
	@RequestMapping(value = "/showNotification", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> showNotification() {

		RestResponse restResponse = null;
		if (log.isDebugEnabled())
			log.debug("Show Notification ");

		try {
			NotificationService notificationService=ApplicationBeanFactory.getBean(NotificationService.class);
			//String id = SecurityManager.getCurrentUserId();
			String id="1000";
			List<Notification> notification=notificationService.showNotification(id);
			NotificationModel notificationModel=new NotificationModel(notification.get(0));
			
			System.out.println(notificationModel);
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(notificationModel, LocalizationUtil.findLocalizedString("signupsuccess.text")),HttpStatus.OK);
			
			
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
			t.printStackTrace();
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);

	}
	
	
}
