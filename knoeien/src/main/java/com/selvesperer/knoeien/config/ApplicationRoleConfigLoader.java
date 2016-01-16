package com.selvesperer.knoeien.config;

import java.io.IOException;
import java.util.Properties;

public class ApplicationRoleConfigLoader {

	static Properties properties = new Properties();
	
	static {	
		try {
			properties.load(ApplicationRoleConfigLoader.class.getClassLoader().getResourceAsStream("application_roles.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties getProperties() {		
		return properties;
	}
	
	public static String getProperty(String key) {		
		return properties.getProperty(key);
	}	
}
