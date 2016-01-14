package com.selvesperer.knoeien.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.ejb.Asynchronous;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
import com.selvesperer.knoeien.utils.configuration.ConfigurationUtil;
import com.sun.mail.util.MailSSLSocketFactory;

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
			if (log.isDebugEnabled())
				log.debug("Sending an immediate email");
			String text = this.constructEmail(abstractEmail);
			this.sendDirect(abstractEmail, text);
		} catch (Throwable ex) {
			if (log.isErrorEnabled()) {
				log.error("Exception happned @saveEmail() " + ExceptionUtils.getStackTrace(ex));
			}
		}
	}

	public boolean sendDirect(AbstractEmail abstractEmail, String text) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", ConfigurationUtil.config().getString("host"));
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", ConfigurationUtil.config().getString("port"));
			props.put("mail.smtp.ssl.enable", "true");
			MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
			socketFactory.setTrustAllHosts(true);

			props.put("mail.smtp.connectiontimeout", 1000 * 13);
			props.put("mail.smtp.timeout", 1000 * 13);

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(ConfigurationUtil.config().getString("username"),
							ConfigurationUtil.config().getString("password"));
				}
			});

			// Message _message = new MimeMessage(session);
			Message _message = new MimeMessage(session);
			Address faddress = new InternetAddress(ConfigurationUtil.config().getString("defaultFrom"));
			Address taddress = new InternetAddress(abstractEmail.getUser().getEmail());

			_message.setFrom(faddress);
			_message.setRecipient(Message.RecipientType.TO, taddress);

			_message.setSubject(abstractEmail.getSubject());
			Multipart multipart = new MimeMultipart("alternative");
			BodyPart htmlBodyPart = new MimeBodyPart();

			htmlBodyPart.setContent(text, "text/html");
			multipart.addBodyPart(htmlBodyPart);

			_message.setContent(multipart);

			Transport.send(_message);
			return true;
		} catch (Throwable ex) {
			log.debug("Post:Run:: stopped causes " + ExceptionUtils.getMessage(ex));
		}
		return false;
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
			// throw new SearchException("email.templateinvalid");
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
}