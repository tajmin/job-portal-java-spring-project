package com.selvesperer.knoeien.utils;

public class QueryUtils {
	public static Integer parseInteger(Object value, boolean nullable){
		try {
			if(value == null && nullable) return null;
			if(value == null && !nullable) return 0;
			
			return Integer.parseInt(value.toString());
		} catch(Throwable ex){}
		
		return nullable ? null : 0;
	}
}
