package com.selvesperer.knoeien.config;
import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationSecurityFilter extends UserFilter {	
	private static Logger log = (Logger)LoggerFactory.getLogger(ApplicationSecurityFilter.class);
 
	@Override
    protected void redirectToLogin(ServletRequest req, ServletResponse res) throws IOException {
    	if (log.isDebugEnabled()) log.debug("Here auto-login will attempt from shiro"); 
    	try {
	    	HttpServletResponse response = (HttpServletResponse) res; 		
    		response.sendRedirect("/knoeien/index.xhtml");
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
