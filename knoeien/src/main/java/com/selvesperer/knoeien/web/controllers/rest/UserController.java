package com.selvesperer.knoeien.web.controllers.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Messages;
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

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.emails.ActivationEmail;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.service.EmailService;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
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

	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> signup(@RequestBody UserModel userModel) {
		User user = null;
		try {
			RestResponse restResponse = null;
			if (StringUtils.isBlank(userModel.getEmail())) {
				restResponse = convertToRestBadResponse("",	LocalizationUtil.findLocalizedString("error.emptyemail.text"));
			}

			if (StringUtils.isBlank(userModel.getPassword())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString("error.emptypassword.text"));
			}

			if (!StringUtils.equals(userModel.getPassword(), userModel.getConfirmPassword())) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString("error.passwordandconfirmpasswordnotmatch.text"));
			}

			if (restResponse != null) {
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
			}

			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			String token = UUID.randomUUID().toString();
			userModel.setPasswordResetToken(token);
			user = userService.saveUser(userModel);
			EmailService emailService = ApplicationBeanFactory.getBean(EmailService.class);
			emailService.sendEmail(new ActivationEmail(user, token));

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("signupsuccess.text")),HttpStatus.OK);
		} catch (Exception ex) {
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(user), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> addUser(@RequestBody UserModel userModel) {
		User user = null;
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);

			String token = UUID.randomUUID().toString();
			userModel.setPasswordResetToken(token);
			user = userService.saveUser(userModel);
			EmailService emailService = ApplicationBeanFactory.getBean(EmailService.class);
			emailService.sendEmail(new ActivationEmail(user, token));
		} catch (Exception ex) {
			Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(user), HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> login(@RequestBody Map<String, String> requestObject, HttpServletRequest request, HttpServletResponse response) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("login User");
		String username = requestObject.get("username");
		String password = requestObject.get("password");
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			User u = userService.login(username, password);

			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute(Constants.CURRENT_USER_ID, u.getEmail());

			HashMap<String, String> uData = new HashMap<>();
			uData.put(Constants.CURRENT_USER_ID, u.getEmail());
			uData.put(Constants.CURRENT_USER_NAME, u.getFullName());

			response.sendRedirect(request.getContextPath() + "/index.xhtml");
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(uData), HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (log.isDebugEnabled()) log.debug("Logging out");
	
		try {
			HttpSession httpSession = request.getSession(true);
			httpSession.invalidate();
			response.sendRedirect(request.getContextPath() + "/index.xhtml");
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("Exceptions happned! " + ex.getMessage());
			}
		}
		return "logout successfull";
	}
	
	@RequestMapping(value = "/sendChangePasswordEmail", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> sendChangePasswordEmail(@RequestParam(value="email", required=true) String email) {
		RestResponse restResponse = null;
		try {			
			if (StringUtils.isBlank(email)) {
				restResponse = convertToRestBadResponse("",	LocalizationUtil.findLocalizedString("error.emptyemail.text"));
			}

			if (restResponse != null) {
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
			}
			
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			userService.sendForgetPasswordEmail(email);
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, "Please check your email and follow the instructions"),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> resetPassword(@RequestBody Map<String, String> requestObject, HttpServletRequest request, HttpServletResponse response) {
		RestResponse restResponse = null;
		try {
			if (log.isDebugEnabled()) log.debug("login User");
			String password = requestObject.get("password");
			String confirmPassword = requestObject.get("confirmPassword");
			String token = requestObject.get("passwordResetToken");
			
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);		

			if (StringUtils.isBlank(password)) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString("error.emptypassword.text"));
			}

			if (!StringUtils.equals(password, confirmPassword)) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString("error.passwordandconfirmpasswordnotmatch.text"));
			}

			if (restResponse != null) {
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
			}
			
			userService.resetPassword(password, token);
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(null, "Your password has been change successfully, Please click for login"),HttpStatus.OK);			
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
	}
}
