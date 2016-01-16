package com.selvesperer.knoeien.config;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.ProjectStage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.selvesperer.knoeien.utils.LoggerUtils;
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;

public class WebApplicationConfig implements WebApplicationInitializer {

	private static Logger log = (Logger)LoggerFactory.getLogger(WebApplicationConfig.class);

	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {
		if (log.isDebugEnabled()) log.debug("initialization web context configuration");
		
		String currentLevel = ConfigurationUtil.config().getString("log.level");
		LoggerUtils.setAllApplicationLogs(currentLevel);

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationSpringConfig.class, ApplicationWebConfig.class, ApplicationJNDIDataConfig.class,	ApplicationJNDIJPAConfig.class,ApplicationMVCConfiguration.class);
	
		servletContext.addListener(HttpSessionEventPublisher.class);
		servletContext.addListener(RequestContextListener.class);

		new ContextLoader(rootContext).initWebApplicationContext(servletContext);


		servletContext.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ConfigurationUtil.config().getString("jsf.stage"));
		servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", ConfigurationUtil.config().getString("jsf.refreshPeriod"));

		Messages.setResolver(new Messages.Resolver() {
			private static final String BASE_NAME = "com.selvesperer.knoeien.messages";

			public String getMessage(String message, Object... params) {
				Assert.notNull(message, "arugment never be null or empty");

				if (message.startsWith("{") && message.endsWith("}")) {
					ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME,	Faces.getLocale());
					String key = message.substring(1, message.length() - 1);
					if (log.isDebugEnabled()) {
						log.debug("fine localized message for key@" + key + " where locale is " + Faces.getLocale().toString());
					}
					if (bundle.containsKey(key)) {
						message = bundle.getString(key);
					}
				}
				return MessageFormat.format(message, params);
			}
		});
	}
}
