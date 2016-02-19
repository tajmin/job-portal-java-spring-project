package com.selvesperer.knoeien.service.impl;

import java.io.IOException;
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

	
	// @author SHIFAT save User settings 
	@Override
	public void saveUserSetting(UserModel userModel, String id, String requestField) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findUserById(id);
		if(requestField.equals("epost")) {
			user.setePost(userModel.isePost());
		}
		
		if(requestField.equals("sms")){
			user.setSms(userModel.isSms());
		}
		
		//next
		if(requestField.equals("message")){
			user.setMessage(userModel.isMessage());
		}
		
		if(requestField.equals("reports")){
			user.setReports(userModel.isReports());
		}
		
		if(requestField.equals("assignedJob")){
			user.setAssignedJob(userModel.isAssignedJob());
		}
		
		if(requestField.equals("confirmJob")){
			user.setConfirmJob(userModel.isConfirmJob());
		}
		
		if(requestField.equals("hideAddress")){
			user.setHideAddress(userModel.isHideAddress());
		}
		
		if(requestField.equals("receiveUpdates")){
			user.setReceiveUpdates(userModel.isReceiveUpdates());
		}
		
	
		userRepository.saveAndFlush(user);			
	}
	
	// @author SHIFAT ends
	
	@Override
	public void updateUser(UserModel userModel, String id) {
		// TODO Auto-generated method stub
		User user = userRepository.findUserById(id);
		
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		user.setPhone(userModel.getPhone());
		user.setLocation(userModel.getLocation());
		userRepository.saveAndFlush(user);		
	}

	@Override
	public User showUserInfo(String id) {
		// TODO Auto-generated method stub
		User user = userRepository.findUserById(id);	
		
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		return user;
	}

	//@author SHIFAT edited for settings 
	@Override
	public User loadUserSetting(String id) {
		// TODO Auto-generated method stub
		User user=userRepository.findUserById(id);
		if (user == null) {
			throw new AuthenticationFailedException("error.usernotfound.text");
		}
		
		return user;
	}

	//@author SHIFAT Service 
	@Override
	public User facebookLogin(String fbName, String fbId) {
		// TODO Auto-generated method stub
		User fbUser = userRepository.findUserByEmail(fbId);
		if(fbUser == null){
			UserModel userModel=new UserModel();
			userModel.setEmail(fbId);
			userModel.setFirstName(fbName);
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
	
	
	//@author SHIFAT ends
}
