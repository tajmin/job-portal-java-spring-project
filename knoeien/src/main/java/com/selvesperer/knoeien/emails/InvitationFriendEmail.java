package com.selvesperer.knoeien.emails;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;

public class InvitationFriendEmail extends AbstractEmail {
	
	private static final long serialVersionUID = -8876927665947138863L;

	private static Logger log = LoggerFactory.getLogger(InvitationFriendEmail.class);
	
	public InvitationFriendEmail(User user) {
		super(user);
	}  

	@Override
	public HashMap<String, String> addProperties(HashMap<String, String> vals) {
		vals.put("contextPath", ConfigurationUtil.config().getString("application.context"));
		return vals;
	}

	@Override
	public String getSubject() {
		return "Invitation!";
	}


	@Override
	public String getTitle() {
		return "";
	}


	@Override
	public HashMap<String, Object> addPropertiesAsObject( HashMap<String, Object> vals ) {
		return vals;
	}
	
	@Override
	public String getEmailTemplateName() {
		String returnVal = "invitation-email";
		return returnVal;
	}
}
