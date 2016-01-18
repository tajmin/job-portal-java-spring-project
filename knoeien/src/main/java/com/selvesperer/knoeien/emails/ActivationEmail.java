package com.selvesperer.knoeien.emails;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;

public class ActivationEmail extends AbstractEmail {
	
	private static final long serialVersionUID = -8876927665947138863L;

	private static Logger log = LoggerFactory.getLogger(ActivationEmail.class);
	
	private String key = "";
	public ActivationEmail(User user, String key) {
		super(user);
		this.key = key;
	}  

	@Override
	public HashMap<String, String> addProperties(HashMap<String, String> vals) {
		vals.put("contextPath", ConfigurationUtil.config().getString("application.context"));
		vals.put("activationKey", key);
		
		return vals;
	}

	@Override
	public String getSubject() {
		return "Signup!";
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
		String returnVal = "activation-email";
		return returnVal;
	}
}
