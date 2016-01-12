package com.selvesperer.knoeien.web.controllers.faces;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.SpringBeanFactory;

@ManagedBean(name = "activationBean")
@RequestScoped
public class ActivationBean implements Serializable {

	private static final long serialVersionUID = -2646367043318201544L;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ActivationBean.class);
	
	private String key;
	private String message = "";
	
	@PostConstruct
	public void init() {
		if (log.isDebugEnabled()) log.debug("@Activation bean");
		
		if(StringUtils.isBlank(getKey())) {
			Messages.addGlobalInfo("This is not a valid key. Please use valid key and try agian");
			return;
		}	
		UserService userService = SpringBeanFactory.getBean(UserService.class);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
