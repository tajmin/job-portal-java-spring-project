package com.selvesperer.knoeien.config;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;

import com.selvesperer.knoeien.data.domain.User;

public class SelvEPrincipal implements Serializable{

	private static final long serialVersionUID = 5631773323625356340L;
	
	User principleUser;
	AuthorizationInfo authorizationInfo;
	AuthenticationInfo authenticationInfo;
	
	public SelvEPrincipal(User principleUser,	AuthorizationInfo authorizationInfo){
		this.principleUser = principleUser;
//		this.authenticationInfo = authenticationInfo;
		this.authorizationInfo = authorizationInfo;
		
	}
	public User getPrincipleUser() {
		return principleUser;
	}
	public void setPrincipleUser(User principleUser) {
		this.principleUser = principleUser;
	}
	public AuthorizationInfo getAuthorizationInfo() {
		return authorizationInfo;
	}
	public void setAuthorizationInfo(AuthorizationInfo authorizationInfo) {
		this.authorizationInfo = authorizationInfo;
	}
	public AuthenticationInfo getAuthenticationInfo() {
		return authenticationInfo;
	}
	public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
		this.authenticationInfo = authenticationInfo;
	}
	

}
