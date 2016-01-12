package com.selvesperer.knoeien.service.impl;

import java.io.IOException;
import java.util.HashMap;

import javax.ejb.Asynchronous;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.selvesperer.knoeien.emails.AbstractEmail;
import com.selvesperer.knoeien.service.EmailService;
import com.selvesperer.knoeien.utils.HandlebarsCustomHelper;

import net.sf.ehcache.search.SearchException;

/**
 * @author Mithun <shahinur.bd@gmail.com>
 * 
 */
@Service
public class EmailServiceImpl implements EmailService {
	private static Logger log = LoggerFactory.getLogger(EmailService.class);
	private static Handlebars handleBars;

	
	@Override
	@Asynchronous
	public void sendEmail(AbstractEmail abstractEmail) {
		
		try {
			if (log.isDebugEnabled())log.debug("Sending an immediate email");
			String text = this.constructEmail(abstractEmail);
			this._sendEmail( text );
		} catch (Throwable ex) {
			if (log.isErrorEnabled()) {
				log.error("Exception happned @saveEmail() " + ExceptionUtils.getStackTrace(ex));
			}
		}
	}	

	private String constructEmail(AbstractEmail source) {
		HashMap<String, String> vals = new HashMap<>();
		HashMap<String, Object> hbProps = new HashMap<String, Object>();

		vals = source.addBaseURL(vals);
		vals = source.addSubject(vals);
		vals = source.addTitle(vals);
		vals = source.addImageText(vals);

		// Note - handlebars can't take '.' tokenized input on proerty keys
		vals = source.addProperties(vals);

		// Translate Email Body
		Handlebars handlebars = getDefaultHandlebars();

		String messageText = "";
		try {
			hbProps = source.addPropertiesAsObject(hbProps);
			if (hbProps == null) {
				hbProps = new HashMap<String, Object>();
			}
			hbProps.putAll(vals);
			Template template = handlebars.compile(source.getEmailTemplateName());
			messageText = template.apply(hbProps);
		} catch (IOException e) {
			//throw new SearchException("email.templateinvalid");
			e.printStackTrace();
		}
		
		return messageText;		
	}

	private Handlebars getDefaultHandlebars() {
		if (handleBars == null) {
			ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
			loader.setPrefix("/com/selvesperer/templates/email");
			loader.setSuffix(".handlebars.html");
			handleBars = new Handlebars(loader);
			handleBars.registerHelpers(new HandlebarsCustomHelper());
		}
		return handleBars;
	}
	
	private boolean _sendEmail(String messageText) {
        try {
            System.out.println("::::::::::::::::::::"+messageText);
        } catch (Throwable ex) {
            log.debug("Post:Run:: stopped causes " + ExceptionUtils.getMessage(ex));
        }
        return false;
    }
}