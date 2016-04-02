package com.selvesperer.knoeien.web.controllers.faces;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;

@ManagedBean(name = "activationBean")
@RequestScoped
public class ActivationBean implements Serializable {

	private static final long serialVersionUID = -2646367043318201544L;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ActivationBean.class);
	
	private String key;
	
	@PostConstruct
	public void init() {
		if (log.isDebugEnabled()) log.debug("@Init");
	}

	public void initActivation() {
		if (log.isDebugEnabled()) log.debug("@Activation bean");		
		try {
			if(StringUtils.isBlank(getKey())) {
				Messages.addGlobalInfo("This is not a valid key. Please use valid key and try agian");
				return;
			}
			UserService userService = ApplicationBeanFactory.getBean(UserService.class);
			User user = userService.findUserByResetToken(this.getKey());
			if(user  == null) {
				Messages.addGlobalInfo("This is not a valid key.");
				return;
			}
			
			userService.activeUser(user);
			Faces.redirect("index.xhtml?faces-redirect=true&active=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
