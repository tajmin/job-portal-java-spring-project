package com.selvesperer.knoeien.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

public class LoggerUtils {
	private static Logger log = (Logger) LoggerFactory.getLogger(LoggerUtils.class);

	public static void setLog(String logger, String level) {
		LoggerUtils.setLog(logger, Level.toLevel(level));
	}

	public static void setLog(String logger, Level level) {
		ch.qos.logback.classic.Logger selvElogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(logger);
		selvElogger.setLevel(level);
		log.debug("Logging for " + logger + " is now set to " + selvElogger.getLevel().toString());
	}

	public static void setAllApplicationLogs(String strLevel) {
		Level level = Level.toLevel(strLevel);

		LoggerUtils.setLog("com.selvesperer", level);
		LoggerUtils.setLog("test.selvesperer", level);
		LoggerUtils.setLog("org.ocpsoft.rewrite.faces", level);
		// LoggerUtils.setLog("org.springframework", level);
		LoggerUtils.setLog("org.springframework.security.web.authentication", level);
		LoggerUtils.setLog("org.apache", level);
		//LoggerUtils.setLog("net.sf.ehcache", level);
		LoggerUtils.setLog("org.hibernate", Level.OFF);
	//	LoggerUtils.setLog("org.hibernate.hql", Level.OFF);

		// disable apache logs that appears in test
		LoggerUtils.setLog("org.apache.http", "error");
	}

}
