package com.selvesperer.knoeien.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.data.repository.UserRepository;
import com.selvesperer.knoeien.emails.ForgetPasswordEmail;
import com.selvesperer.knoeien.exception.AuthenticationFailedException;
import com.selvesperer.knoeien.exception.SelvEspererException;
import com.selvesperer.knoeien.exception.UnauthorizedActionException;
import com.selvesperer.knoeien.service.EmailService;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.web.controllers.model.PaymentInfoModel;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

@Service("userService")
@Scope(ScopeType.SINGLETON)
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Inject
	private UserRepository userRepository;

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User findUserById(String id) {
		return userRepository.findUserById(id);
	}

	@Override
	public User saveUser(UserModel userModel) {		
		User user = new User(userModel);
		StandardPasswordEncoder encoder = ApplicationBeanFactory.getBean(StandardPasswordEncoder.class);
		user.setPassword(encoder.encode(userModel.getPassword()));
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User findUserByResetToken(String key) {
		return userRepository.findUserByResetToken(key);
	}

	@Override
	public User resetPassword(String password, String token) {
		User u = this.findUserByResetToken(token);

		if (log.isInfoEnabled()) log.info("Reset Password for " + token);
		if  (u == null) throw new SelvEspererException("error.usernotfound.text");
		
		if (!StringUtils.equals(u.getPasswordResetToken(), token))
			throw new UnauthorizedActionException("");

		StandardPasswordEncoder passwordEncoder = ApplicationBeanFactory.getBean(StandardPasswordEncoder.class);
		u.setPassword(passwordEncoder.encode(password));
		u.setPasswordResetToken("");
		u = userRepository.saveAndFlush(u);
		return u;
	}

	public User activeUser(User user) {
		user.setActive(true);
		user = userRepository.saveAndFlush(user);
		return user;
	}

	public User login(String username, String password) {
		User user = userRepository.findUserByEmail(username);
		if (user == null) {
			throw new AuthenticationFailedException("error.usernameandpasswordnotmatch.text");
		}

		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(username, password, false));

		if (!subject.isAuthenticated()) {
			SecurityUtils.getSubject().logout();
		}
		return user;
	}

	public void sendForgetPasswordEmail(String email) throws EmailException, IOException {
		User user = userRepository.findUserByEmail(email);
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}

		String token = UUID.randomUUID().toString();
		user.setPasswordResetToken(token);
		user = userRepository.saveAndFlush(user);
		EmailService emailService = ApplicationBeanFactory.getBean(EmailService.class);
		emailService.sendEmail(new ForgetPasswordEmail(user, token));
	}

	@Override
	public void saveUserSetting(UserModel userModel, String id, String requestField) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findUserById(id);
		if(requestField.equals("mobileSms")) {
			user.setMobileSms(userModel.isMobileSms());
		}
		
		if(requestField.equals("pushNotification")){
			user.setPushNotification(userModel.isPushNotification());
		}
		
		//next
		if(requestField.equals("ePost")){
			user.setePost(userModel.isePost());
		}
		
		if(requestField.equals("receiveUpdates")){
			user.setReceiveUpdates(userModel.isReceiveUpdates());
		}
		
		if(requestField.equals("receiveMessage")){
			user.setReceiveMessage(userModel.isReceiveMessage());
		}
		
		if(requestField.equals("jobPost")){
			user.setJobPost(userModel.isJobPost());
		}
		
		if(requestField.equals("jobInterest")){
			user.setJobInterest(userModel.isJobInterest());
		}
		
		if(requestField.equals("jobAssigned")){
			user.setJobAssigned(userModel.isJobAssigned());
		}
		
		if(requestField.equals("completeJob")){
			user.setCompleteJob(userModel.isCompleteJob());
		}
		
		userRepository.saveAndFlush(user);			
	}
	
	public void updateUser(UserModel userModel, String id) {
		User user = userRepository.findUserById(id);
		
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		user.setPhone(userModel.getPhone());
		user.setLocation(userModel.getLocation());
		user.setPostcode(userModel.getPostcode());
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setAddress(userModel.getAddress());
		user.setHouseNumber(userModel.getHouseNumber());
		user.setAboutMe(userModel.getAboutMe());
		user.setBackgroundImageUrl(userModel.getBackgroundImageUrl());
		user.setPromoCode(userModel.getPromoCode());
		user.setCardNumber(userModel.getCardNumber());
		user.setCardholderName(userModel.getCardholderName());
		user.setAccountNumber(userModel.getAccountNumber());
		user.setCardMonth(userModel.getCardMonth());
		user.setCardYear(userModel.getCardYear());
		user.setCvc(userModel.getCvc());
	
		userRepository.saveAndFlush(user);		
	}
	
	public void updatePaymentInfo(PaymentInfoModel paymentInfoModel, String id) {
		User user = userRepository.findUserById(id);
		
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		user.setCardNumber(paymentInfoModel.getCardNumber());
		user.setCardholderName(paymentInfoModel.getCardholderName());
		user.setAccountNumber(paymentInfoModel.getAccountNumber());
		user.setCardMonth(paymentInfoModel.getCardMonth());
		user.setCardYear(paymentInfoModel.getCardYear());
		user.setCvc(paymentInfoModel.getCvc());
	
		userRepository.saveAndFlush(user);		
	}


	public User showUserInfo(String id) {
		User user = userRepository.findUserById(id);	
		
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		return user;
	}
	 
	@Override
	public User loadUserSetting(String id) {
		User user=userRepository.findUserById(id);
		
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		return user;
	}

	@Override
	public User facebookLogin(String fbName, String fbId) {
		// TODO Auto-generated method stub
		User fbUser = userRepository.findUserByEmail(fbId);
		if(fbUser == null){
			UserModel userModel=new UserModel();
			userModel.setEmail(fbId);
			
			//@author SHIFAT splitting firstname and last name
			//Edited 20 Feb 2016
			String fbFirstName="";
			String fbLastName="";
			if(fbName.split("\\w+").length>1) {
				fbFirstName=fbName.substring(0,fbName.lastIndexOf(" "));
				fbLastName=fbName.substring(fbName.lastIndexOf(" ")+1);
			} else {
				fbFirstName=fbName;
				fbLastName="";
			}
			
			userModel.setFirstName(fbFirstName);
			userModel.setLastName(fbLastName);
			
			//Ends
			userModel.setPassword("123456789");
			userModel.setAdmin(false);
			userModel.setLocale("bd");
			fbUser = new User(userModel);
			
			StandardPasswordEncoder encoder = ApplicationBeanFactory.getBean(StandardPasswordEncoder.class);
			fbUser.setPassword(encoder.encode(userModel.getPassword()));
			
			userRepository.saveAndFlush(fbUser);
		}
		
		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(fbId,"123456789", false));
		
		return fbUser;
	}

	@Override
	public String sendVerificationCode(String targetURL, String xml) {
	
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(xml.getBytes().length));

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.writeBytes (xml);
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
	
}
