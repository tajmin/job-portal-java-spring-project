package com.selvesperer.knoeien.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.selvesperer.knoeien.spring.utils.ApplicationBeanFactory;
import com.selvesperer.knoeien.utils.LoggerUtils;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;


@Configuration
@ComponentScan(basePackages = "com.selvesperer.knoeien.data", excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:/test.properties")
@Profile("test")
public class ApplicationTestingConfig {

	private static Logger log = (Logger)LoggerFactory.getLogger(ApplicationTestingConfig.class);

	@Autowired
	private ApplicationContext applicationContext;	
	
	@Bean
	public DataSource dataSource() {
		
		ApplicationBeanFactory.setApplicationContext(applicationContext);
		LoggerUtils.setAllApplicationLogs("DEBUG");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		if(log.isDebugEnabled()) log.debug("application.profile.name", "test");
		
		System.setProperty("profile.name", "test");		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		String schemaName = ConfigurationUtil.config().getString("db.schema.name");
		String username = ConfigurationUtil.config().getString("db.username");
		String password = ConfigurationUtil.config().getString("db.password");		
		
		dataSource.setUrl("jdbc:mysql://localhost:3306/"+schemaName);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		return dataSource;
	}	
}
