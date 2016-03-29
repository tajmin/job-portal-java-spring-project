package com.selvesperer.knoeien.web.controllers.rest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.shiro.SecurityUtils;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.emails.ActivationEmail;
import com.selvesperer.knoeien.emails.InvitationFriendEmail;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.EmailService;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.Constants;
import com.selvesperer.knoeien.utils.IdGenerator;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;
import com.selvesperer.knoeien.web.controllers.model.EmployeerModel;
import com.selvesperer.knoeien.web.controllers.model.PaymentInfoModel;
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

	@RequestMapping(value = "/invite", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> invite(@RequestParam(value="email", required=true) String email) throws EmailException, IOException  {	
		User user=null;		
		try {
			RestResponse restResponse = null;
			
			if (StringUtils.isBlank(email)) {
				restResponse = convertToRestBadResponse("",	LocalizationUtil.findLocalizedString("error.emptyemail.text"));
			}

			if (restResponse != null) {
				return new ResponseEntity<RestResponse>(restResponse, HttpStatus.OK);
			}
		
			user = new User();
			user.setEmail(email);
			EmailService emailService = ApplicationBeanFactory.getBean(EmailService.class);
			emailService.sendEmail(new InvitationFriendEmail(user));
			
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("invitationsuccess.text")),HttpStatus.OK);
		} catch (Exception ex) {
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(user), HttpStatus.BAD_REQUEST);
	}
	 
	@RequestMapping(value = "/saveUserSetting", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> saveUserSetting(@RequestBody UserModel userModel, @RequestParam(value="name", required=true) String name) {
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			String id = SecurityManager.getCurrentUserId();
			userService.saveUserSetting(userModel, id, name);
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("settingssaved.text")), HttpStatus.OK);
		} catch (Exception ex) {
			// Messages.addGlobalError(ex.getMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(userModel), HttpStatus.BAD_REQUEST);
	}
	
 
	@RequestMapping(value = "/loadUserSetting", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> loadUserSetting() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("User Setting ");		
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			String id = SecurityManager.getCurrentUserId();			
			User user = userService.loadUserSetting(id);
			UserModel userModel = new UserModel(user);			
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(userModel, LocalizationUtil.findLocalizedString("")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
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
			
			if(userModel.getDateOfBirth() == null) {
				restResponse = convertToRestBadResponse(restResponse, "", LocalizationUtil.findLocalizedString("error.emptydateofbirth.text"));
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
			HashMap<String, String> uData = new HashMap<>();
			uData.put(Constants.CURRENT_USER_ID, u.getEmail());
			uData.put(Constants.CURRENT_USER_NAME, u.getFullName());
			
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(uData, LocalizationUtil.findLocalizedString("signupsuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", LocalizationUtil.findLocalizedString("error.usernameandpasswordnotmatch.text"));
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (log.isDebugEnabled()) log.debug("Logging out");
		RestResponse restResponse = null;
		try {
			SecurityUtils.getSubject().logout();
			request.getSession().invalidate();
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse("", "logout success"),HttpStatus.OK);
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("Exceptions happned! " + ex.getMessage());
			}
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
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
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> editProfile(@RequestBody UserModel userModel) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Edit Profile");		
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			String id = SecurityManager.getCurrentUserId();
			userService.updateUser(userModel, id);
						
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(null, LocalizationUtil.findLocalizedString("updatesuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/profileInfo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> profileInfo() {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("Profile Info");		
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			String id = SecurityManager.getCurrentUserId();
			User user = userService.showUserInfo(id);
			UserModel userModel = new UserModel(user);

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(userModel, LocalizationUtil.findLocalizedString("updatesuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserPaymentInfo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getUserPaymentInfo() {
		RestResponse restResponse = null;
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			String id = SecurityManager.getCurrentUserId();
			User user = userService.showUserInfo(id);
			PaymentInfoModel paymentInfoModel = new PaymentInfoModel(user);

			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(paymentInfoModel, LocalizationUtil.findLocalizedString("updatesuccess.text")),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveUserPaymentInfo", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> saveUserPaymentInfo(@RequestBody PaymentInfoModel paymentInfoModel) {
		RestResponse restResponse = null;
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			String id = SecurityManager.getCurrentUserId();
			userService.updatePaymentInfo(paymentInfoModel, id);						
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(true, LocalizationUtil.findLocalizedString("updatesuccess.text")),HttpStatus.OK);
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
			String saveDirectory = ConfigurationUtil.config().getString("document.userImageDoc");
			String imageName = IdGenerator.generate("userimage");
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
	
	
	@RequestMapping(value = "/sendVerificationCode", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> sendVerificationCode(@RequestParam(value = "mobileNumber", required = true) String mobileNumber, HttpServletRequest request) throws EmailException, IOException {
		RestResponse restResponse = null;
		UserService userService = ApplicationBeanFactory.getBean(UserService.class);
		final String username = ConfigurationUtil.config().getString("sms.username");
		final String password = ConfigurationUtil.config().getString("sms.password");
		final String sender = ConfigurationUtil.config().getString("sms.sender");
		final String api = ConfigurationUtil.config().getString("sms.api");
		try {
			long timeCalculate = System.nanoTime();
			double randomCalculate = Math.random() * 1000;
			long mixingTimeAndRand = (long) (timeCalculate * randomCalculate);
			String s1 = mixingTimeAndRand + "";
			String verificationCode = s1.substring(0, 6);
			String message = verificationCode;
			String receiver = mobileNumber.trim();
			if(StringUtils.isNotBlank(receiver)){
				if(!StringUtils.contains(receiver, "+")){
					receiver = "+" + receiver;
				}
			}
			boolean status;
			String xml = "<?xml version=\"1.0\"?><SESSION><CLIENT>" + username + "</CLIENT><PW>" + password
					+ "</PW><RCPREQ>Y</RCPREQ><MSGLST><MSG><TEXT>" + message + "</TEXT><SND>" + sender + "</SND><RCV>"
					+ receiver + "</RCV></MSG></MSGLST></SESSION>";

			String gatewayResponse = userService.sendVerificationCode(api, xml);
			String search="fail";
			if(gatewayResponse.toLowerCase().indexOf(search)!=-1) {
				status=false;
			} else {
				status=true;
			}
			
			request.getSession().setAttribute(Constants.VERIFICATION_CODE, verificationCode);
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(status, ""), HttpStatus.OK);
		} catch (Exception ex) {
			restResponse = convertToRestBadResponse("", ex.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(restResponse), HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "/verifyNumber", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<RestResponse> verifyNumber(@RequestParam(value = "verificationCode", required = true) String verificationCode, HttpServletRequest request) throws IOException {
		RestResponse restResponse = null;
		try {
			String storedCode=(String) request.getSession().getAttribute(Constants.VERIFICATION_CODE);
			boolean status =false;
			if(storedCode.equals(verificationCode)){
				status = true;
			}			
			return new ResponseEntity<RestResponse>(convertToRestGoodResponse(status, ""), HttpStatus.OK);
		} catch (Exception ex) {
			restResponse = convertToRestBadResponse("", ex.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>(convertToRestGoodResponse(restResponse), HttpStatus.BAD_REQUEST);
	}
	


	@RequestMapping(value = "/getUserByJobId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getUserByJobId(@RequestParam(value = "jobID", required = true) String jobId) {
		RestResponse restResponse = null;
		if (log.isDebugEnabled()) log.debug("getUserByJobId");		
		try {
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			User user = userService.findUserByJobId(jobId);
			EmployeerModel employeerModel = new EmployeerModel(user);
			return new ResponseEntity<RestResponse>( convertToRestGoodResponse(employeerModel, ""),HttpStatus.OK);
		} catch (AuthenticationFailedException t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		} catch (Exception t) {
			restResponse = convertToRestBadResponse("", t.getLocalizedMessage());
		}
		return new ResponseEntity<RestResponse>( restResponse, HttpStatus.OK);
	}


}
