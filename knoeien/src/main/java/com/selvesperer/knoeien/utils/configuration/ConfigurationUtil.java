package com.selvesperer.knoeien.utils.configuration;

import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class ConfigurationUtil {
	public static final String DEFAULT_PROFILE_NAME = "dev";
	private static Logger log = (Logger) LoggerFactory.getLogger(ConfigurationUtil.class);
	private static Config config;

	public synchronized static Config config() {
		if (config == null || config.getBoolean("reloadConfig")) {
			config = ConfigFactory.parseResources("application.conf");

			String profileName = getProfileName();
			Config stageConfig = config.getConfig(profileName);

			for (Entry<String, ConfigValue> entry : stageConfig.entrySet()) {
				config = config.withValue(entry.getKey(), entry.getValue());
			}
		}

		return config;
	}

	public static String getBaseUrl() {
		String baseURL = config().getString("application.baseUrl");
		return baseURL;
	}

	private static String lookupValue(String val) {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			String s =  (String) envCtx.lookup(val);
			return s;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getMailtest() {
		String s = lookupValue("selvesperer-mailtest");
		if (s == null) s = System.getProperty("selvesperer-mailtest");
		if(log.isDebugEnabled()) log.debug("Got mailtest value of " + s);
		return s;
	}	
	
	public static String getProfileName() {
		String profileName = System.getProperty("profile.name");
		profileName = profileName == null ? DEFAULT_PROFILE_NAME : profileName;
		return profileName;
	}

}
