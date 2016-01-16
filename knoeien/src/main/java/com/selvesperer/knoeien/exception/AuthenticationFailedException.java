package com.selvesperer.knoeien.exception;

public class AuthenticationFailedException extends SelvEspererException {
	
	private static final long serialVersionUID = 8452891448457395743L;

	public AuthenticationFailedException(String key) {
		super(key);
	}

	public AuthenticationFailedException(String key, String params) {
		super(key, params);
	}
}
