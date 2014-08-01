//package test;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.jar.JarEntry;
//import java.util.jar.JarFile;
//import DC.Utility.Configuration;
//
//public class ClassLoad {
//	static URLClassLoader loader = null;
//
//	/**
//	 * 在默认的目录加载jar
//	 * 
//	 * @return
//	 */
//	public static URLClassLoader getClassLoad() {
//		Configuration config = new Configuration(Configuration.getRoot()
//				+ File.separator + "classpath.properties");
//		if (loader == null) {
//			URLClassLoaderUtil urlClass = new URLClassLoaderUtil(
//					config.getValue("classpath1"), false);
//			loader = urlClass.getClassLoader();
//		}
//		return loader;
//	}
//
//	/**
//	 * 在给定的路径加载jar文件
//	 * 
//	 * @param url
//	 *            指定路径
//	 * @param isFile
//	 *            true 文件 false 目录
//	 * @return
//	 */
//	public static URLClassLoader getClassLoad(String url, boolean isFile) {
//		URLClassLoaderUtil urlClass = new URLClassLoaderUtil(url, false);
//		URLClassLoader loader = urlClass.getClassLoader();
//		return loader;
//	}
//}