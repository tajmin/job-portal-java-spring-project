package com.selvesperer.knoeien.web.controllers.rest;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.web.controllers.model.RestMessage;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;

@Controller
public abstract class AbstractController {
	
	@ResponseBody
	protected RestResponse convertToRestGoodResponse(Object responseObject) {
		RestResponse response = new RestResponse();
		response.setResponse(responseObject);
		return response;
	}
	
	@ResponseBody
	protected RestResponse convertToRestGoodResponse(Object responseObject, String message) {
		RestResponse response = new RestResponse();
		response.setResponse(responseObject);
		response.setMessage(message);
		return response;
	}
	
	@ResponseBody
	protected RestResponse convertToRestBadResponse(String field, String message) {
		RestResponse restResponse = new RestResponse();
		RestMessage restMessage = new RestMessage();
		ArrayList<RestMessage> restMessages = new ArrayList<RestMessage>();
		
		restResponse.setSuccess(false);
		restMessage.setField(field);
		restMessage.setMessage(message);
		restMessage.setMessage(message);		
		restMessages.add(restMessage);		
		restResponse.setMessages(restMessages);
		return restResponse;
	}
	
	@ResponseBody
	protected RestResponse convertToRestBadResponse(RestResponse restResponse, String field, String message) {
		if(restResponse == null) {
			restResponse = new RestResponse();
			restResponse.setSuccess(false);
		}
			
		if(restResponse.getMessages() == null) restResponse.setMessages(new ArrayList<RestMessage>());
		RestMessage restMessage = new RestMessage();
		
		restMessage.setField(field);
		restMessage.setMessage(message);
		restMessage.setMessage(message);		
		
		restResponse.getMessages().add(restMessage);
		return restResponse;
	}
}
