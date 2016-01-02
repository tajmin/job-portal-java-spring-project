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

import com.selvesperer.knoeien.spring.utils.SpringBeanFactory;
import com.selvesperer.knoeien.utils.LoggerUtils;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;


@Configuration
@ComponentScan(basePackages = "com.selvesperer.knoeien.data", excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:/test.properties")
@Profile("test")
public class SpringTestingConfig {

	private static Logger log = (Logger)LoggerFactory.getLogger(SpringTestingConfig.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public DataSource XdataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		if(log.isDebugEnabled()) log.debug("profile.name", "test");
		System.setProperty("profile.name", "test");

		dataSource.setDriverClassName("org.h2.Driver");
		String schemaName = ConfigurationUtil.config().getString("db.schema.name").toLowerCase();
		log.debug("SCHEMA IS " + schemaName);
		String url = "jdbc:h2:mem:test;MODE=Mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;INIT=CREATE SCHEMA IF NOT EXISTS " +schemaName +"\\;" + "SET SCHEMA "+schemaName;
		dataSource.setUrl(url);
		//dataSource.setUrl("jdbc:h2:mem:test;MODE=Mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;INIT=CREATE SCHEMA IF NOT EXISTS "	+ schemaName);
		
		dataSource.setUsername("sa");

		//use your own local mysql in tests here...
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/selvesperer_tests?characterEncoding=UTF-8");
//		dataSource.setUsername("tomcat");
//		dataSource.setPassword("tomcat");
//        
		return dataSource;
	}
	
	@Bean
	public DataSource dataSource() {
		
		SpringBeanFactory.setApplicationContext(applicationContext);
		LoggerUtils.setAllApplicationLogs("DEBUG");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		if(log.isDebugEnabled()) {
			log.debug("profile.name", "test");
		}
		System.setProperty("profile.name", "test");
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		String schemaName = ConfigurationUtil.config().getString("db.schema.name");
		String username = ConfigurationUtil.config().getString("db.username");
		String password = ConfigurationUtil.config().getString("db.password");
		if( log.isDebugEnabled() ) {
			log.debug( "SCHEMA IS " + schemaName );
			log.debug( "Username IS " + username );
			log.debug( "Password IS " + password );
		}
		
		dataSource.setUrl("jdbc:mysql://localhost:3306/"+schemaName);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		return dataSource;
	}
	
}
