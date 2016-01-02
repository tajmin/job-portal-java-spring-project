package com.selvesperer.knoeien.web.controllers.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.web.controllers.model.RestResponse;

@Controller
public abstract class AbstractController {
	@ResponseBody
	protected RestResponse convertToRestResponse(Object responseObject) {
		RestResponse response = new RestResponse();
		response.setResponse(responseObject);
		return response;
	}

}
