package com.selvesperer.knoeien.config;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;

import com.selvesperer.knoeien.data.domain.User;

public class ApplicationPrincipal implements Serializable{

	private static final long serialVersionUID = 5631773323625356340L;
	
	private User principleUser;
	private AuthorizationInfo authorizationInfo;
	private AuthenticationInfo authenticationInfo;
	
	public ApplicationPrincipal(User principleUser,	AuthorizationInfo authorizationInfo){
		this.principleUser = principleUser;
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
