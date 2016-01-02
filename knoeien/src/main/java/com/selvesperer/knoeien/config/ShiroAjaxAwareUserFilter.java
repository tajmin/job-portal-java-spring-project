package com.selvesperer.knoeien.config;
import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroAjaxAwareUserFilter extends UserFilter{
	
	private static Logger log = (Logger)LoggerFactory.getLogger(ShiroAjaxAwareUserFilter.class);
	private static final String FACES_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>";
 
	@Override
    protected void redirectToLogin(ServletRequest req, ServletResponse res) throws IOException {
    	if (log.isDebugEnabled()) log.debug("auto-login attempt from shiro");   
        HttpServletRequest request = (HttpServletRequest) req;
    	try {
	    	// Do logic here.
    	} catch (LockedAccountException le) {
    		super.redirectToLogin(req, res);

    	}
    }
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		boolean access= super.isAccessAllowed(request, response, mappedValue);
		return access;
	}
	
	@Override
	public void setFilterConfig(FilterConfig filterConfig) {
		super.setFilterConfig(filterConfig);
	
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {
		return super.onAccessDenied(request, response);
	}
}
