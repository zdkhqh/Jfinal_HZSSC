package com.hzvtc.hzssc.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final String DEFAULTPATTERN = "yyyy-MM-dd";
	private static final String NORMALPATTERN = "yyyy-MM-dd hh:mm:ss";

	/**
	 * 把日期字符串转为java.util.Date类型
	 */
	public static Date strToDate(String dateStr, String parttern)
			throws Exception {
		if (parttern == null || parttern.equals("")) {
			parttern = DEFAULTPATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(parttern);
		return sdf.parse(dateStr);
	}

	/**
	 * Date转字符串 精确到日
	 * 
	 * @return
	 */
	public static String getDateInstance(Date date) {
		DateFormat df1 = DateFormat.getDateInstance();// 日期格式，精确到日
		return df1.format(date);
	}

	/**
	 * Date转字符串 精确到时分秒
	 * 
	 * @return
	 */
	public static String getDateTimeInstance(Date date) {
		DateFormat df1 = DateFormat.getDateTimeInstance();// 日期格式，精确到日
		return df1.format(date);
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getBeforeDay(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getAfterDay(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}
/*public static void main(String[] args) {
	System.out.println(getAfterDay("2017-12-12"));
	System.out.println(getBeforeDay("2017-12-12"));
}*/
	/**
	 * 获取上个月第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfLastMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取上个月最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfLastMonth() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

		String dateStr = format.format(c.getTime());
		try {
			return strToDate(dateStr + " 23:59:59", "yyyy-MM-dd hh:mm:ss");
		} catch (Exception e) {
			return null;
		}

		/*
		 * c.set(Calendar.HOUR_OF_DAY, 23); c.set(Calendar.MINUTE, 59);
		 * c.set(Calendar.SECOND, 59); return c.getTime();
		 */
	}

	/**
	 * 获取日期的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取日期的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取上一年的年份
	 * 
	 * @return
	 */
	public static int getLastYear() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
		String dateStr = format.format(c.getTime());
		return dateStr;
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}
}
