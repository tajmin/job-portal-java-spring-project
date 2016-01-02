package com.selvesperer.knoeien.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selvesperer.knoeien.utils.localization.LocalizationUtil;

public class SelvEBusinessException extends Exception {
	
	private static final long serialVersionUID = 3353336741551619674L;

	private static Logger log = (Logger) LoggerFactory.getLogger(SelvEBusinessException.class);
	
	private String title;
	private String detail;
	private String message=LocalizationUtil.findLocalizedString("error.unknown");

	private Exception exception;

	public SelvEBusinessException(String title,String detail,Exception exception) {
		if(log.isDebugEnabled())
			log.debug(exception.getMessage());
		this.title = title;
		this.detail = detail;
		this.exception = exception;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
