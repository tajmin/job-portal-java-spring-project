package com.selvesperer.knoeien.config;

import java.io.IOException;
import java.util.Properties;

public class SecurityConfigLoader {

	static Properties prop = new Properties();
	
	static{
	
		try {
			prop.load(SecurityConfigLoader.class.getClassLoader().getResourceAsStream("security_roles.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties getProperties(){
		
		return prop;
	}
	
	public static String getProperty(String key){
		
		return prop.getProperty(key);
	}
	
	
}
