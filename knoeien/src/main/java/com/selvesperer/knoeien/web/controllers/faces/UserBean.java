package com.selvesperer.knoeien.web.controllers.faces;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.service.UserService;
import com.selvesperer.knoeien.spring.utils.SpringBeanFactory;
import com.selvesperer.knoeien.web.controllers.model.UserModel;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 6526515012691012844L;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(UserBean.class);

	@PostConstruct
	public void init() {
		if (log.isDebugEnabled()) log.debug("Comment bean post constructor.");
	}

	public void saveUser() {
		try {
			UserService userService = SpringBeanFactory.getBean(UserService.class);
			UserModel userModel = new UserModel();
			userModel.setFirstName("Test f name");
			userModel.setLastName("Test l name");
			userModel.setPreferredName("test p name");
			userModel.setEmail(System.currentTimeMillis() + "test@selvesperer.com");
			userModel.setPassword("TestPassword");
			userService.saveUser(userModel);
			Messages.addGlobalInfo("The user has been saved successfully!");
		} catch (Exception ex) {
			Messages.addGlobalError(ex.getMessage());
		}
	}
}
