package com.selvesperer.knoeien.service;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

public interface UserService {
	
	public User findUserByEmail(String email);	

	public User findUserByResetToken(String key);
		
	public User findUserById(String id);

	public User saveUser(UserModel userModel);
	
	public User resetPassword(String userId, String password, String resetToken);
}
