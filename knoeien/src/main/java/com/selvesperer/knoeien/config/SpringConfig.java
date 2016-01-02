package com.selvesperer.knoeien.config;

import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.selvesperer.knoeien.utils.localization.LocalizationUtil;

@Configuration
@EnableSpringConfigured
@EnableAsync
@EnableScheduling
@EnableJpaRepositories(basePackages = { "com.selvesperer.knoeien.data.repository" })
@ComponentScan("com.selvesperer.knoeien")
@EnableTransactionManagement
public class SpringConfig {
	@Bean
	public static MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
		bean.setIgnoreUnresolvablePlaceholders(true);
		bean.setIgnoreResourceNotFound(true);
		return bean;
	}

	@Bean
	public static LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public MessageSource messageSource() {
		return LocalizationUtil.getInstanceOfMessageSource();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Bean
	Object configurableAspect() {
		return AnnotationBeanConfigurerAspect.aspectOf();
	}
}
