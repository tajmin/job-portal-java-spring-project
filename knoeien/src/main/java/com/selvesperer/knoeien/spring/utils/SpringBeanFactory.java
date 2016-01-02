package com.selvesperer.knoeien.spring.utils;

import org.springframework.context.ApplicationContext;

public class SpringBeanFactory {

	private static ApplicationContext applicationContext;

	public static <T> T getBean(final String name, final Class<T> requiredType) {
		T bean = null;
		if (applicationContext != null) {
			bean = applicationContext.getBean(name, requiredType);
		}
		return bean;
	}

	public static <T> T getBean(final Class<T> requiredType) {
		T bean = null;
		if (applicationContext != null) {
			bean = applicationContext.getBean(requiredType);
		}
		return bean;
	}

	public static void setApplicationContext(
			final ApplicationContext applicationContext) {
		if (SpringBeanFactory.applicationContext == null) {
			SpringBeanFactory.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
