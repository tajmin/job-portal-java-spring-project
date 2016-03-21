package com.selvesperer.knoeien.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class DateFormatUtils {
	
	protected static final DateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
	protected static final DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected static final DateFormat apiDateFormatNoTimeZone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS");
	protected static final DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
	public static final DateFormat birthdayFormat = new SimpleDateFormat("MM dd");
	protected static final DateFormat webFormat = new SimpleDateFormat("dd-MM-yyyy");
	

	public static String getFormatPattern(DateFormat dateFormat) {
		if (dateFormat != null && dateFormat instanceof SimpleDateFormat) {
			return ((SimpleDateFormat) dateFormat).toPattern();
		}
		return "undefined";
	}
	
	//======================== STANDARD FORMATTERS ======================
	/**
	 * Short date format: 03/30/12
	 */
	public static DateFormat shortDateFormatter(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		dateFormat.setTimeZone(timeZone);
		return dateFormat;
	}

	/**
	 * Medium date format: Mar, 30 2012
	 */
	public static DateFormat mediumDateFormatter(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		dateFormat.setTimeZone(timeZone);
		return dateFormat;
	}

	/**
	 * Long date format: 03/30/12 10:00 AM
	 */
	public static DateFormat longDateFormatter(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
		dateFormat.setTimeZone(timeZone);
		return dateFormat;
	}

	/**
	 * Long day date format: Sunday, March 30, 2012
	 */
	public static DateFormat longDayDateFormatter(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
		dateFormat.setTimeZone(timeZone);
		return dateFormat;
	}

	/**
	 * Short hour format: 9:00 AM
	 */
	public static DateFormat shortHourDateFormatter(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
		dateFormat.setTimeZone(timeZone);
		return dateFormat;
	}
	
	//======================== END STANDARD FORMATTERS ======================
	
	/**
	 * Full day format: Sunday
	 */
	public static DateFormat fullDayFormatter(Locale locale, TimeZone tz) {
		DateFormat dateFormat = new SimpleDateFormat(fullDayFormatPattern(), locale);
		dateFormat.setTimeZone(tz);
		return dateFormat;
	}
	
	/**
	 * fullMonth date format: March 30
	 */
	public static DateFormat fullMonthDateFormatter(Locale locale, TimeZone tz) {
		DateFormat dateFormat = new SimpleDateFormat(fullMonthDateFormatPattern(locale, tz), locale);
		dateFormat.setTimeZone(tz);
		return dateFormat;
	}

	/**
	 * fullMonth date format: March 30 1999
	 */
	public static DateFormat longFullMonthDateFormatter(Locale locale, TimeZone tz) {
		DateFormat dateFormat = new SimpleDateFormat(longFullMonthDateFormatPattern(locale, tz), locale);
		dateFormat.setTimeZone(tz);
		return dateFormat;
	}



	/**
	 * Partial date format: Sun, Mar 30, 2012
	 */
	public static DateFormat partialDateFormatter(Locale locale, TimeZone tz) {
		DateFormat df = new SimpleDateFormat(partialDateFormatPattern(locale, tz), locale);
		df.setTimeZone(tz);
		return df;
	}

	/**
	 * Full date format: Sun, Mar 30, 2012 11:00 AM
	 */
	public static DateFormat fullDateFormatter(Locale locale, TimeZone tz) {
		DateFormat df = new SimpleDateFormat(fullDateFormatPattern(locale, tz), locale);
		df.setTimeZone(tz);
		return df;
	}

	/**
	 * Month day format: Mar 30
	 */
	public static DateFormat monthDayDateFormatter(Locale locale, TimeZone tz) {
		DateFormat dateFormat = new SimpleDateFormat(monthDayDateFormatPattern(locale, tz), locale);
		dateFormat.setTimeZone(tz);
		return dateFormat;
	}

	/**
	 * Short month day format: 03/30
	 */
	public static DateFormat shortMonthDayDateFormatter(Locale locale, TimeZone tz) {
		DateFormat dateFormat = new SimpleDateFormat(shortMonthDayDateFormatPattern(locale, tz), locale);
		dateFormat.setTimeZone(tz);
		return dateFormat;
	}

	/**
	 * Short month year format: 03/14
	 */
	public static DateFormat shortMonthYearDateFormatter(Locale locale, TimeZone tz) {
		DateFormat dateFormat = new SimpleDateFormat(shortMonthYearDateFormatPattern(locale, tz), locale);
		dateFormat.setTimeZone(tz);
		return dateFormat;
	}


	//==================== STRING PATTERNS FOR NON-STANDARD FORMATTERS ======================
	
	/*
	 * 03/30/99
	 */
	public static String shortDateFormatPattern(Locale locale, TimeZone tz) {
		return getFormatPattern(DateFormatUtils.shortDateFormatter(locale, tz));
	}

	/*
	 * Mar 30, 2012
	 */
	public static String mediumDateFormatPattern(Locale locale, TimeZone tz) {
		return getFormatPattern(mediumDateFormatter(locale, tz));
	}

	/*
	 * October 30
	 */
	public static String fullMonthDateFormatPattern(Locale locale, TimeZone tz) {
		String sdp = DateFormatUtils.shortDateFormatPattern(locale,  tz);//get the localized pattern then adjust to our liking
		String pattern = sdp.replaceAll("M+",  "MMMM").replaceAll("y+", "").replaceAll("d+", "d").replaceAll("/",  " ").trim();
		return pattern;
	}

	/*
	 * October 30 1999
	 */
	public static String longFullMonthDateFormatPattern(Locale locale, TimeZone tz) {
		String sdp = DateFormatUtils.shortDateFormatPattern(locale,  tz);//get the localized pattern then adjust to our liking
		String pattern = sdp.replaceAll("M+",  "MMMM").replaceAll("y+", "yyyy").replaceAll("d+", "d").replaceAll("/",  " ").trim();
		return pattern;
	}

	/*
	 * October 30 1999
	 */
	public static String javascriptShortDateFormatPattern(Locale locale, TimeZone tz) {
		String sdp = DateFormatUtils.shortDateFormatPattern(locale,  tz);//get the localized pattern then adjust to our liking
		String pattern = sdp.replaceAll("M+",  "mm").replaceAll("y+", "yy").replaceAll("d+", "dd").trim();
		return pattern;
	}

	/*
	 * 3/30/2012 10:00 AM
	 */
	public static String longDateFormatPattern(Locale locale, TimeZone tz) {
		return getFormatPattern(longDateFormatter(locale, tz));
	}

	/*
	 * Sunday, March 30, 2012
	 */
	public static String longDayDateFormatPattern(Locale locale, TimeZone tz) {
		return getFormatPattern(longDayDateFormatter(locale, tz));
	}

	/*
	 * Partial date format: Sun, Mar 30, 2012
	 */
	public static String partialDateFormatPattern(Locale locale, TimeZone tz) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.FULL, locale);
		String pattern = dateFormat.toPattern();
		pattern = pattern.replaceAll("E+", "EEE").replaceAll("M+", "MMM");
		return pattern;
	}

	/*
	 * Full date format: Sun, Mar 30, 2012 11:00 AM
	 */
	public static String fullDateFormatPattern(Locale locale, TimeZone tz) {
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, locale);
		String pattern = sdf.toPattern();
		pattern = pattern.replaceAll("E+", "EEE").replaceAll("M+", "MMM");
		return pattern;
	}

	/*
	 * Oct 30
	 */
	public static String monthDayDateFormatPattern(Locale locale, TimeZone tz) {
		String sdp = DateFormatUtils.shortDateFormatPattern(locale,  tz);
		String pattern = sdp.replaceAll("M+",  "MMM").replaceAll("y+", "").replaceAll("d+", "d").replaceAll("/",  " ").trim();
		return pattern;
	}

	/*
	 * 03/30
	 */
	public static String shortMonthDayDateFormatPattern(Locale locale, TimeZone tz) {
		String sdp = DateFormatUtils.shortDateFormatPattern(locale,  tz);//get the localized pattern then adjust to our liking
		String pattern = sdp.replaceAll("M+",  "MM").replaceAll("y+", "").replaceAll("d+", "dd");
		pattern = trimSeparators(pattern);
		return pattern;
	}

	/*
	 * 03/14
	 */
	public static String shortMonthYearDateFormatPattern(Locale locale, TimeZone tz) {
		String sdp = DateFormatUtils.shortDateFormatPattern(locale,  tz);//get the localized pattern then adjust to our liking
		String pattern = sdp.replaceAll("M+",  "MM").replaceAll("d+", "").replaceAll("y+", "yy");
		pattern = trimSeparators(pattern);
		return pattern;
	}
	
	/*
	 * Full day format: Sunday
	 */
	public static String fullDayFormatPattern() {
		return "EEEE";
	}
	
	private static String trimSeparators(String pattern) {
		if (pattern.startsWith("/")) {
			pattern = pattern.substring(1);
		}
		if (pattern.endsWith("/")) {
			pattern = pattern.substring(0, pattern.length() - 1);
		}
		return pattern;
	}

	
//	G	era designator	Text	AD
//	y	year	Number	2009
//	M	month in year	Text & Number	July & 07
//	d	day in month	Number	10
//	h	hour in am/pm (1-12)	Number	12
//	H	hour in day (0-23)	Number	0
//	m	minute in hour	Number	30
//	s	second in minute	Number	55
//	S	millisecond	Number	978
//	E	day in week	Text	Tuesday
//	D	day in year	Number	189
//	F	day of week in month	Number	2 (2nd Wed in July)
//	w	week in year	Number	27
//	W	week in month	Number	2
//	a	am/pm marker	Text	PM
//	k	hour in day (1-24)	Number	24
//	K	hour in am/pm (0-11)	Number	0
//	z	time zone	Text	Pacific Standard Time
//	'	escape for text	Delimiter	(none)
//	'	single quote	Literal	'
	
	public static String getDBFormattedDateString(Calendar date){
		String dateString = "";
		if(date != null){
			dateString = dbFormat.format(date.getTime());
		}
		return dateString;
	}
	
	public static String getShortFormattedDateString(Timestamp timestamp){
		String dateString = "";
		if(timestamp != null){
			dateString = shortFormat.format(timestamp.getTime());
		}
		return dateString;
	}
	
	public static String getWebFormattedDateString(Calendar date){
		String dateString = "";
		if(date != null){
			dateString = webFormat.format(date.getTime());
		}
		return dateString;
	}
	
	public static Calendar getDBCalendarFromString(String date){
		Calendar calender = null;
		try{
			if(StringUtils.isNotBlank(date)){
				calender = Calendar.getInstance();
				calender.setTime(dbFormat.parse(date));
			}
		}catch(Throwable t){
			calender = null;
		}
		return calender;
	}
	

}
