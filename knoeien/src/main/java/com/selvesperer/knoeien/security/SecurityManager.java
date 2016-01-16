
package com.selvesperer.knoeien.security;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.data.domain.User;
import com.selvesperer.knoeien.utils.Constants;

public class SecurityManager {

	private static final Logger log = (Logger) LoggerFactory.getLogger(SecurityManager.class);


	public static String getCurrentUserId() {
		try {
			return (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER_ID).toString();
		} catch (NullPointerException npe) {
			return null;
		} catch (UnavailableSecurityManagerException nse) {
			return null;
		}
		catch (Exception ex) {
			return null;
		}
	}

	public static String getCurrentCompanyId() {
		try {
			return (String)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_COMPANY_ID);
		} catch (NullPointerException npe) {
			return null;
		} catch (UnavailableSecurityManagerException nse) {
			return null;
		}catch (Exception ex) {
			return null;
		}
	}

	public static boolean isAuthenticated() {
		try {
			if (SecurityUtils.getSubject() != null)
				return SecurityUtils.getSubject().isAuthenticated();
			else
				return false;
		} catch (NullPointerException npe) {
			return false;
		} catch (UnavailableSecurityManagerException nse) {
			return false;
		}catch (Exception ex) {
			return false;
		}
		
	}


	public static void setSessionValues(String userID, String companyID) {
		try {
			SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_USER_ID, userID);
			SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_COMPANY_ID, companyID);
		} catch (Exception e) {
			log.error("Exception in setSessionValues:",e);
		}
	}

	public static void clearAuthenticationSubject() {
		SecurityUtils.getSubject().logout();
	}

	public static boolean hasRole(String roleName) {
		return SecurityUtils.getSubject().hasRole(roleName);
	}
	
	public static AuthorizationInfo populateAuthorization(User user) {
		
    	if (log.isDebugEnabled()) log.debug("Creating a security role for user: "+user.getFullName());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        HashSet<String> hs = new HashSet<String>();
        
       info.addStringPermissions(hs);
        if (log.isDebugEnabled()) log.debug("User Permissions:"+hs.toString());
        return info;

	}

	private static void addPermissions(HashSet<String> hs, String perms) {
		if (perms != null) hs.addAll(Arrays.asList(perms.split(",")));
	}
}