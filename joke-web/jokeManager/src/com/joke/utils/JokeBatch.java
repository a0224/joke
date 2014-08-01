package com.joke.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joke.pojo.JarPojo;
import com.joke.pojo.JokePojo;
import com.joke.pojo.NewsPojo;
import com.joke.pojo.SexyPojo;
import com.joke.service.JokeService;

public class JokeBatch {

	/** log */
	private static Log log = LogFactory.getLog(JokeService.class);

	public static List<JokePojo> getJokeUtil(JarPojo jar, String url,
			Integer userId) {
		List<JokePojo> jokePlist = null;
		try {

			String class_path = jar.getClassName();// Jar中的所需要加载的类的类名
			File file = new File(Constant.readValue("data") + jar.getUrl());
			URL jarPath = file.toURL();

			ClassLoader cl = new URLClassLoader(new URL[] { jarPath });// 从Jar文件得到一个Class加载器
			// ClassLoader cl = new URLClassLoader(new URL[]{new
			// URL(jar_path)});//从Jar文件得到一个Class加载器
			Class<?> c = cl.loadClass(class_path);// 从加载器中加载Class

			Object sif = c.newInstance();// 从Class中实例出一个对象
			Method me = c.getDeclaredMethod("getJokeList", String.class,
					Integer.class);

			String jsonStr = (String) me.invoke(sif, url, userId);
			Gson gson = new Gson();

			jokePlist = gson.fromJson(jsonStr, new TypeToken<List<JokePojo>>() {
			}.getType());

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		}

		return jokePlist;
	}

	public static List<NewsPojo> getNewsUtil(JarPojo jar, String url) {
		List<NewsPojo> newsPlist = null;
		try {

			String class_path = jar.getClassName();// Jar中的所需要加载的类的类名
			// File file = new File(Constant.readValue("data") + jar.getUrl());
			File file = new File(Constant.readValue("data") + jar.getUrl());
			URL jarPath = file.toURL();

			ClassLoader cl = new URLClassLoader(new URL[] { jarPath });// 从Jar文件得到一个Class加载器
			// ClassLoader cl = new URLClassLoader(new URL[]{new
			// URL(jar_path)});//从Jar文件得到一个Class加载器
			Class<?> c = cl.loadClass(class_path);// 从加载器中加载Class

			Object sif = c.newInstance();// 从Class中实例出一个对象
			Method me = c.getDeclaredMethod("getNewList", String.class,
					String.class);

			String jsonStr = (String) me.invoke(sif, url, "");
			Gson gson = new Gson();

			newsPlist = gson.fromJson(jsonStr, new TypeToken<List<NewsPojo>>() {
			}.getType());

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		}

		return newsPlist;
	}

	public static List<SexyPojo> getSexyUtil(JarPojo jar, String url) {
		List<SexyPojo> relist = null;
		try {

			String class_path = jar.getClassName();// Jar中的所需要加载的类的类名
			// File file = new File(Constant.readValue("data") + jar.getUrl());
			File file = new File(Constant.readValue("data") + jar.getUrl());
			URL jarPath = file.toURL();

			ClassLoader cl = new URLClassLoader(new URL[] { jarPath });// 从Jar文件得到一个Class加载器
			// ClassLoader cl = new URLClassLoader(new URL[]{new
			// URL(jar_path)});//从Jar文件得到一个Class加载器
			Class<?> c = cl.loadClass(class_path);// 从加载器中加载Class

			Object sif = c.newInstance();// 从Class中实例出一个对象
			Method me = c.getDeclaredMethod("getSexyList", String.class,
					String.class);

			String jsonStr = (String) me.invoke(sif, url, "");
			Gson gson = new Gson();

			relist = gson.fromJson(jsonStr, new TypeToken<List<SexyPojo>>() {
			}.getType());

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		}

		return relist;
	}

	public static void loadJar() {
		try {

			String class_path = "com.joke.util.imp.AaaTest";// Jar中的所需要加载的类的类名
			File file = new File("F:/aaTest.jar");
			URL jarPath = file.toURL();

			ClassLoader cl = new URLClassLoader(new URL[] { jarPath });// 从Jar文件得到一个Class加载器
			Class<?> c = cl.loadClass(class_path);// 从加载器中加载Class
			Object sif = c.newInstance();// 从Class中实例出一个对象
			Method me = c.getDeclaredMethod("aaTest", String.class);
			String reStr = (String) me.invoke(sif, "哈哈 成功了!!");
			System.out.println("loadJar---" + reStr);

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			System.out.println(e);

		}
	}

	public static void loadJarInte() {
		try {
			loadJarInte1();
			String class_path = "com.joke.util.imp.AaaTest";// Jar中的所需要加载的类的类名
			File file = new File("F:/aaTest.jar");
			URL jarPath = file.toURL();

			ClassLoader cl = new URLClassLoader(new URL[] { jarPath });// 从Jar文件得到一个Class加载器
			Class<?> c = cl.loadClass(class_path);// 从加载器中加载Class
			Object sif = c.newInstance();// 从Class中实例出一个对象
			Method me = c.getDeclaredMethod("aaTest", String.class);
			String reStr = (String) me.invoke(sif, "哈哈 成功了!!");
			System.out.println("loadJar---" + reStr);

			// Test ttt = (Test) c.newInstance();// 从Class中实例出一个对象
			// String rrr = ttt.aaTest("你妹 不错啊!!!!!");
			// System.out.println("Test---" + rrr);

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			System.out.println(e);

		}
	}

	public static void loadJarInte1() {
		try {

			String class_path = "com.joke.MyJokeUtile";// Jar中的所需要加载的类的类名
			File file = new File("F:/MyJokeUtile.jar");
			URL jarPath = file.toURL();

			ClassLoader cl = new URLClassLoader(new URL[] { jarPath });// 从Jar文件得到一个Class加载器
			Class<?> c = cl.loadClass(class_path);// 从加载器中加载Class
			Object sif = c.newInstance();// 从Class中实例出一个对象
			Method me = c.getDeclaredMethod("getJokeListTest");
			String reStr = (String) me.invoke(sif);
			System.out.println("loadJar---" + reStr);

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			System.out.println(e);

		}
	}

	public static void main(String[] str) {
		loadJarInte();
	}

}
