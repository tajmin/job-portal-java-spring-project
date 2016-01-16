package com.selvesperer.knoeien.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;

public class ApplicationJNDIDataConfig {
	@Bean
	protected DataSource dataSource() {
		try {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			String jdbcURL = System.getProperty("jdbc.url");
			if (StringUtils.isNotEmpty(jdbcURL)) {
				cpds.setJdbcUrl(jdbcURL); //if we ever need to do an RDS restore, it restores into a new instance which would need a new URL
			} else {
				cpds.setJdbcUrl(ConfigurationUtil.config().getString("db.jdbc.url"));
			}
			cpds.setUser(ConfigurationUtil.config().getString("db.username"));
			cpds.setPassword(ConfigurationUtil.config().getString("db.password"));
			cpds.setMinPoolSize(ConfigurationUtil.config().getInt("db.minpool"));
			cpds.setAcquireIncrement(1);
			cpds.setMaxPoolSize(ConfigurationUtil.config().getInt("db.maxpool"));
			//cpds.setMaxStatements(20);
			cpds.setPreferredTestQuery("select 1");
			cpds.setTestConnectionOnCheckout(false);
			cpds.setTestConnectionOnCheckin(true);
			cpds.setIdleConnectionTestPeriod(ConfigurationUtil.config().getInt("db.idletest"));
			cpds.setMaxIdleTime(ConfigurationUtil.config().getInt("db.maxidle"));

			return cpds;
			
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);

		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
	}
}
