package com.selvesperer.knoeien.utils;


public interface Constants {

	// Common
	public static final String DOUBLE_QUOTE = "\"";
	public static final String COLON = ":";
	public static final String COMMA = ",";
	public static final String BRACE_SECOND_START = "{";
	public static final String BRACE_SECOND_END = "}";
	public static final String BRACE_THIRD_START = "[";
	public static final String BRACE_THIRD_END = "]";
	public static final String LINE_BREAK = "\n";
	
	/*
	 * To be used as session variable keys
	 */
	public static final String CURRENT_USER_ID = "userId";
	public static final String CURRENT_COMPANY_ID = "companyId";
	public static final String CURRENT_USER_NAME = "userName";
	public static final int TOKEN_EXPIRES_IN_MINUTES = 10;
	
	//Database keys
	public static final int RESULT_LIMIT = 4;
	public static final int RESULT_OFFSET = 10;
	public static final int JOB_LATEST_SIZE = 2;
	public static final int JOB_INTEREST_SIZE = 2;
	public static final int MESSAGE_RESULT_LIMIT = 10;
	
	//sms verification code
	public static final String VERIFICATION_CODE = "verificationCode";
	
	//notification
	public static final int NOTIFICATION_SIZE = 5;
	
}
