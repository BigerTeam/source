package com.source.utils.train;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateFormatCHN = new SimpleDateFormat("yyyy年MM月dd日");
	public static SimpleDateFormat dateFormatC = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

	public static TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
	public static final int EPOCH_MODIFIED_JD = 2400000 + 1;

	public static Date string2Date(String dateString) {
		Date date = null;
		try {
			if (dateString == null || "".equals(dateString))
				return null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			date = df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getYearMonthString(Timestamp time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(time);
	}

	public static String getYearMonthDayString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}

	public static String getYearMonthString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(date);
	}

	public static String getYearString(Timestamp ts) {
		return yearFormat.format(ts);
	}

	public static int getCurrentYear() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return Integer.parseInt(getYearString(ts));
	}

	public static int getCurrentYearMonthInt() {
		return Integer.valueOf(getYearMonthString(new Date()));
	}

	public static String getTimeString(Timestamp time) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(time);
	}

	public static String getYearWeekString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyww");
		return df.format(date);
	}

	public static String getYearWeekString(Timestamp time) {
		DateFormat df = new SimpleDateFormat("yyyyww");
		return df.format(time);
	}

	public static String getDateString(Timestamp time) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(time);
	}

	public static String getDateHourString(Timestamp time) {
		DateFormat df = new SimpleDateFormat("yyyyMMddHH");
		return df.format(time);
	}

	public static String getCurrentDateString() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(getCurrentTime());
	}

	public static String getCurrentShortDateString() {
		return getShortDateString(getCurrentTime());
	}

	public static String getShortDateString(Timestamp ts) {
		DateFormat df = new SimpleDateFormat("yyMMdd");
		return df.format(ts);
	}

	public static String getCurrentTimeString() {
		DateFormat df = new SimpleDateFormat("HHmm");
		return df.format(getCurrentTime());
	}

	public static String getDateTimeString(Timestamp time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(time);
	}

	public static int days(Date from, Date to) {
		return (int) (((to.getTime() - from.getTime()) / 1000) / 86400);
	}

	public static int daysFrom(java.sql.Timestamp time) {
		int offset = Calendar.getInstance(DEFAULT_TIME_ZONE).get(Calendar.ZONE_OFFSET);
		return (int) ((System.currentTimeMillis() + offset) / TimeUnit.DAYS.toMillis(1)
				- (time.getTime() + offset) / TimeUnit.DAYS.toMillis(1));
	}

	public static long dayStart() {
		return dayStart(0);
	}

	public static long dayStart(int afterDays) {
		Calendar cal = Calendar.getInstance(DEFAULT_TIME_ZONE);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		long dayStart = cal.getTimeInMillis() / 1000 * 1000 + TimeUnit.DAYS.toMillis(afterDays);
		return dayStart;
	}

	// public static java.sql.Timestamp earlistTime()
	// {
	// return new java.sql.Timestamp(1000);
	// }

	public static boolean isToday(Timestamp time) {
		if (time == null)
			return false;

		long start = dayStart();
		long diff = time.getTime() - start;
		return diff >= 0 && diff < TimeUnit.DAYS.toMillis(1);
	}

	public static boolean isSameDay(Timestamp t1, Timestamp t2) {
		return getDateString(t1).equals(getDateString(t2));
	}

	public static boolean isSameMonth(Timestamp time) {
		if (time == null)
			return false;
		Calendar now = Calendar.getInstance(DEFAULT_TIME_ZONE);
		Calendar last = Calendar.getInstance(DEFAULT_TIME_ZONE);
		last.setTimeInMillis(time.getTime());
		return last.get(Calendar.MONTH) == now.get(Calendar.MONTH) && last.get(Calendar.YEAR) == now.get(Calendar.YEAR);
	}

	public static long monthStart() {
		Calendar cal = Calendar.getInstance(DEFAULT_TIME_ZONE);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTimeInMillis() / 1000 * 1000;
	}

	public static long monthEnd() {
		Calendar cal = Calendar.getInstance(DEFAULT_TIME_ZONE);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		long nextMonthStart = cal.getTimeInMillis() / 1000 * 1000;
		System.out.println("nextMonthStart:" + getDateTimeString(new Timestamp(nextMonthStart)));
		return nextMonthStart - TimeUnit.MILLISECONDS.toMillis(1);
	}

	public static int secondsToNextDay() {
		return (int) (dayStart(1) / 1000 - System.currentTimeMillis() / 1000);
	}

	// public static String today()
	// {
	// return timestamp(0).toString().substring(0, 10);
	// }
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	//
	// public static String toTimeString(long millis)
	// {
	// int totalSeconds = (int) (millis / 1000);
	// if (totalSeconds <= 0)
	// return "0秒";
	// int weeks = totalSeconds / WEEK;
	// totalSeconds %= WEEK;
	// int days = totalSeconds / DAY;
	// totalSeconds %= DAY;
	// int hours = totalSeconds / HOURS;
	// totalSeconds %= HOURS;
	// int minute = totalSeconds / MINUTE;
	// totalSeconds %= MINUTE;
	// String result = "";
	// if (weeks > 0)
	// {
	// result += weeks + "周";
	// }
	// if (days > 0)
	// {
	// result += days + "天";
	// }
	// if (hours > 0)
	// {
	// result += hours + "小时";
	// }
	// if (minute > 0)
	// {
	// result += minute + "分";
	// }
	// if (totalSeconds > 0)
	// {
	// result += totalSeconds + "秒";
	// }
	// return result;
	// }

	/**
	 * 计算给定时间的周一凌晨零点对应的时间
	 * 
	 * @param now
	 * @return
	 */
	public static long getWeekStart(long now) {
		Calendar cal = Calendar.getInstance(DEFAULT_TIME_ZONE);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		long dayStart = cal.getTimeInMillis() / 1000 * 1000;
		return dayStart;
	}

	/**
	 * 计算给定时间是本周的第几天
	 * 
	 * @param now
	 * @return 周一是第1天
	 */
	public static int getDaysFromMonday(long now) {
		Calendar cal = Calendar.getInstance(DEFAULT_TIME_ZONE);
		cal.setTimeInMillis(now);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek > Calendar.SUNDAY) {
			return dayOfWeek - 1;
		}
		return 7;
	}

	/**
	 * 根据字符串获取Timestamp对象
	 * 
	 * @param time
	 *            时间字符串，格式为"yyyy-MM-dd HH:mm:ss"
	 * @return null 转换失败
	 */
	public static java.sql.Timestamp timeFromString(String time) {
		Date date;
		try {
			date = dateFormat.parse(time);
			return new java.sql.Timestamp(date.getTime());
		} catch (Exception e) {
			// logger.error("Illegal time \"" + time + "\"", e);
			return null;
		}
	}

	/**
	 * 根据时间字符串获取Timestamp对象
	 * 
	 * @param time
	 *            时间字符串，格式为"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static java.sql.Timestamp timestamp(String time) {
		return java.sql.Timestamp.valueOf(time);
	}

	/**
	 * 时间格式为yyyy年mm月dd日
	 * 
	 * @return
	 */
	public static String todayCHN(Timestamp ts) {
		return dateFormatCHN.format(ts);
	}

	/**
	 * 时间格式为yyyy年mm月dd日
	 * 
	 * @return
	 */
	public static String toDayCHN(Date ts) {
		return dateFormatC.format(ts);
	}

	/**
	 * 计算给定时间的最近一个周期的开始值，支持小时，天，星期
	 * 
	 * @param delay
	 *            （小时）
	 * @param curTime
	 * @return (毫秒)
	 */
	public static long getLatestPhaseStart(int delay, long curTime) {
		Calendar calendar = Calendar.getInstance(DEFAULT_TIME_ZONE);
		int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
		if (delay == 7 * 24) {
			// 针对星期进行修正，1970.01.01是周四
			return ((curTime + TimeUnit.DAYS.toMillis(3) + zoneOffset) / TimeUnit.HOURS.toMillis(delay))
					* TimeUnit.HOURS.toMillis(delay) - TimeUnit.DAYS.toMillis(3) - zoneOffset;
		} else if (delay == 24) {
			return ((curTime + zoneOffset) / TimeUnit.HOURS.toMillis(delay)) * TimeUnit.HOURS.toMillis(delay)
					- zoneOffset;
		} else if (delay > 0 && delay < 24) {
			return (curTime / TimeUnit.HOURS.toMillis(delay)) * TimeUnit.HOURS.toMillis(delay);
		} else {
			throw new IllegalArgumentException("周期不支持" + delay + "小时");
		}
	}

	/**
	 * 根据周期计算给定时间属于1970年以来第几阶段,支持小时，天，星期
	 * 
	 * @param delay
	 *            （小时）
	 * @param curTime
	 * @return
	 */
	public static int phaseOf1970(int delay, long curTime) {
		Calendar calendar = Calendar.getInstance(DEFAULT_TIME_ZONE);
		int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);

		if (delay == 7 * 24) {
			// 针对星期进行修正，1970.01.01是周四
			return (int) ((getLatestPhaseStart(delay, curTime) - TimeUnit.DAYS.toMillis(3) - zoneOffset)
					/ TimeUnit.HOURS.toMillis(delay));
		} else if (delay == 24) {
			return (int) (getLatestPhaseStart(delay, curTime) / TimeUnit.HOURS.toMillis(delay));
		} else if (delay > 0 && delay < 24) {
			return (int) (getLatestPhaseStart(delay, curTime) / TimeUnit.HOURS.toMillis(delay));
		} else {
			throw new IllegalArgumentException("周期不支持" + delay + "小时");
		}
	}

	public static boolean isCurrentPhase(int delay, Date date) {
		if (date == null) {
			return false;
		}
		long start = getLatestPhaseStart(delay, System.currentTimeMillis());
		int diff = (int) ((date.getTime() - start));
		return diff >= 0 && diff < TimeUnit.HOURS.toMillis(delay);
	}

	public static boolean isLastPhase(int delay, Date date) {
		if (date == null) {
			return false;
		}
		long start = getLatestPhaseStart(delay, System.currentTimeMillis());
		int diff = (int) ((date.getTime() - start));
		return diff < 0 && diff >= -TimeUnit.HOURS.toMillis(delay);
	}

	public static int calculateJulianDayNumberAtNoon(Timestamp ts) {
		// http://www.hermetic.ch/cal_stud/jdn.htm
		Calendar c = Calendar.getInstance(DEFAULT_TIME_ZONE);
		c.setTime(ts);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		return (1461 * (y + 4800 + (m - 14) / 12)) / 4 + (367 * (m - 2 - 12 * ((m - 14) / 12))) / 12
				- (3 * ((y + 4900 + (m - 14) / 12) / 100)) / 4 + d - 32075;
	}

	public static int calculateJulianDayNumberAtMidnight(Timestamp ts) {
		return calculateJulianDayNumberAtNoon(ts) - EPOCH_MODIFIED_JD;
	}

	public static int numDaysFrom(Timestamp ts1, Timestamp ts2) {
		// ts1 is smaller
		// ts2 is bigger
		return calculateJulianDayNumberAtMidnight(ts2) - calculateJulianDayNumberAtMidnight(ts1);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int numDaysFrom(Date date1, Date date2) {
		Timestamp t1 = new Timestamp(date1.getTime());
		Timestamp t2 = new Timestamp(date2.getTime());
		return numDaysFrom(t1, t2);
	}

	public static int getNextYearMonthInt() {
		Date date = new Date(monthEnd() + TimeUnit.MILLISECONDS.toMillis(2));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return Integer.valueOf(df.format(date));
	}

	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date getPayDate(String time) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		if (CheckUtils.isNotNull(time)) {
			date = fmt.parse(time);
		}
		return date;
	}

	/**
	 * 得到当前月的第一天
	 * 
	 * @return
	 * @author rudong
	 */
	public static Date getCurrentMonthFirstDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 得到当前月的第一天
	 * 
	 * @return
	 * @author rudong
	 */
	public static Date getCurrentMonthFirstDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 得到当前月的最后一天
	 * 
	 * @return
	 * @author rudong
	 */
	public static Date getCurrentMonthLastDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 得到当前月的最后一天
	 * 
	 * @return
	 * @author rudong
	 */
	public static Date getCurrentMonthLastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static boolean judgeSettleDate(Date fromDate, Date toDate, int period) {
		Calendar cal_from = Calendar.getInstance();
		cal_from.setTime(fromDate);
		Calendar cal_to = Calendar.getInstance();
		cal_to.setTime(toDate);
		cal_to.add(Calendar.DAY_OF_MONTH, -period);
		while (cal_from.before(cal_to)) {
			cal_from.add(Calendar.DAY_OF_MONTH, period);
		}
		// 打印出来的是今天结算日期
		System.out.println("下次结算开始日期：" + cal_from.get(Calendar.YEAR) + "-" + cal_from.get(Calendar.MONTH) + "-"
				+ cal_from.get(Calendar.DAY_OF_MONTH));
		// 打印出来的是上次结算日期
		System.out.println("上次结算日期：" + cal_to.get(Calendar.YEAR) + "-" + cal_to.get(Calendar.MONTH) + "-"
				+ cal_to.get(Calendar.DAY_OF_MONTH));
		return cal_from.get(Calendar.DAY_OF_MONTH) == cal_to.get(Calendar.DAY_OF_MONTH) + period;
	}

	/**
	 * 获取yyyy-MM-dd的标准时间
	 * 
	 * @param date
	 * @return
	 * @author rudong
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 获取yyyy-MM-dd HH:mm:ss的标准时间
	 * 
	 * @param date
	 * @return
	 * @author rudong
	 */
	public static String getDateLongString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date addDateTime(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	public static boolean checkWeekDate(String week, Date date) {
		int weekday = dayForWeek(date);
		return week.charAt(weekday - 1) == '1';
	}

	/**
	 * 勾金波
	 * 
	 * 2014年9月16日10:19:53 添加 Utils 类
	 */

	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy年M月dd日 H点m分s秒
	 */
	public static final String CNLONG_DATE_PATTERN = "yyyy年M月dd日 H点m分s秒";

	/**
	 * yyyy年M月dd日
	 */
	public static final String CNSHORT_DATE_PATTERN = "yyyy年M月dd日";

	/**
	 * yyyyMMdd
	 */
	public static final String DATE_PATTERN_PLAIN = "yyyyMMdd";

	public static final String YEAR_PATTERN_PLAIN = "yyyy";
	/** 1:春 ，2:夏 ，3：秋 ，4：冬天 */
	public static final int SEASON_SPRING = 1;
	/** 1:春 ，2:夏 ，3：秋 ，4：冬天 */
	public static final int SEASON_SUMMER = 2;
	/** 1:春 ，2:夏 ，3：秋 ，4：冬天 */
	public static final int SEASON_AUTUMN = 3;
	/** 1:春 ，2:夏 ，3：秋 ，4：冬天 */
	public static final int SEASON_WINTER = 4;

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String LONG_DATE_PATTERN_PLAIN = "yyyyMMddHHmmss";

	private static final java.text.SimpleDateFormat dateFormatG = new java.text.SimpleDateFormat(DATE_PATTERN);
	private static final java.text.SimpleDateFormat longDateFormat = new java.text.SimpleDateFormat(LONG_DATE_PATTERN);
	private static final java.text.SimpleDateFormat cnLongDateFormat = new java.text.SimpleDateFormat(
			CNLONG_DATE_PATTERN);

	/**
	 * 格式：yyyy-MM-dd
	 */
	public static String format(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = dateFormatG.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;
	}

	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String longDate(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = longDateFormat.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;
	}

	/**
	 * 格式：yyyy年MM月dd日 H点m分s秒
	 */
	public static String cnLongDate(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = cnLongDateFormat.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;
	}

	/**
	 * 将时间转化成指定格式
	 * 
	 * @param format
	 * @param v
	 * @return
	 */
	public static String formatDate(String format, Object v) {
		if (v == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(v);
	}

	/**
	 * 时间字符串转日期
	 * 
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateTime, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断日期是否为月末
	 * 
	 * @param currDate
	 * @return true月末,false不是月末
	 */
	public static boolean isEndOfMonth(Date currDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(currDate);
		if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取前一天
	 */
	public static Date getYesTaday() {
		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.MONTH) == 0 && cal.get(Calendar.DAY_OF_YEAR) == 1) {
			cal.roll(Calendar.YEAR, -1);
		}
		cal.roll(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}

	/**
	 * 得到指定 增加或者减去 天数 Date类型
	 */
	public static Date getAppointDate(Date date, int daysum) {
		/***
		 * Calendar cal = Calendar.getInstance(); cal.setTime(date);
		 * cal.roll(Calendar.DAY_OF_YEAR, daysum); return cal.getTime();
		 ***/

		// 日期格式
		// SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Calendar calstart = java.util.Calendar.getInstance();
		calstart.setTime(date);
		calstart.add(java.util.Calendar.DAY_OF_WEEK, daysum);
		return calstart.getTime();
	}

	/**
	 * 得到指定 增加或者减去 天数 String类型
	 */
	public static String getAppointDateString(Date date, int daysum) {
		return longDate(getAppointDate(date, daysum));
	}

	/***
	 * 得到两个日期相差的天数 *
	 ****/
	public static long getQuot(Date date1, Date date2) {
		long quot = 0;
		quot = date1.getTime() - date2.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		return Math.abs(quot);
	}

	/**
	 * 传入Date类型 获得指定格式的 Date数据 yyyy-MM-dd
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getDateByType(Date date, String format) {
		return DateUtils.parseDate(DateUtils.longDate(date), format);
	}

	/**
	 * 获取当前日期所在周指定的一天 GXY Oct 19, 2010 11:04:35 AM
	 * 
	 * @param dayNo
	 *            日期所在的序号从1到7
	 * @param flag
	 *            true: 星期天为本周的开始时间 false: 星期一为本周的开始时间
	 */
	public static Date getThisWeekDay(int dayNo, boolean flag) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(flag ? Calendar.SUNDAY : Calendar.MONDAY);
		c.add(Calendar.DATE, -(c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek() + 1) + dayNo);
		return c.getTime();
	}

	/**
	 * 根据用户生日计算年龄
	 */
	public static Integer getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (null == birthday) {
			return null;
		}

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 得到时间数组里面的最大时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMaxDate(ArrayList<Date> dates) {
		if (dates.isEmpty()) {
			return null;
		}
		Date dateFirst = null;
		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i) != null) {
				dateFirst = dates.get(i);
				break;
			}
		}

		ArrayList<Date> deleteDate = new ArrayList<Date>();
		for (int i = 1; i < dates.size(); i++) {
			if (dates.get(i) == null) {
				deleteDate.add(null);
			} else if (dates.get(i).before(dateFirst)) {
				deleteDate.add(dates.get(i));
			}
		}
		if (deleteDate.isEmpty()) {
			if (dates.size() == 1) {
				return dates.get(0);
			}
			dates.remove(dateFirst);
		} else {
			dates.removeAll(deleteDate);
		}
		return getMaxDate(dates);
	}

	/**
	 * 获取传入日期之前的月日期
	 * 
	 * @param date
	 *            传入日期
	 * @param before
	 *            前几个月
	 * @return 返回第前几个月的日期值
	 */
	public static Date getBeforeMonth(String date, int before) {
		Date d = null;
		if (0 == before) {
			d = DateUtils.parseDate(date, DateUtils.DATE_PATTERN);
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseDate(date, DateUtils.DATE_PATTERN));
			c.add(Calendar.MONTH, before);
			d = c.getTime();
		}
		return d;
	}

	/**
	 * 获取传入日期之前的月日期
	 * 
	 * @param date
	 *            传入日期
	 * @param before
	 *            前几个天
	 * @return 返回第前几个天的日期值
	 */
	public static Date getBeforeDay(String date, int before) {
		Date d = null;
		if (0 == before) {
			d = DateUtils.parseDate(date, DateUtils.DATE_PATTERN);
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseDate(date, DateUtils.DATE_PATTERN));
			c.add(Calendar.DAY_OF_YEAR, before);
			d = c.getTime();
		}
		return d;
	}

	/**
	 * 季节：春夏秋冬
	 * 
	 * @param args
	 */
	public static int getSeasonInt(Date date) {
		String nowYear = DateUtils.formatDate("yyyy", date);

		Date date1 = DateUtils.parseDate(nowYear + "-02-04", "yyyy-MM-dd");
		Date date2 = DateUtils.parseDate(nowYear + "-05-05", "yyyy-MM-dd");
		Date date3 = DateUtils.parseDate(nowYear + "-08-07", "yyyy-MM-dd");
		Date date4 = DateUtils.parseDate(nowYear + "-11-07", "yyyy-MM-dd");

		if (date.before(date1)) {
			return SEASON_WINTER;// 冬
		} else if (date.before(date2)) {
			return SEASON_SPRING;// 春
		} else if (date.before(date3)) {
			return SEASON_SUMMER;// 夏
		} else if (date.before(date4)) {
			return SEASON_AUTUMN;// 秋
		} else {
			return SEASON_WINTER;// 冬
		}
	}

	/**
	 * @param date可以使当前日期
	 *            ,也可以是别的日期
	 * @return 返回数字对应0:星期天 ，1：星期一， 2：星期二， 3：星期三， 4：星期四， 5：星期五， 6：星期六
	 */
	public static int getWeekOfDate(Date date) {
		int[] weekDays = { 0, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 判断时间格式
	 * 
	 * @param str
	 */
	public static boolean isValidDate(String str, String format) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat date = new SimpleDateFormat(format);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			date.setLenient(false);
			date.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 判断输入的时间是否在当前时间之后，
	 * 
	 * @param time
	 *            时间 格式支持 yyyy-MM-dd HH:mm:ss ， yyyy-MM-dd ， HH:mm:ss
	 * @return 输入时间在当前时间之后返回true ，否则返回 false
	 */
	public static boolean isOutNowTime(String time) {
		boolean boll = false;
		if (isValidDate(time, "HH:mm:ss")) {
			Date nowDate = DateUtils.parseDate(DateUtils.formatDate("HH:mm:ss", new Date()), "HH:mm:ss");
			if (parseDate(time, "HH:mm:ss").after(nowDate))
				boll = true;
		}
		if (isValidDate(time, "yyyy-MM-dd HH:mm:ss")) {
			Date nowDate = DateUtils.parseDate(DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", new Date()),
					"yyyy-MM-dd HH:mm:ss");
			if (parseDate(time, "yyyy-MM-dd HH:mm:ss").after(nowDate))
				boll = true;
		}
		if (isValidDate(time, "yyyy-MM-dd")) {
			Date nowDate = DateUtils.parseDate(DateUtils.formatDate("yyyy-MM-dd", new Date()), "yyyy-MM-dd");
			if (parseDate(time, "yyyy-MM-dd").after(nowDate))
				boll = true;
		}
		return boll;
	}
	public static String getGmtDateString(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d yyyy 00:00:00 'GMT+0800 (中国标准时间)'",Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(addDateTime(string2Date(date), 1));
	}
}
