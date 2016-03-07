package com.selvesperer.knoeien.utils;

import org.apache.commons.lang3.StringUtils;

public class AppsUtil {

	public static String doubleToString(Double value) {
		try {
			if (value != null) {
				return value.toString();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return "";
	}

	public static Double stringToDouble(String value) {
		try {
			if (StringUtils.isNotBlank(value)) {
				return Double.parseDouble(value);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	
	public static Integer stringToInt(String value) {
		try {
			if (StringUtils.isNotBlank(value)) {
				return Integer.parseInt(value);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public static String integerToString(Integer value) {
		try {
			if (value != null) {
				return value.toString();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return "";
	}

}
