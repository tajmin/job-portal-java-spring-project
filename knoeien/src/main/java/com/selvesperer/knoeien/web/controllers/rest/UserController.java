package com.selvesperer.knoeien.web.controllers.rest;

import java.io.Serializable;
import java.util.UUID;

import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.emails.ActivationEmail;
import com.selvesperer.knoeien.service.EmailService;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.SpringBeanFactory;
import com.selvesperer.knoeien.web.controllers.model.RestResponse;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

@Controller
@RequestMapping(value = "/api/v1/user")
public class UserController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -3609323082017170597L;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Integer getData() {
		System.out.println("hay i amd here in get data for you.................................");
		return 1;
	}
	
	@RequestMapping(method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> addUser(@RequestBody UserModel userModel) {		
		User user = null;
		try {
			UserService userService = SpringBeanFactory.getBean(UserService.class);
			
			String token = UUID.randomUUID().toString(); 
			userModel.setPasswordResetToken(token);
			user = userService.saveUser(userModel);
			EmailService emailService = SpringBeanFactory.getBean(EmailService.class);
			emailService.sendEmail(new ActivationEmail(user, token));
		} catch (Exception ex) {
			Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestResponse(user), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST , produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> login(@RequestBody UserModel userModel) {
		System.out.println(" i dont know what i am doing ");
		
		return null;
	}
}
