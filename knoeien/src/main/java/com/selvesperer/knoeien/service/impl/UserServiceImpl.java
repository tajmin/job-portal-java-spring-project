package com.selvesperer.knoeien.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.data.repository.UserRepository;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.ScopeType;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

@Service("userService")
@Scope(ScopeType.SINGLETON)
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Inject
	private UserRepository userRepository;
	
	@Override
	public User findUserByEmail(String email) {
		return null;
	}

	@Override
	public User findUserById(String id) {
		return null;
	}
	
	@Override
	public User saveUser(UserModel userModel) {		
		return userRepository.saveAndFlush(new User(userModel));
	}
}
