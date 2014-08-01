package com.joke.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {

	/** log */
	private static Log log = LogFactory.getLog(DateUtil.class);

	public static String GetNowDate() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public static String GetNowTime() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public static String GetDateTime() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public static Timestamp GetNowDateTime() {
		Date timeDate = new Date();
		return new Timestamp(timeDate.getTime());
	}

	public static Date GetDateTimeByString(String dateString) {
		// Timestamp dateTime = null;
		Date timeDate = null;
		try {
			DateFormat dateFormat;
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.CHINA);// 设定格式
			// dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss",
			// Locale.ENGLISH);
			dateFormat.setLenient(false);
			timeDate = dateFormat.parse(dateString);
			// dateTime = new Timestamp(timeDate.getTime());
		} catch (ParseException e) {
			log.info("时间格式不正确");
			log.info(e);
			e.printStackTrace();
		}// util类型
			// Timestamp类型,timeDate.getTime()返回一个long型

		return timeDate;
	}

	public static Date GetDateByString(String dateString) {
		// Timestamp dateTime = null;
		Date timeDate = null;
		try {
			DateFormat dateFormat;
			dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// 设定格式
			// dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss",
			// Locale.ENGLISH);
			dateFormat.setLenient(false);

			timeDate = dateFormat.parse(dateString);
			// dateTime = new Timestamp(timeDate.getTime());
		} catch (ParseException e) {
			log.info("时间格式不正确");
			log.info(e);
			e.printStackTrace();
		}// util类型
			// Timestamp类型,timeDate.getTime()返回一个long型

		return timeDate;
	}

	public static String fileName() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public static String filePath(String file, String type) {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		int dom = cal.get(Calendar.DAY_OF_MONTH);
		int doy = cal.get(Calendar.DAY_OF_YEAR);
		StringBuffer buff = new StringBuffer();
		buff.append(file);
		buff.append("/").append(Constant.getParam().getProperty(type));
		buff.append("/").append(year).append("/").append(month).append("/")
				.append(day);
		return buff.toString();
	}

	public static String filePath(String file) {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		int dom = cal.get(Calendar.DAY_OF_MONTH);
		int doy = cal.get(Calendar.DAY_OF_YEAR);

		StringBuffer buff = new StringBuffer();
		buff.append(file);
		buff.append("/").append(year).append("/").append(month).append("/")
				.append(day);
		return buff.toString();
	}

	public static String fileName(String name, String type) {
		StringBuffer buff = new StringBuffer();
		buff.append(name).append("_")
				.append(Constant.getParam().getProperty(type));
		return buff.toString();
	}

	public static String dateTime() {
		return String.valueOf(new Date().getTime());
	}

	public static String getAbsolutePathWithClass() {
		return Class.class.getClass().getResource("/").getPath();
	}

	/**
	 * @return 以当前时间生成编号
	 */
	public static String generateCode() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				.toString();
	}

	public static Integer getDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}

}
