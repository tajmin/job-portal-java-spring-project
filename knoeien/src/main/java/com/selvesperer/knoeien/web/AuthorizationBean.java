package com.selvesperer.knoeien.web;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "authorizationBean")
@ApplicationScoped
public class AuthorizationBean implements Serializable {
	private static final long serialVersionUID = 873863236916169509L;

	private static final Logger log = (Logger) LoggerFactory.getLogger(AuthorizationBean.class);
	
	public boolean hasPermission(String permission) {
		if (!SecurityUtils.getSubject().isPermitted(permission)) {
			return false;
		} else
			return true;
	}
	
	public boolean canView() {
		return SecurityUtils.getSubject().isAuthenticated();
	}
}
