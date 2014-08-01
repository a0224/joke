package com.joke.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.Gson;

public class Constant {

	/** log */
	private static Log log = LogFactory.getLog(Constant.class);
	/** 用户session **/
	public static final String USER_INF_SESSION_NAME = "user_inf";
	public static final String WALLPAPER_CATEGORYNAME = "wallpaper_categoryname";
	public static final String VALIDATE_STRING = "validateString";
	/** 上传文件地址 **/
	public static final String IMAGE_PATH = "/uploads/images";
	public static final String FILE_PATH = "/uploads/files";
	public static final String WALLPAPER_PATH = "/uploads/images/wallpaper";
	public static final String WALLPAPER_CATEGORY_PATH = "/uploads/images/wallpaperCategory";
	public static final String APK_PATH = "/uploads/apk";
	public static final String TEMP_PATH = "/uploads/temp";
	public static final String UPLOADS_FILE = "/uploads/uploadsFile";
	public static final String uploads_PATH = "/uploads";
	public static String PROJECT_PATH = "http://localhost:8080/manager";
	public static int totalProperty;
	public static Gson resultJson = new Gson();
	public static String apkEnName = "launcher";
	public static String apkChName = "云桌面";
	public static final String darkkinger = "darkkinger110";
	public static DecimalFormat decimalFormat = new DecimalFormat("#.##");
	public static final String picTypes = "png,jpg,gif,bmp,jpeg,PNG,JPG,GIF,BMP,JPEG";

	/** 角色 **/
	public static final int ADMIN = 1;
	public static final int CPIS = 2;
	public static final int DEVLEOPER = 3;
	public static final int FINANCE = 4;
	public static final int CHANNEL = 5;// 渠道商
	public static final int AIALSBUS = 8;// 子渠道商
	/** 状态 **/
	public static final int DELETE = -1;
	public static final int NORMAL = 1;
	public static final int RELEASE = 1;
	public static final int UNRELEASE = 1;
	public static final int FORMAL = 1;
	public static final int UNFORMAL = 2;

	public static final int CLOUD = 1;
	public static final int PRIVAE = 2;

	public static final String PUBLISH = "1";
	public static final String UNPUBLISH = "2";

	public static final String push_link = "1";
	public static final String push_text = "2";
	public static final String push_quiet = "3";
	public static final String push_unquiet1 = "4";
	public static final String push_unquiet2 = "5";

	public static final String pushFlag_adv = "1";
	public static final String pushFlag_text = "2";
	public static final String pushFlag_link = "3";

	public static final int recommend = 1;
	public static final int unRecommend = 2;

	public static final int X = 3;
	public static final int Y = 3;

	public static final String Channel = "0";
	public static final String OpenType = "1";
	public static final String FirstReceiveTimeUnit = "1";
	public static final String FirstrecunitTimetype = "2";
	public static final String HeartbeatTime = "300";
	public static final String DayInterval = "1";
	public static final String MaxReceiveNum = "5";
	public static final String AiirpushUnittimeType = "3";
	public static final String Aiirpush_unittime = "30";

	public static int uploadsApkisLock = 0;

	/**  **/
	public static Properties properties = new Properties();
	public static Properties props;
	public static String osName = System.getProperty("os.name");

	// 属性文件的路径
	// static String profilepath = "mail.properties";

	public Constant() {
		getParam();
	}

	public static Properties getParam() {
		return properties;
	}

	static {
		try {
			log.debug("osName-------" + osName);
			if (osName.toLowerCase().contains("linux")) {
				InputStream in = Constant.class
						.getResourceAsStream("Constant_linux.properties");
				properties.load(in);
			} else {
				InputStream in = Constant.class
						.getResourceAsStream("Constant_win.properties");
				properties.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
			System.exit(-1);
		}
	}

	/**
	 * 根据主键key读取主键的值value
	 * 
	 * @param filePath
	 *            属性文件路径
	 * @param key
	 *            键名
	 */
	public static String readValue(String key) {
		try {
			// if (osName.toLowerCase().contains("linux")) {
			// InputStream in = Constant.class
			// .getResourceAsStream("Constant_linux.properties");
			// properties.load(in);
			// } else {
			// InputStream in = Constant.class
			// .getResourceAsStream("Constant_win.properties");
			// properties.load(in);
			// }
			String value = properties.getProperty(key);
			log.info(key + "键的值是：" + value);
			return value;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新（或插入）一对properties信息(主键及其键值) 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public static void writeProperties(String keyname, String keyvalue)
			throws IOException {
		// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
		// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
		OutputStream fos = null;
		if (osName.toLowerCase().contains("linux")) {
			fos = new FileOutputStream(Constant.class.getResource(
					"Constant_linux.properties").getPath());
		} else {
			fos = new FileOutputStream(Constant.class.getResource(
					"Constant_win.properties").getPath());
		}

		properties.setProperty(keyname, keyvalue);
		// 以适合使用 load 方法加载到 Properties 表中的格式，
		// 将此 Properties 表中的属性列表（键和元素对）写入输出流
		properties.store(fos, "Update '" + keyname + "' value");
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public void updateProperties(String keyname, String keyvalue) {
		try {

			OutputStream fos = null;
			if (osName.toLowerCase().contains("linux")) {
				fos = new FileOutputStream("Constant_linux.properties");
			} else {
				fos = new FileOutputStream("Constant_win.properties");
			}

			if (osName.toLowerCase().contains("linux")) {
				fos = new FileOutputStream(Constant.class.getResource(
						"Constant_linux.properties").getPath());
			} else {
				fos = new FileOutputStream(Constant.class.getResource(
						"Constant_win.properties").getPath());
			}
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			properties.setProperty(keyname, keyvalue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			properties.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			log.error(e);
			log.error("属性文件更新错误");
		}
	}

	public static boolean conEndName(String fileName) {
		return picTypes.contains(fileName.substring(
				fileName.lastIndexOf(".") + 1, fileName.length()));
	}

	// 测试代码
	public static void main(String[] args) {
		// System.out.println(readValue("apktoolDir"));
		// System.out.println(getParam().get("apktoolDir"));
		// writeProperties("MAIL_SERVER_INCOMING", "327@qq.com");
		// log.info("操作完成");

		String fileName = "123.jpg";
		String end = fileName.substring(fileName.lastIndexOf("."),
				fileName.length());
		System.out.print(end);
		System.out.print(picTypes.contains(fileName.substring(
				fileName.lastIndexOf(".") + 1, fileName.length())));
	}
}

// public static void main(String[] str) {
// Constant.getParam();
// System.out.println(Constant.properties.getProperty("1"));
// }

// }
