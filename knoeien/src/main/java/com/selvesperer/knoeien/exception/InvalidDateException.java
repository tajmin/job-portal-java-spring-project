package com.selvesperer.knoeien.exception;

public class InvalidDateException extends SelvEspererException {
	
	private static final long serialVersionUID = -6257912580251936366L;

	public InvalidDateException(String key) {
		super(key);
	}

	public InvalidDateException(String key, String params) {
		super(key, params);
	}
}
