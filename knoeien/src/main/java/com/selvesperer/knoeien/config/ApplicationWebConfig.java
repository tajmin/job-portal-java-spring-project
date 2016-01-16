package com.selvesperer.knoeien.config;

import java.util.HashMap;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.selvesperer.knoeien.spring.ViewScope;

@Configuration
@ComponentScan(basePackages = "com.selvesperer.knoeien", excludeFilters = { @Filter(Configuration.class) })
@ImportResource( { "classpath:/META-INF/spring/security.xml",
	"classpath:/META-INF/spring/applicationContext.xml",
	"classpath:/META-INF/spring/dispatcher-servlet.xml"} )  
public class ApplicationWebConfig {
	@Bean
	public static ViewScope viewScope() {
		return new ViewScope();
	}

	@Bean
	public static CustomScopeConfigurer scopeConfigurer() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("view", viewScope());
		configurer.setScopes(hashMap);
		return configurer;
	}

}
