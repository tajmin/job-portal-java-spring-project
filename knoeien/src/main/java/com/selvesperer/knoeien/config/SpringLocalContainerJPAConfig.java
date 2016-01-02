package com.selvesperer.knoeien.config;

import java.text.MessageFormat;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;

@Configuration
@EnableSpringConfigured
public class SpringLocalContainerJPAConfig {
	protected static final Logger logger = (Logger) LoggerFactory.getLogger(SpringConfig.class);
	protected static final String HIBERNATE_TRANSACTION_JTA_PLATFORM = "hibernate.transaction.jta.platform";

	@Autowired
	DataSource dataSource;

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

	@Value("${hibernate.format_sql:false}")
	private String formatSql;

	@Value("${hibernate.dialect:org.hibernate.dialect.MySQL5InnoDBDialect}")
	private String hibernateDialect;

	@Value("${hibernate.default_schema:selvesperer}")
	private String defaultSchema;

	@Value("${hibernate.use_default_schema:true}")
	private boolean useDefaultSchema;

	@Value("${hibernate.use_sql_comments:false}")
	private String useSqlComments;
	
	@Value("${hibernate.connection.useUnicode:true}")
	private String useUnicode;

	@Value("${hibernate.connection.characterEncoding:UTF-8}")
	private String characterEncoding;

	@Value("${hibernate.charSet:UTF-8}")
	private String charSet;

//	@Value("${hibernate.cache.region.factory_class:org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory}")
//	private String cacheClass;
//
//	@Value("${hibernate.cache.use_second_level_cache:true}")
//	private boolean useSecondLevelCache;
//
	@Value("${hibernate.connection.release_mode:AFTER_TRANSACTION}")
	private String releaseMode;

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(jpaAdapter());
		emf.setPersistenceProviderClass(HibernatePersistence.class);
		emf.setJpaDialect(new HibernateJpaDialect());
		//emf.setPersistenceXmlLocation("META-INF/testpersistence.xml");
		emf.setJpaProperties(getHibernateProperties());
		emf.setPackagesToScan(packagesToScan);
		emf.afterPropertiesSet();
		return emf;
	}

	@Bean
	public JpaVendorAdapter jpaAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory()
				.getObject());
		jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());
		return jpaTransactionManager;
	}

	@Bean
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
		properties.put("hibernate.ejb.naming_strategy", namingStrategy);
		properties.put("hibernate.connection.release_mode", getReleaseMode());
		properties.put("hibernate.connection.characterEncoding", getCharacterEncoding());
		properties.put("hibernate.connection.charSet", getCharSet());
		properties.put("hibernate.connection.useUnicode", getUseUnicode());

		if (logger.isInfoEnabled()) {
			logger.info(MessageFormat.format("SET HIBERNATE PROPERTIES: {0}", properties.toString()));
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

}
