package com.selvesperer.knoeien.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.selvesperer.knoeien.exception.InvalidDateException;
import com.selvesperer.knoeien.utils.localization.LocalizationUtil;

/**
 * Adds formatting, parsing, and arithmetic capability to the Java Calendar
 * object.
 */
public class SelvEDate extends GregorianCalendar implements Comparable<Calendar> {

	private static final long serialVersionUID = -6729491175608853232L;
	private static final Logger log = (Logger) LoggerFactory.getLogger(SelvEDate.class);

	public final static int ONE_SECOND = 1000;
	public final static int ONE_MINUTE = 60 * ONE_SECOND;
	public final static int ONE_HOUR = 60 * ONE_MINUTE;
	public final static int ONE_DAY = 24 * ONE_HOUR;
	public final static int ONE_WEEK = 7 * ONE_DAY;
	public final static int ONE_YEAR = 365 * ONE_DAY;

	public static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;	

	private static final Pattern TIMESTAMP_PATTERN = Pattern.compile("-?\\d+");

	/*
	 * YYYY - MM - DD
	 */
	private static final Pattern SHORT_API_DATE_PATTERN = Pattern.compile("\\d{4}\\-\\d{1,2}-\\d{1,2}");

	/*
	 * 2013-12-28 15:53:46
	 */
	private static final Pattern MYSQL_TIMESTAMP_PATTERN = Pattern.compile("(\\d{4}\\-\\d{1,2}\\-\\d{1,2})\\ \\d{1,2}\\:\\d{1,2}\\:\\d{1,2}");

	/*
	 * YYYY- MM - DD T HH : mm : ss :/. SSS -/+ 00 : 00
	 */
	private static final Pattern API_DATE_PATTERN = Pattern.compile("(\\d{4}-\\d{1,2}-\\d{1,2})T(\\d{1,2}):(\\d{1,2})(:(\\d{1,2})([:\\.](\\d{1,3}))?)?([-+]\\d{2}:?\\d{2}|Z)?");

	private Locale initLocale; // the locale used to initialize this data...
								// used as backup when locale is not available.

	private Locale getInitLocale() {
		if (initLocale == null)
			return LocaleContextHolder.getLocale();
		return initLocale;
	}

	public SelvEDate() {
		setTime(new Date());
	}

	/**
	 * 
	 * @param month - the 1-based month of the year (1-12)
	 * @param day - the day of the month
	 */
	public SelvEDate(int month, int day) {
		set(MONTH, month - 1);
		set(DAY_OF_MONTH, day);
	}

	public static SelvEDate now() {
		return new SelvEDate();
	}
	
	public static SelvEDate today() {
		return new SelvEDate().clearTime();
	}

	/**
	 * Creates an SelvEDate "now" and sets the calendar's timezone to the timezone
	 * passed in.
	 */
	public static SelvEDate now(TimeZone zone) {
		SelvEDate now = new SelvEDate();
		now.setTimeZone(zone);
		return now;
	}

	public static SelvEDate nullableDate(Calendar cal) {
		if (cal == null)
			return null;
		return new SelvEDate(cal);
	}

	public static SelvEDate parseMonthDayString(String str) {
		DateFormat df = DateFormatUtils. shortMonthDayDateFormatter(LocaleContextHolder.getLocale(), TimeZone.getDefault());
		try {
			Date d = df.parse(str);
			return new SelvEDate(d);
		} catch (Exception e) {
			try {
				return new SelvEDate(str);
			} catch (Exception ex) {
				throw new InvalidDateException(str);
			}
		}
	}

	public static SelvEDate parseMonthYearString(String str) {
		DateFormat monthYearFormat = new SimpleDateFormat("MM/yy");
		try {
			Date d = monthYearFormat.parse(str);
			return new SelvEDate(d);
		} catch (Exception e) {
			try {
				return new SelvEDate(str);
			} catch (Exception ex) {
				throw new InvalidDateException(str);
			}
		}
	}

	public SelvEDate(int year, int month, int day, TimeZone timeZone) {
		setTimeZone(timeZone);
		set(year, month, day);
	}

	public SelvEDate(DateFormat df, String text) {
		try {
			Date d = df.parse(text);
			setTime(d);
		} catch (Exception ex) {
			throw new InvalidDateException(text);
		}
	}

	public SelvEDate(int year, int month, int day) {
		set(year, month, day);
	}

	public SelvEDate(int year, int month, int day, int hourOfDay, int minute) {
		set(year, month, day, hourOfDay, minute);
	}

	public SelvEDate(int year, int month, int day, int hourOfDay, int minute, TimeZone timeZone) {
		setTimeZone(timeZone);
		set(year, month, day, hourOfDay, minute);
	}

	public SelvEDate(int year, int month, int day, int hourOfDay, int minute, int second) {
		set(year, month, day, hourOfDay, minute, second);
	}

	public SelvEDate(int year, int month, int day, int hourOfDay, int minute, int second, TimeZone timeZone) {
		setTimeZone(timeZone);
		set(year, month, day, hourOfDay, minute, second);
	}

	public SelvEDate(Date d) {
		setTime(d);
	}

	public SelvEDate(Date d, TimeZone timeZone) {
		setTime(d);
		setTimeZone(timeZone);
	}

	public SelvEDate(Calendar cal) {
		setTime(cal.getTime());
	}

	public SelvEDate(Calendar cal, TimeZone timeZone) {
		setTime(cal.getTime());
		setTimeZone(timeZone);
	}

	public SelvEDate(long timestamp) {
		setTimeInMillis(timestamp);
	}

	public SelvEDate(long timestamp, TimeZone timeZone) {
		super(timeZone);
		setTimeInMillis(timestamp);
	}

	public SelvEDate(Long timestamp) {
		setTimeInMillis(timestamp);
	}

	public SelvEDate(String text) throws InvalidDateException {
		this(text, LocaleContextHolder.getLocale());
	}

	public SelvEDate(String text, Locale locale) throws InvalidDateException {
		this(text, locale, TimeZone.getDefault());
	}

	public SelvEDate(String text, TimeZone zone) throws InvalidDateException {
		this(text, LocaleContextHolder.getLocale(), zone);
	}

	public SelvEDate(String text, Locale locale, TimeZone timeZone) throws InvalidDateException {
		this(text, locale, timeZone, false);
	}

	private SelvEDate(String text, Locale locale, TimeZone zone, boolean unsafeData) throws InvalidDateException {
		super(zone);
		try {
			if (TIMESTAMP_PATTERN.matcher(text).matches()) {
				// Date string is a long being turned into a date
				setTime(new Date(Long.parseLong(text)));
				return;
			} else {
				// Check for timezone field at the end of the date string. If it
				// is a timezone string, strip it off
				// of the date string and apply the timezone after parsing the
				// rest of the date.
				if (text.lastIndexOf(" ") > -1) {
					String timeZoneName = text.substring(text.lastIndexOf(" ") + 1);
					TimeZone timeZone = TimeZone.getTimeZone(timeZoneName);
					// Check for bad timezone ID.
					if (timeZoneName.equals(timeZone.getID())) {
						setTimeZone(timeZone);
						text = text.substring(0, text.lastIndexOf(" "));
					}
				}

				Matcher matcher;
				if ((matcher = SHORT_API_DATE_PATTERN.matcher(text)).matches()) {
					// Short API date format with not time attached, based on
					// ISO 8601 - yyyy-MM-dd
					setTime(DateFormatUtils.shortFormat.parse(text));
					return;
				} else if ((matcher = API_DATE_PATTERN.matcher(text)).matches()) {
					// This is some sort of ISO 8601 date in the form
					// "yyyy-MM-dd'T'HH:mm:ss:SSSZ"
					// see for more details: http://www.w3.org/TR/NOTE-datetime
					String date = matcher.group(1);
					String hours = matcher.group(2);
					String minutes = matcher.group(3);
					String seconds = matcher.group(5);
					String millis = matcher.group(7);
					String timeZone = matcher.group(8);

					if (seconds == null)
						seconds = "00";
					if (millis == null)
						millis = "000";

					if (timeZone == null) {
						timeZone = "";
					}

					if (timeZone.equals("Z")) {
						// UTC time, change it to something we can parse
						// instead.
						timeZone = "-0000";

					} else if (timeZone.contains(":")) {
						// Time zone in format -00:00, convert it to -0000
						timeZone = timeZone.replace(":", "");
					}

					// Now normalize it into a working date format.
					text = date + "T" + hours + ":" + minutes + ":" + seconds + ":" + millis + timeZone;

					DateFormat dateFormat = (DateFormat) (StringUtils.isEmpty(timeZone) ? DateFormatUtils.apiDateFormatNoTimeZone.clone() : DateFormatUtils.apiDateFormat.clone());
					dateFormat.setLenient(true);
					dateFormat.setTimeZone(getTimeZone());
					setTime(dateFormat.parse(text));
					return;

				} else {
					try {
						setTime(DateFormatUtils.shortDateFormatter(locale, getTimeZone()).parse(text));
						clearInternalTime();
						return;
					} catch (Exception e) {
					}
					try {
						setTime(DateFormatUtils.mediumDateFormatter(locale, getTimeZone()).parse(text));
						clearInternalTime();
						return;
					} catch (Exception e) {
					}
					try {
						setTime(DateFormatUtils.longFullMonthDateFormatter(locale, getTimeZone()).parse(text));
						clearInternalTime();
						return;
					} catch (Exception e) {
					}
					try {
						setTime(DateFormatUtils.longDateFormatter(locale, getTimeZone()).parse(text));
						clearInternalTime();
						return;
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		throw new InvalidDateException(text);
	}

	public SelvEDate clone() {
		return new SelvEDate(getTimeInMillis(), getTimeZone());
	}

	public static SelvEDate toDate(String text) {
		return toDate(text, LocaleContextHolder.getLocale());
	}

	public static SelvEDate toDate(String text, Locale locale) {
		return toDate(text, locale, TimeZone.getDefault());
	}

	public static SelvEDate toDate(String text, TimeZone zone) {
		return toDate(text, LocaleContextHolder.getLocale(), zone);
	}

	public static SelvEDate toDate(String text, Locale locale, TimeZone zone) {
		if (StringUtils.isEmpty(text))
			return null;

		try {
			return new SelvEDate(text, locale, zone, true);
		} catch (InvalidDateException e) {
			return null;
		}
	}

	/**
	 * API format, based on ISO 8601: 2006-12-24T10:00:00:000-0600
	 */
	public String toAPIString() {
		return toAPIString(getTimeZone());
	}

	/**
	 * API format, based on ISO 8601: 2006-12-24T10:00:00:000-0600
	 */
	public String toAPIString(TimeZone timeZone) {
		DateFormat dateFormat = (DateFormat) DateFormatUtils.apiDateFormat.clone();
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(getTime());
	}

	/**
	 * Short API format (no time), based on ISO 8601: 2006-12-24
	 */
	public String toShortAPIString() {
		return toShortAPIString(getTimeZone());
	}

	/**
	 * Short API format (no time), based on ISO 8601: 2006-12-24
	 */
	public String toShortAPIString(TimeZone timeZone) {
		DateFormat dateFormat = (DateFormat) DateFormatUtils.shortFormat.clone();
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(getTime());
	}

	/**
	 * DB Format string yyyy-mm-dd hh:mm:ss
	 */
	public String toDBFormatString() {
		return toDBFormatString(getTimeZone());
	}

	/**
	 * DB Format string yyyy-mm-dd hh:mm:ss
	 */
	public String toDBFormatString(TimeZone timeZone) {
		DateFormat dateFormat = (DateFormat) DateFormatUtils.dbFormat.clone();
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(getTime());
	}
	
	/**
	 * DB Format string yyyy-mm-dd hh:mm:ss
	 */
	public String toDBShortFormatString() {
		return toDBShortFormatString(getTimeZone());
	}

	/**
	 * DB Format string yyyy-mm-dd hh:mm:ss
	 */
	public String toDBShortFormatString(TimeZone timeZone) {
		DateFormat dateFormat = (DateFormat) DateFormatUtils.shortFormat.clone();
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(getTime());
	}
	
	/**
	 * Short date format: 3/30/12
	 */
	public String toShortString() {
		return toShortString(getInitLocale(), getTimeZone());
	}

	/**
	 * Short date format: 3/30/12
	 */
	public String toShortString(Locale locale) {
		return toShortString(locale, getTimeZone());
	}

	/**
	 * Short date format: 3/30/12
	 */
	public String toShortString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.shortDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Medium date format: Mar 30, 2012
	 */
	public String toMediumString() {
		return toMediumString(getInitLocale(), getTimeZone());
	}

	/**
	 * Medium date format: Mar 30, 2012
	 */
	public String toMediumString(Locale locale) {
		return toMediumString(locale, getTimeZone());
	}

	/**
	 * Medium date format: Mar 30, 2012
	 */
	public String toMediumString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.mediumDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * fullMonth date format: March 30
	 */
	public String toFullMonthString() {
		return toFullMonthString(getInitLocale(), getTimeZone());
	}

	/**
	 * fullMonth date format: March 30
	 */
	public String toFullMonthString(Locale locale) {
		return toFullMonthString(locale, getTimeZone());
	}

	/**
	 * fullMonth date format: March 30
	 */
	public String toFullMonthString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.fullMonthDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * fullMonth date format: March 30 1999
	 */
	public String toLongFullMonthString() {
		return toLongFullMonthString(getInitLocale(), getTimeZone());
	}

	/**
	 * fullMonth date format: March 30 1999
	 */
	public String toLongFullMonthString(Locale locale) {
		return toLongFullMonthString(locale, getTimeZone());
	}

	/**
	 * fullMonth date format: March 30 1999
	 */
	public String toLongFullMonthString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.longFullMonthDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Long date format: 03/30/12 10:00 AM
	 */
	public String toLongString() {
		return toLongString(getInitLocale(), getTimeZone());
	}

	/**
	 * Long date format: 03/30/12 10:00 AM
	 */
	public String toLongString(Locale locale) {
		return toLongString(locale, getTimeZone());
	}

	/**
	 * Long date format: 03/30/12 10:00 AM
	 */
	public String toLongString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.longDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Long day date format: Sunday, March 30, 2012
	 */
	public String toLongDayString() {
		return toLongDayString(getInitLocale(), getTimeZone());
	}

	/**
	 * Long day date format: Sunday, March 30, 2012
	 */
	public String toLongDayString(Locale locale) {
		return toLongDayString(locale, getTimeZone());
	}

	/**
	 * Long day date format: Sunday, March 30, 2012
	 */
	public String toLongDayString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.longDayDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Partial date format: Sun, Mar 30, 2012
	 */
	public String toPartialString() {
		return toPartialString(getInitLocale(), getTimeZone());
	}

	public String toPartialString(Locale locale) {
		return toPartialString(locale, getTimeZone());
	}

	public String toPartialString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.partialDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Full date format: Sun, Mar 30, 2012 11:00 AM
	 */
	public String toFullString() {
		return toFullString(getInitLocale(), getTimeZone());
	}

	public String toFullString(Locale locale) {
		return toFullString(locale, getTimeZone());
	}

	public String toFullString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.fullDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Month day format: Mar 30
	 */
	public String toMonthDayString() {
		return toMonthDayString(getInitLocale(), getTimeZone());
	}

	public String toMonthDayString(Locale locale) {
		return toMonthDayString(locale, getTimeZone());
	}

	public String toMonthDayString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.monthDayDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Short month day format: 03/30
	 */
	public String toShortMonthDayString() {
		return toShortMonthDayString(getInitLocale(), getTimeZone());
	}

	public String toShortMonthDayString(Locale locale) {
		return toShortMonthDayString(locale, getTimeZone());
	}

	public String toShortMonthDayString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.shortMonthDayDateFormatter(locale, timeZone).format(getTime());
	}

	/**
	 * Short month year format: 03/30
	 */
	public String toShortMonthYearString() {
		return toShortMonthYearString(getInitLocale(), getTimeZone());
	}

	public String toShortMonthYearString(Locale locale) {
		return toShortMonthYearString(locale, getTimeZone());
	}

	public String toShortMonthYearString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.shortMonthYearDateFormatter(locale, timeZone).format(getTime());
	}
	
	
	/**
	 * Short hour format: 9:00 AM
	 */
	public String toShortHourString() {
		return toShortHourString(getInitLocale(), getTimeZone());
	}

	public String toShortHourString(Locale locale) {
		return toShortHourString(locale, getTimeZone());
	}

	public String toShortHourString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.shortHourDateFormatter(locale, timeZone).format(getTime());
	}
	
	/**
	 * Full day format: Sunday
	 */
	public String toFullDayString() {
		return toFullDayString(getInitLocale(), getTimeZone());
	}
	
	public String toFullDayString(Locale locale, TimeZone timeZone) {
		return DateFormatUtils.fullDayFormatter(locale, timeZone).format(getTime());
	}
	
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1; // months are zero based
	}

	public static int getMonth(Calendar cal) {
		return cal.get(Calendar.MONTH) + 1; // months are zero based
	}

	public int getMonth() {
		return get(Calendar.MONTH) + 1;
	}

	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getHourOfDay(SelvEDate selvEDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(selvEDate.getTime());
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfMonth(Calendar cal) {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public int getDayOfMonth() {
		return get(Calendar.DAY_OF_MONTH);
	}
	
	public SelvEDate addField(int field, int value) {
		SelvEDate tmp = clone();
		tmp.add(field, value);
		return tmp;
	}

	public SelvEDate addHours(int hours) {
		return addField(HOUR, hours);
	}

	public SelvEDate addMinutes(int minutes) {
		return addField(MINUTE, minutes);
	}
	
	public SelvEDate addSeconds(int seconds) {
		return addField(SECOND, seconds);
	}
	
	public SelvEDate addMilliseconds(int millis) {
		return addField(MILLISECOND, millis);
	}

	public SelvEDate addDays(int days) {
		return addField(DATE, days);
	}

	public SelvEDate addMonths(int months) {
		return addField(MONTH, months);
	}

	public SelvEDate addWeeks(int weeks) {
		return addField(DATE, weeks * 7);
	}

	public SelvEDate addYears(int years) {
		return addField(YEAR, years);
	}

	public static boolean isToday(Date curDate) {
		return isSameDay(new SelvEDate(curDate), SelvEDate.today());
	}

	public static boolean isYesterday(Date curDate) {
		return isSameDay(new SelvEDate(curDate), SelvEDate.today().addDays(-1));
	}

	public static boolean isToday(SelvEDate curDate) {
		return isSameDay(curDate, SelvEDate.today());
	}

	public static boolean isYesterday(SelvEDate curDate) {
		return isSameDay(curDate, SelvEDate.today().addDays(-1));
	}

	public static boolean isTomorrow(SelvEDate curDate) {
		return isSameDay(curDate, SelvEDate.today().addDays(1));
	}

	public static boolean isSameDay(SelvEDate d1, SelvEDate d2) {
		if (d1 == null) {
			return false;
		}
		if (d2 == null) {
			return false;
		}
		if (d1.clearTime().getTimeInMillis() == d2.clearTime().getTimeInMillis()) {
			return true;
		}
		return false;
	}

	public static boolean isSameMonth(SelvEDate d1, SelvEDate d2) {
		if (d1 == null) {
			return false;
		}
		if (d2 == null) {
			return false;
		}
		if (d1.get(YEAR) == d2.get(YEAR) && d1.getMonth() == d2.getMonth()) {
			return true;
		}
		return false;
	}

	public static boolean isSameYear(SelvEDate d1, SelvEDate d2) {
		if (d1 == null) {
			return false;
		}
		if (d2 == null) {
			return false;
		}
		if (d1.get(YEAR) == d2.get(YEAR)) {
			return true;
		}
		return false;
	}

	public SelvEDate setTime(long time) {
		SelvEDate tmp = clone();
		tmp.setTimeInMillis(time);
		return tmp;
	}

	public static boolean datesEqual(Calendar d1, Calendar d2) {
		if (d1 == d2) {
			return true;
		}
		if (d1 == null) {
			return false;
		}
		if (d2 == null) {
			return false;
		}
		return d1.getTime().getTime() == d2.getTime().getTime();
	}

	public static double diff(Calendar thisDate, Calendar otherDate) {
		return diff(thisDate, otherDate, false);
	}

	public static double diff(Calendar thisDate, Calendar d2, boolean killTimes) {
		SelvEDate origDate = new SelvEDate(thisDate);
		SelvEDate otherDate = new SelvEDate(d2);
		if (killTimes) {
			otherDate.clearInternalTime();
			origDate.clearInternalTime();
		}
		long myTime = origDate.getTimeInMillis() + origDate.getTimeZone().getOffset(origDate.getTimeInMillis());
		long otherTime = otherDate.getTimeInMillis() + otherDate.getTimeZone().getOffset(otherDate.getTimeInMillis());
		long difference = otherTime - myTime;
		if (difference == 0)
			return 0;
		double daysDifferent = difference / (double) MILLISECONDS_PER_DAY;
		return daysDifferent;
	}

	public static int diffInDays(Calendar thisDate, Calendar thselvEDate) {
		int diffInDays = (int) diff(thisDate, thselvEDate, true);
		return (diffInDays < 0 ? (int) Math.floor(diffInDays) : diffInDays);
	}

	public static int diffInDays(Date thisDate, Date thselvEDate) {
		int diffInDays = (int) diff(DateToCalendar(thisDate), DateToCalendar(thselvEDate), true);
		return (diffInDays < 0 ? (int) Math.floor(diffInDays) : diffInDays);
	}

	public static Calendar DateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static int diffInWeeks(Calendar thisDate, Calendar otherDate) {
		SelvEDate w1 = (SelvEDate) thisDate.clone();
		w1.set(DAY_OF_WEEK, w1.getMinimum(DAY_OF_WEEK));
		w1 = w1.clearTime();
		SelvEDate w2 = (SelvEDate) otherDate.clone();
		w2.set(DAY_OF_WEEK, w2.getMinimum(DAY_OF_WEEK));
		w2 = w2.clearTime();

		int weeks = 0;

		if (w1.before(w2)) {
			while (w1.before(w2)) {
				weeks++;
				w1.add(DATE, 7);
			}
		} else {
			while (w2.before(w1)) {
				weeks--;
				w2.add(DATE, 7);
			}
		}
		return weeks;
	}

	/** other - this **/
	public static int diffInMonths(Calendar thisDate, Calendar otherDate) {
		return ((otherDate.get(YEAR) - thisDate.get(YEAR)) * 12) - thisDate.get(MONTH) + otherDate.get(MONTH);
	}

	/** otherDate - thisDate **/
	public static int diffInMonths(Date thisDate, Date otherDate) {
		Calendar thisDateCal = Calendar.getInstance();
		thisDateCal.setTime(thisDate);
		Calendar otherDateCal = Calendar.getInstance();
		otherDateCal.setTime(otherDate);
		return ((otherDateCal.get(YEAR) - thisDateCal.get(YEAR)) * 12) - thisDateCal.get(MONTH) + otherDateCal.get(MONTH);
	}

	/** otherDate - thisDate **/
	public static int diffInYears(Calendar thisDate, Calendar otherDate) {
		return Math.abs(otherDate.get(YEAR) - thisDate.get(YEAR));
	}

	public static int diffInYears(Date thisDate, Date otherDate) {
		return Math.abs(DateToCalendar(otherDate).get(YEAR) - DateToCalendar(thisDate).get(YEAR));
	}

	public SelvEDate setField(int field, int value) {
		SelvEDate tmp = clone();
		tmp.set(field, value);
		return tmp;
	}

	public static int diffInSeconds(Calendar thisDate, Calendar otherDate) {
		return (int) Math.ceil((Math.abs(thisDate.getTime().getTime() - otherDate.getTime().getTime())) / 1000);
	}

	public static int diffInMinutes(Calendar thisDate, Calendar otherDate) {
		return (int) Math.ceil((thisDate.getTimeInMillis() - otherDate.getTimeInMillis()) / (60 * 1000));
	}

	public static int diffInHours(Calendar thisDate, Calendar otherDate) {
		return (int) Math.ceil((Math.abs(thisDate.getTime().getTime() - otherDate.getTime().getTime())) / 1000 / 60 / 60);
	}

	public int compareTo(Calendar date) {
		return getTime().compareTo(date.getTime());
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Calendar))
			return false;
		return getTimeInMillis() == ((Calendar) obj).getTimeInMillis();
	}

	public String toString() {
		return toShortString();
	}

	public String toString(Locale locale, TimeZone timeZone) {
		return toShortString(locale, timeZone);
	}

	public String toString(Locale locale) {
		return toShortString(locale);
	}

	public boolean hasTimeSet() {
		if (get(SECOND) > 0 || get(MINUTE) > 0 || get(HOUR) > 0)
			return true;
		return false;
	}

	private void clearInternalTime() {
		clear(HOUR_OF_DAY);
		clear(HOUR);
		clear(MINUTE);
		clear(SECOND);
		clear(MILLISECOND);
		clear(AM_PM);
	}

	public SelvEDate clearTime() {
		SelvEDate tmp = clone();
		tmp.clearInternalTime();
		return tmp;
	}

	public static int getCurrentYear() {
		return SelvEDate.getYear(new SelvEDate().getTime());
	}

	public static int getCurrentMonthNumber() {
		return SelvEDate.getMonth(new SelvEDate().getTime());
	}

	public static int getCurrentDayOfCurrentMonth() {
		return SelvEDate.getDayOfMonth(new SelvEDate().getTime());
	}	

	public static SelvEDate beginningOfLastWeek() {
		SelvEDate d = beginningOfWeek().addWeeks(-1);
		return d;
	}

	public static SelvEDate beginningOfWeek() {
		return beginningOfWeek(SelvEDate.today());
	}
	
	public static SelvEDate beginningOfWeek(SelvEDate d) {
		if (d==null) return d;
		d = d.addDays((d.get(DAY_OF_WEEK) * -1) + 1);
		return d;		
	}

	public static SelvEDate beginningOfMonth() {
		return beginningOfMonth(SelvEDate.today());
	}
	
	public static SelvEDate beginningOfMonth(SelvEDate d) {
		if (d == null) return d;
		d = d.clearTime().setField(d.DAY_OF_MONTH, 1);
		return d;
	}
	
	public static SelvEDate endOfDay(SelvEDate selvEDate) {
		return selvEDate.addDays(1).addMilliseconds(-1);
	}
	
	public static SelvEDate endOfDay() {
		return endOfDay(SelvEDate.today());
	}
	
	public static SelvEDate endOfMonth() {
		return endOfMonth(SelvEDate.today());
	}	
	
	public static SelvEDate endOfMonth(SelvEDate d) {
		if (d==null) return d;
		return beginningOfMonth(d).addMonths(1).addSeconds(-1);
	}
	
	public static SelvEDate beginningOfQuarter() {
		return beginningOfQuarter(SelvEDate.today());
	}
	
	public static SelvEDate beginningOfQuarter(SelvEDate d) {
		d = d.clearTime().setField(d.DAY_OF_MONTH, 1);
		int mod = (d.getMonth() - 1) % 3;
		return d.addMonths(-1 * mod);
	}
	
	public static SelvEDate endOfQuarter() {
		return endOfQuarter(SelvEDate.today());
	}
	
	public static SelvEDate endOfQuarter(SelvEDate d) {
		if (d == null) return d;
		return beginningOfQuarter(d).addMonths(3).addSeconds(-1);
	}
	
	public static SelvEDate beginningOfYear() {
		return beginningOfYear(SelvEDate.today());
	}
	
	public static SelvEDate beginningOfYear(SelvEDate d) {
		if (d == null) return d;
		SelvEDate td = SelvEDate.today().setField(DAY_OF_MONTH, 1).setField(MONTH, 0);
		return td;
	}
	
	public static SelvEDate endOfYear() {
		return endOfYear(SelvEDate.today());
	}
	
	public static SelvEDate endOfYear(SelvEDate d) {
		if (d==null) return d;
		return beginningOfYear(d).addYears(1).addSeconds(-1);
	}
	
	/**
	 * Get the natural formatting of a date (e.g., Just now, 10 minutes ago,
	 * Yesterday at 10:00 am, etc.)
	 * 
	 * @return natural format of date
	 */
	public String toReadableString() {

		SelvEDate now = now();
		long diff = now.getTime().getTime() - this.getTime().getTime();

		// Just now
		if (Math.abs(diff) < ONE_MINUTE) {
			return LocalizationUtil.findLocalizedString("datedifference.now");
		}
		// Past
		else if (diff > 0) {
			// x minutes ago
			if (diff < ONE_HOUR) {
				int minutes = (int) (diff / ONE_MINUTE);
				return minutes > 1 ? LocalizationUtil.findLocalizedString("datedifference.past.minute.plural", minutes + "") : LocalizationUtil.findLocalizedString("datedifference.past.minute");
			}
			// x hours ago
			else if (diff < ONE_DAY) {
				// TODO - if this date has the time fields cleared, the result
				// should be 'today'
				if (!hasTimeSet())
					return LocalizationUtil.findLocalizedString("today");
				int hours = (int) (diff / ONE_HOUR);
				return hours > 1 ? LocalizationUtil.findLocalizedString("datedifference.past.hour.plural", hours + "") : LocalizationUtil.findLocalizedString("datedifference.past.hour", hours + "");
			}
			// Yesterday
			else if (isSameDay(this, now.addDays(-1))) {
				return LocalizationUtil.findLocalizedString("datedifference.yesterday");
			}
			// x days ago
			else if (diff < ONE_WEEK) {
				int days = (int) (diff / ONE_DAY);
				if (days == 1) {
					int hours = (int) (diff / ONE_HOUR);
					return LocalizationUtil.findLocalizedString("datedifference.past.hour.plural", hours + "");
				}
				return LocalizationUtil.findLocalizedString("datedifference.past.day.plural", days + "");
			}
			// last week
			else if (diff < ONE_WEEK * 2) {
				int days = (int) (diff / ONE_DAY);
				return days <= 8 ? LocalizationUtil.findLocalizedString("datedifference.past.week") : LocalizationUtil.findLocalizedString("datedifference.past.day.plural", days + "");
			}
			// x weeks ago
			else if ((int) (diff / ONE_WEEK) < 6) {
				int weeks = (int) (diff / ONE_WEEK);
				return LocalizationUtil.findLocalizedString("datedifference.past.week.plural", weeks + "");
			} else if ((int) (diff / ONE_WEEK) < 20) {
				return toFullMonthString();
			} else {
				return toMediumString();
			}
		}
		// Future
		else {
			diff = Math.abs(diff);

			// in x minutes
			if (diff < ONE_HOUR) {
				int minutes = (int) (diff / ONE_MINUTE);
				return minutes > 1 ? LocalizationUtil.findLocalizedString("datedifference.future.minute.plural", minutes + "") : LocalizationUtil.findLocalizedString("datedifference.future.minute");
			}
			// in x hours, or between tomorrow and x days
			else if (diff < ONE_DAY || (diff > ONE_DAY && diff < ONE_DAY * 2)) {
				int hours = (int) (diff / ONE_HOUR);
				return hours > 1 ? LocalizationUtil.findLocalizedString("datedifference.future.hour.plural", hours + "") : LocalizationUtil.findLocalizedString("datedifference.future.hour");
			}
			// tomorrow
			else if (isSameDay(this, now.addDays(1))) {
				return LocalizationUtil.findLocalizedString("datedifference.tomorrow");
			}
			// in x days
			else if (diff < ONE_WEEK) {
				int days = (int) (diff / ONE_DAY);
				return LocalizationUtil.findLocalizedString("datedifference.future.day.plural", days + "");
			}
			// last week
			else if (diff < ONE_WEEK * 2) {
				int days = (int) (diff / ONE_DAY);
				return days <= 8 ? LocalizationUtil.findLocalizedString("datedifference.future.week") : LocalizationUtil.findLocalizedString("datedifference.future.day.plural", days + "");
			}
			// x weeks ago
			else if ((int) (diff / ONE_WEEK) < 6) {
				int weeks = (int) (diff / ONE_WEEK);
				return LocalizationUtil.findLocalizedString("datedifference.future.week.plural", weeks + "");
			}
			// Just the date
			else {
				return toFullMonthString();
			}
		}
	}
}