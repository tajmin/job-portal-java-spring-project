package com.selvesperer.knoeien.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import com.github.jknack.handlebars.Options;


public class HandlebarsCustomHelper {
	
	/**
	 * Compares strings... look for a handlebars exaple of {{#eq ...
	 * @param value
	 * @param valueToBeCompare
	 * @param options
	 * @return
	 * @throws IOException
	 */
	public CharSequence eq(String value, String valueToBeCompare, Options options) throws IOException {
		if (value.equalsIgnoreCase(valueToBeCompare)) {
			return options.fn(this);
		}
		return options.inverse(this);
	}
	
	/**
	 * {{callMethod this "doSomething"}} 
	 */
	public CharSequence callMethod(Object object, String fieldName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String fieldValue = null;

		if (object != null) {
			Method method = object.getClass().getMethod(fieldName);
			if (method != null) {
				Object result = method.invoke(object);
				if (result != null) {
					fieldValue = result.toString();
				}
			}
		}

		return fieldValue;
	}
	
	public CharSequence formatOneParam(String message, Object object, String field, Options options) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return format(message, object, options, field);
	}

	public CharSequence formatTwoParams(String message, Object object, String field1, String field2, Options options) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return format(message, object, options, field1, field2);
	}
	
	/**
	 * {{formatXParams "Message {0} to format {1}", object, "method1", "method2"}} 
	 */
	private CharSequence format(String message, Object object, Options options, String... methodNames) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object[] args = new String[methodNames.length];

		if (object != null) {
			for(int i = 0; i < methodNames.length; i++) {				
				CharSequence result = callMethod(object, methodNames[i]);
				args[i] = result == null ? null : result.toString();				
			}
		}
		
		
		return MessageFormat.format(message, args);
	}
}
