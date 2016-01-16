package com.selvesperer.knoeien.config;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.security.SecurityManager;
import com.selvesperer.knoeien.service.UserService;

@Service("applicationRealm")
public class ApplicationRealm extends AuthorizingRealm {

	public static String APPLICATION_REALM_NAME = "ApplicationRealm";
	private static Logger log = (Logger)LoggerFactory.getLogger(ApplicationRealm.class);
	
	@Inject
	UserService userService;
	
	public ApplicationRealm() {
		APPLICATION_REALM_NAME = getName();
		setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    @Override
	public String getName() {
    	return APPLICATION_REALM_NAME;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        if (log.isDebugEnabled()) log.debug("gET Authentication Info for User : "+token.getUsername());
        User user =userService.findUserByEmail(token.getUsername());
        log.debug("emm... Returned user is " +user);
        if (user != null) {
        	if (log.isDebugEnabled())  log.debug("User found in system");
        	AuthorizationInfo authorization = SecurityManager.populateAuthorization(user);
        	ApplicationPrincipal principal = new ApplicationPrincipal(user, authorization);
        	SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(principal, user.getPassword(), getName());
        	SecurityManager.setSessionValues(user.getId(), user.getCompanyId(), user.getFullName());
            return authInfo;
        } else {
        	if (log.isDebugEnabled()) log.debug("User not found in system");
            return null;
        }
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
    	Object userObject = null;
    	if(principals.fromRealm(APPLICATION_REALM_NAME).iterator().hasNext())
    		userObject = principals.fromRealm(APPLICATION_REALM_NAME).iterator().next();
        if (userObject != null) {        	
        	ApplicationPrincipal principal = (ApplicationPrincipal)userObject ;
        	return principal.getAuthorizationInfo();
        } else {
            return null;
        }
    }	
}