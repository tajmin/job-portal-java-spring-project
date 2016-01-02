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
import com.selvesperer.knoeien.security.SecurityUtil;
import com.selvesperer.knoeien.service.UserService;

@Service("JpaRealm")
public class JpaRealm extends AuthorizingRealm {

	public static String REALM_NAME = "JPARealm";
	private static Logger log = (Logger)LoggerFactory.getLogger(JpaRealm.class);
	
	@Inject
	UserService userService;
	
	public JpaRealm() {
		REALM_NAME = getName();
		setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    @Override
	public String getName() {
    	return REALM_NAME;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        if (log.isDebugEnabled()) log.debug("doGetAuthenticationInfo:user:"+token.getUsername());
        User user =userService.findUserByEmail(token.getUsername());
        log.debug("user returned from user service :"+user);
        if (user != null) {
        	if (log.isDebugEnabled()) 
        		log.debug("user found");
        	AuthorizationInfo authorization = SecurityUtil.populateAuthorization(user);
        	SelvEPrincipal principal = new SelvEPrincipal(user, authorization);
        	SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(principal, user.getPassword(), getName());
        	SecurityUtil.setSessionValues(user.getId(), user.getCompanyID());
            return authInfo;
        } else {
        	if (log.isDebugEnabled()) log.debug("user not found");
            return null;
        }
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
    	Object userObject = null;
    	if(principals.fromRealm(REALM_NAME).iterator().hasNext())
    		userObject = principals.fromRealm(REALM_NAME).iterator().next();
        if (userObject != null) {        	
        	SelvEPrincipal principal = (SelvEPrincipal)userObject ;
        	return principal.getAuthorizationInfo();
        } else {
            return null;
        }
    }	
}