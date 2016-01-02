package com.selvesperer.knoeien.config;

import java.text.MessageFormat;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.selvesperer.knoeien.spring.utils.SpringBeanFactory;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;

@Configuration
@EnableSpringConfigured
@EnableTransactionManagement
public class SpringJNDIJPAConfig {
	protected static final Logger logger = (Logger)LoggerFactory.getLogger(SpringConfig.class);
	protected static final String HIBERNATE_TRANSACTION_JTA_PLATFORM = "hibernate.transaction.jta.platform";

	@Value("${hibernate.naming_strategy:org.hibernate.cfg.DefaultNamingStrategy}")
	private String namingStrategy;

	@Value("${hibernate.packages_to_scan:com.selvesperer.knoeien.data.domain}")
	private String packagesToScan;

	@Value("${spring_config.project_name}")
	private String projectName;

	@Value("${hibernate.show_sql:false}")
	private String showSql;

	//@Value("${hibernate.hbm2ddl.auto:none}")
	@Value("${hibernate.hbm2ddl.auto:create-drop}")
	private String hbm2ddlAuto;

	@Value("${hibernate.format_sql:true}")
	private String formatSql;

	@Value("${hibernate.dialect:org.hibernate.dialect.MySQL5InnoDBDialect}")
	private String hibernateDialect;

	@Value("${hibernate.connection.useUnicode:true}")
	private String useUnicode;

	@Value("${hibernate.connection.characterEncoding:UTF-8}")
	private String characterEncoding;

	@Value("${hibernate.charSet:UTF-8}")
	private String charSet;

	@Value("${hibernate.default_schema}")
	private String defaultSchema;

	@Value("${hibernate.use_default_schema:true}")
	private boolean useDefaultSchema;

	@Value("${hibernate.use_sql_comments:false}")
	private String useSqlComments;
	
	@Value("${hibernate.connection.release_mode:AFTER_TRANSACTION}")
	private String releaseMode;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DataSource dataSource;

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	protected EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
				
		//JtaPersistenceUnitManager puManager = new JtaPersistenceUnitManager();
		/*Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
		dataSources.put("dataSource", dataSource);
		puManager.setDataSourceLookup(new MapDataSourceLookup(dataSources));
		puManager.setDefaultDataSource(dataSource);
		puManager.setPackagesToScan(packagesToScan());
		bean.setPersistenceUnitManager(puManager);*/			
		
		bean.setDataSource(dataSource);
		bean.setPackagesToScan(packagesToScan());
		
		bean.setPersistenceProviderClass(HibernatePersistence.class);
		bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		//bean.setJpaDialect(HibernateJpaDialect.class);
		Properties jpaProperties = getHibernateProperties();	
		bean.setJpaProperties(jpaProperties);

		//puManager.afterPropertiesSet();
		bean.afterPropertiesSet();
		return bean.getObject();
	}

	protected String getDefaultSchema() {
		String ds = ConfigurationUtil.config().getString("db.schema.name");
		if (ds != null)
			defaultSchema = ds;
		return defaultSchema;
	}

	protected String getUseUnicode() {
		return useUnicode;
	}

	protected String getCharacterEncoding() {
		return characterEncoding;
	}

	protected String getCharSet() {
		return charSet;
	}

	protected String getFormatSql() {
		return formatSql;
	}

	protected String getHbm2ddlAuto() {
		return hbm2ddlAuto;
	}

	protected String getHibernateDialect() {
		return hibernateDialect;
	}

	protected Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", getHibernateDialect());
		properties.put("hibernate.hbm2ddl.auto", getHbm2ddlAuto());
		properties.put("hibernate.show_sql", getShowSql());
		properties.put("hibernate.use_sql_comments", getUseSqlComments());
		properties.put("hibernate.format_sql", getFormatSql());
		
		if (useDefaultSchema) {
			properties.put("hibernate.default_schema", getDefaultSchema());
		}
		
		properties.put("hibernate.connection.characterEncoding", getCharacterEncoding());
		properties.put("hibernate.connection.charSet", getCharSet());
		properties.put("hibernate.connection.useUnicode", getUseUnicode());
		
		properties.put("hibernate.connection.release_mode", getReleaseMode());
		
		if (logger.isInfoEnabled()) {
			logger.info(MessageFormat.format("SET HIBERNATE PROPERTIES: {0}",
					properties.toString()));
		}
		return properties;
	}

	protected String getProjectName() {
		return projectName;
	}

	protected String getShowSql() {
		return showSql;
	}

	protected String getUseSqlComments() {
		return useSqlComments;
	}

	protected String packagesToScan() {
		return packagesToScan;
	}
	
	protected String getReleaseMode() {
		return releaseMode;
	}
	
	@Bean
	protected JpaTransactionManager transactionManager() {
		SpringBeanFactory.setApplicationContext(applicationContext);
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory((EntityManagerFactory)applicationContext.getBean("entityManagerFactory"));
		
		manager.afterPropertiesSet();
		return manager;
	}

}
