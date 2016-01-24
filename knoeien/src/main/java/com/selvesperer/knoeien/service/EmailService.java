package com.selvesperer.knoeien.service;

import java.io.IOException;

import org.apache.commons.mail.EmailException;

import com.selvesperer.knoeien.emails.AbstractEmail;

public interface EmailService {

	public void sendEmail(AbstractEmail source) throws EmailException, IOException;
	
	//@author SHIFAT 
//	public void sendInvitationEmail() throws EmailException,IOException;
	
	
}
