package com.selvesperer.knoeien.utils;

import java.util.Calendar;

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
	
	public static Calendar getCalenderByAdding(int month, int day, String hhmmss){
		Calendar cal = null;
		try{
			cal = SelvEDate.getInstance();
			cal.add(Calendar.MONTH, month);
			cal.add(Calendar.DATE, day);
			int hh = 0, mm =0, ss=0;
			if(StringUtils.isNotBlank(hhmmss)){
				try{
					String times[] = hhmmss.split(":");
					if(times.length > 0) hh = Integer.valueOf(times[0]);
					if(times.length > 1) hh = Integer.valueOf(times[1]);
					if(times.length > 2) hh = Integer.valueOf(times[2]);
				}catch(Throwable t){}
			}
			cal.add(Calendar.HOUR_OF_DAY, hh);
			cal.add(Calendar.MINUTE, mm);
			cal.add(Calendar.SECOND, ss);
		}catch(Throwable t){
			
		}
		return cal;
	}
	
	public static int getDurationInSecond(int hour, int minutes, int seconds){
		return (hour*60*60) + (minutes*60) + seconds;
	}
	
	public static Double addCommision(Double price, Double percent){
		if(price != null){
			if(percent != null)
				return price + ((percent * price) / 100);
			else return price;
		}
		return null;
	}

}
