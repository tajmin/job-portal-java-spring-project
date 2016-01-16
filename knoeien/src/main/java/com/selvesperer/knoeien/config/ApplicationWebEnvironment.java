package com.selvesperer.knoeien.config;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;

public class ApplicationWebEnvironment extends IniWebEnvironment{
	private static Logger log = (Logger)LoggerFactory.getLogger(ApplicationWebEnvironment.class);
	@Override
	public void init() {
		super.init();		
		
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
        ApplicationBeanFactory.setApplicationContext(springContext);
        if (log.isDebugEnabled()) log.debug("Spring Context does not found");
        ApplicationPasswordService pwdService = springContext.getBean(ApplicationPasswordService.class);        
       
        ApplicationRealm applicationRealm = springContext.getBean(ApplicationRealm.class);        
        if (log.isDebugEnabled()) log.debug("ApplicationRealm found");
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        passwordMatcher.setPasswordService(pwdService);
        applicationRealm.setCredentialsMatcher(passwordMatcher);        
        
        Collection<Realm> realms = new ArrayList<Realm>();
		realms.add(applicationRealm);
        ((RealmSecurityManager) getSecurityManager()).setRealms(realms);
        
        if (log.isDebugEnabled()) log.debug("ApplicationRealm has been configured");
        	        
	}

	
}
