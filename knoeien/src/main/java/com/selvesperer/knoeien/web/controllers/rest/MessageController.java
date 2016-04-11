package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
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

import com.selvesperer.knoeien.data.domain.Message;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.MessageService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.MessageModel;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;


@Controller
@RequestMapping(value = "/api/v1/message")
public class MessageController extends AbstractController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8556165488812300867L;
	private static final Logger log = (Logger) LoggerFactory.getLogger(MessageController.class);
	
	@RequestMapping(value = "/getMessageListByToUserId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getMessageListByToUserId(@RequestParam(value = "toUserId", required = true) String toUserId) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Message List");
		
		try {
			MessageService messageService = ApplicationBeanFactory.getBean(MessageService.class);			
			List<Message> message = messageService.showMessageByToUserId(toUserId);
			
			MessageModel messageModel = new MessageModel();
			List<MessageModel> messageModelList = messageModel.getMessageModelList(message);

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(messageModelList, LocalizationUtil.findLocalizedString("signupsuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getMessageListByJobId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getMessageListByJobId(@RequestParam(value = "jobId", required = true) String jobId, @RequestParam(value="page", required=true) Integer page) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Message List");
		
		try {
			MessageService messageService = ApplicationBeanFactory.getBean(MessageService.class);			
			List<MessageModel> message = messageService.showMessageByJobId(jobId, SecurityManager.getCurrentUserId(), page, Constants.MESSAGE_RESULT_LIMIT);
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(message, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/postMessage", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> postMessage(@RequestBody MessageModel messageModel) {
		Message message = null;
		
		
		try {
			RestResponse restResponse = null;

			if (StringUtils.isBlank(messageModel.getToUserId())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString(""));
			}
			
			if (StringUtils.isBlank(messageModel.getFromUserId())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString(""));
			}
			
			if (StringUtils.isBlank(messageModel.getJobId())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString(""));
			}
			
			if (StringUtils.isBlank(messageModel.getUserMessage())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString(""));
			}
			
			if (StringUtils.isBlank((CharSequence) messageModel.getSendDateTime())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString(""));
			}
			
			if (restResponse != null) {
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
			}

			MessageService messageService = ApplicationBeanFactory.getBean(MessageService.class);
			message = messageService.saveMessage(messageModel);		
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("jobpostsuccess.text")),HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(message), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/sendMessageToEmployeer", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> sendMessageToEmployeer(@RequestBody MessageModel messageModel) {
		Message message = null;
		RestResponse restResponse = null;
		try {
			
			messageModel.setFromUserId(SecurityManager.getCurrentUserId());
			//messageModel.getSendDateTime()
			MessageService messageService = ApplicationBeanFactory.getBean(MessageService.class);
			message = messageService.saveMessage(messageModel);		
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(message, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(message), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getMessageListByJobSeekerId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getMessageListByJobSeekerId(@RequestParam(value = "jobId", required = true) String jobId,@RequestParam(value = "jobSeekerId", required = true) String  jobSeekerId,@RequestParam(value="page", required=true) Integer page) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Message List for Job Seeker");
		
		try {
			//String jobId="46002544-3650-uuid-8f9c-8b0641fd1fea";
			//String jobSeekerId="46002537-4352-uuid-8a00-52ffe25330f6";
			//String jobPosterId="45975303-2462-uuid-8718-db3d5754b154";
			//int page=1;
			MessageService messageService = ApplicationBeanFactory.getBean(MessageService.class);	
			String jobPosterId=SecurityManager.getCurrentUserId();
			List<MessageModel> message = messageService.findAllMessagesBySeekerId(jobId,jobSeekerId,jobPosterId,page,Constants.MESSAGE_RESULT_LIMIT);
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(message, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}

}
