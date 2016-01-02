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

import com.selvesperer.knoeien.spring.utils.SpringBeanFactory;

public class ShiroWebEnvironment extends IniWebEnvironment{
	private static Logger log = (Logger)LoggerFactory.getLogger(ShiroWebEnvironment.class);
	@Override
	public void init() {
		super.init();		
		
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
        SpringBeanFactory.setApplicationContext(springContext);
        if (log.isDebugEnabled()) log.debug("Spring Context found");
        SelvEspererPasswordService pwdService = springContext.getBean(SelvEspererPasswordService.class);        
       
        JpaRealm jpaRealm = springContext.getBean(JpaRealm.class);        
        if (log.isDebugEnabled()) log.debug("JPARealm found");
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        passwordMatcher.setPasswordService(pwdService);
        jpaRealm.setCredentialsMatcher(passwordMatcher);        
        
        Collection<Realm> realms = new ArrayList<Realm>();
		realms.add(jpaRealm);
        ((RealmSecurityManager) getSecurityManager()).setRealms(realms);
        
        if (log.isDebugEnabled()) log.debug("realm configured");
        	        
	}

	
}
