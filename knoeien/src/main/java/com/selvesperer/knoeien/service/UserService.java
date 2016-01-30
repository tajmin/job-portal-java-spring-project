package com.selvesperer.knoeien.service;

import java.io.IOException;

import org.apache.commons.mail.EmailException;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

public interface UserService {
	
	public User findUserByEmail(String email);	

	public User findUserByResetToken(String key);
		
	public User findUserById(String id);

	public User saveUser(UserModel userModel);
	
	public User resetPassword(String password, String resetToken);
	
	public User activeUser(User user);
	
	public User login(String username, String password);
	
	public void sendForgetPasswordEmail(String email) throws EmailException, IOException;
	
	public void saveUserSetting(UserModel userModel,String id);
	
	public void updateUser(UserModel userModel, String id);
	
	public User showUserInfo(String id);
	
}
