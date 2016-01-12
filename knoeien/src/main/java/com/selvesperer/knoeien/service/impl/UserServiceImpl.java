package com.selvesperer.knoeien.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.data.repository.UserRepository;
import com.selvesperer.knoeien.exception.SelvEspererException;
import com.selvesperer.knoeien.exception.UnauthorizedActionException;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.spring.utils.SpringBeanFactory;
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
		return userRepository.saveAndFlush(new User(userModel));
	}

	@Override
	public User findUserByResetToken(String key) {
		return userRepository.findUserByResetToken(key);
	}

	@Override
	public User resetPassword(String userId, String password, String resetToken) {
		User u = this.findUserById(userId);

		if (log.isInfoEnabled()) log.info("Reset Password for " + userId);
		if (u == null) throw new SelvEspererException("error.usernotfound.text");
		if (password == null || password.length() > 40 || password.length() < 6) throw new SelvEspererException("error.passwordnotvalid.text");

		if (!StringUtils.equals(u.getPasswordResetToken(), resetToken)) throw new UnauthorizedActionException("");

		StandardPasswordEncoder passwordEncoder = SpringBeanFactory.getBean(StandardPasswordEncoder.class);
		u.setPassword(passwordEncoder.encode(password));
		u.setPasswordResetToken("");
		u = userRepository.saveAndFlush(u);
		return u;
	}
}
