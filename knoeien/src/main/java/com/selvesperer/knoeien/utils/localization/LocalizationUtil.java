package com.selvesperer.knoeien.utils.localization;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;

public class LocalizationUtil {
	public static final String DEFAULT_RESOURCE_BUNDLE = "msg";
	private static ReloadableResourceBundleMessageSource messageSource;
	private static Logger log = (Logger) LoggerFactory.getLogger(LocalizationUtil.class);

	public static MessageSource getInstanceOfMessageSource() {
		if (messageSource == null) {
			messageSource = new ReloadableResourceBundleMessageSource();
			messageSource.setUseCodeAsDefaultMessage(true);// TODO should be
															// configured
			messageSource.setBasenames("classpath:com/selvesperer/messages", "classpath:com/selvesperer/exceptions", "classpath:ValidationMessages");
			messageSource.setCacheSeconds(60);// TODO should be configured
			messageSource.setDefaultEncoding("UTF-8");// TODO should be
														// configured
		}
		return messageSource;
	}

	public static FacesMessage createLocalizedFacesMessage(Severity severity, String summary, String message, Object... messageArgs) {
		return createLocalizedFacesMessage(getCurrentLocale(), severity, summary, message, messageArgs);
	}

	public static FacesMessage createLocalizedFacesMessage(String summary, Object... messageArgs) {
		return createLocalizedFacesMessage(getCurrentLocale(), null, summary, null, messageArgs);
	}

	public static FacesMessage createLocalizedFacesMessage(Locale locale, Severity severity, String summary, String message, Object... messageArgs) {
		String localizedSummary = findLocalizedString(summary);
		String localizedMessage = findLocalizedString(message, messageArgs);
		if (severity == null) {
			return new FacesMessage(localizedSummary);
		} else {
			return new FacesMessage(severity, localizedSummary, localizedMessage);
		}
	}

	public static FacesMessage createLocalizedFacesMessage(Locale locale, String key, Severity severity) {
		String localizedMessage = findLocalizedString(locale, key);
		// if (log.isDebugEnabled())log.debug("got a message of " +
		// localizedMessage + " from key=" + key + " and locale of " +
		// locale.toString());
		FacesMessage facesMessage = new FacesMessage(localizedMessage);
		if (severity != null)
			facesMessage.setSeverity(severity);
		return facesMessage;
	}

	public static String findLocalizedString(String key, Object... messageArgs) {
		return findLocalizedString(getCurrentLocale(), key, messageArgs);
	}

	public static String findLocalizedString(Locale locale, String key, Object... messageArgs) {
		MessageSource ms = getInstanceOfMessageSource();
		// if (log.isDebugEnabled()) log.debug("looking for a message for " +
		// key + "[" + messageArgs + "] with locale of " + locale.toString());
		String s = ms.getMessage(key, messageArgs, locale);
		return ObjectUtils.toString(ms.getMessage(key, messageArgs, locale));
	}

	public static String findLocalizedStringFromConfig(String config) {
		String configValue = ConfigurationUtil.config().getString(config);
		return findLocalizedString(configValue);
	}

	public static Locale getCurrentLocale() {
		return LocaleContextHolder.getLocale();
	}

	// this seems dangerous
	/*
	 * public static void setCurrentLocale(Locale thisLocale) {
	 * LocaleContextHolder.setLocale(thisLocale); }
	 */

	public static Locale getDefaultLocale() {
		return Locale.ENGLISH;
	}
}
