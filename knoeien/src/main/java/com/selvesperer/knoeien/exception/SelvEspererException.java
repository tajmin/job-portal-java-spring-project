package com.selvesperer.knoeien.exception;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.utils.localization.LocalizationUtil;

public class SelvEspererException extends RuntimeException {
	
	private static final long serialVersionUID = -6848804775114477302L;

	private static Logger log = (Logger) LoggerFactory.getLogger(SelvEspererException.class);
	
	private String[] args;
	private UIComponent clientComponent;
	private String clientID;
	private FacesMessage.Severity severity = FacesMessage.SEVERITY_INFO;
	private String messageKey;
	private String message;
	private Exception exception;
	private String titleKey="oops";
	private String title;
	
	public SelvEspererException(Exception e) {
		this(null, (String)null, e, (String)null);
		if(log.isDebugEnabled())
			log.error(e.getLocalizedMessage(),e);
	}
 
	public SelvEspererException(String messagekey) {
		this(null, messagekey, (Exception)null, (String)null);
	}

	public SelvEspererException(String messagekey, String messageArgs) {
		this(null, messagekey, (Exception)null, messageArgs);
	}

	public SelvEspererException(String messagekey, Exception e) {
		this(null, messagekey, e, (String)null);
	}

	public SelvEspererException(String messagekey, Exception e, String...messageArgs) {
		this(null, messagekey, e, messageArgs);
	}
	
	public SelvEspererException(String titlekey, String messagekey, String... messageArgs) {
		this(titlekey, messagekey, null, messageArgs);
	}
	
	public SelvEspererException(String titlekey, String messagekey, Exception e, String... messageArgs) {
		if (titlekey != null) titleKey = titlekey;
		this.messageKey = messagekey;
		this.args = messageArgs;
		this.exception = e;
		
		//the message key is what we really care about
		if (!StringUtils.isEmpty(messagekey)) return;
		
		//try to set a new message key based on what kind of exception this is
		if (exception != null) {
			initCause(exception.getCause());
			//find the message...
			if (e instanceof ConstraintViolationException) {
	            ConstraintViolation<?> violation = ((ConstraintViolationException)e).getConstraintViolations().iterator().next();
	            setMessage(violation.getMessage());
			//add handling for other exception types (data truncation, etc.) here...
			} else if (e instanceof SelvEspererException) {
				this.messageKey = ((SelvEspererException)e).getMessageKey();
				this.args = ((SelvEspererException)e).getArgs();
			} else {
				setMessageKey("error.unknown");
				setSeverity(FacesMessage.SEVERITY_ERROR);
			}
			return;
		}
		
		//this is definitely an unknown exception
		setMessageKey("error.unknown"); 
		return;		
	}
	
	/*
	 * Takes care of Faces error management
	 */
	public void addLocalizedMessageToContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (!StringUtils.isEmpty(clientID) && clientComponent == null) {
			UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
			UIComponent component = viewRoot.findComponent(getClientID());
			setClientComponent(component);
		}
		if (clientComponent != null) {
			try {
				UIInput currentInput = (UIInput) clientComponent.findComponent(clientComponent.getClientId());
				currentInput.setValid(false);
			} catch (Exception e) {}
		} 
		FacesMessage fm = new FacesMessage(getLocalizedMessage());
		if (getSeverity() != null) 
			fm.setSeverity(getSeverity());
		context.addMessage(clientID, fm);
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public UIComponent getClientComponent() {
		return clientComponent;
	}

	public void setClientComponent(UIComponent clientComponent) {
		this.clientComponent = clientComponent;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	@Override
	public String getLocalizedMessage() {
		if (messageKey != null) {
		return LocalizationUtil.findLocalizedString(getMessageKey(), (Object[]) getArgs());
		}
		return getMessage();
	}

	public String getLocalizedTitle() {
		if (titleKey != null) {
			return LocalizationUtil.findLocalizedString(getTitleKey());
		}
		return getTitle();
	}

	public FacesMessage.Severity getSeverity() {
		return severity;
	}

	public void setSeverity(FacesMessage.Severity severity) {
		this.severity = severity;
	}

	public String getDetailMessage() {
		if (getCause() != null) return getCause().toString();
		if (exception != null) return exception.toString();
		return this.getClass().getName();
	}
	
	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
