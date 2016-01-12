package com.selvesperer.knoeien.exception;

public class UnauthorizedActionException extends SelvEspererException {
	
	private static final long serialVersionUID = 5398371656233563734L;

	public UnauthorizedActionException(String key) {
		super(key);
	}

	public UnauthorizedActionException(String key, String params) {
		super(key, params);
	}
}
