package test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class OutJavaRun {

	public static void main(String[] args) throws MalformedURLException {

		String class_path = "com.joke.MyJokeUtile";// Jar中的所需要加载的类的类名

		String jar_path = "file://F:/Style.jar";// jar所在的文件的URL
		File file = new File("F:/MyJokeUtile.jar");
		URL url = file.toURL();

		try {

			ClassLoader cl = new URLClassLoader(new URL[] { url });// 从Jar文件得到一个Class加载器
			// ClassLoader cl = new URLClassLoader(new URL[]{new
			// URL(jar_path)});//从Jar文件得到一个Class加载器
			Class<?> c = cl.loadClass(class_path);// 从加载器中加载Class

			// JokeUtile sif = (JokeUtile) c.newInstance();// 从Class中实例出一个对象
			//
			// System.out.println(sif.testNg("nimei"));// 调用Jar中的类方法
			// System.out.println(sif.getJokeList("http://ic.snssdk.com/2/essay/v3/recent/?count=100",1));//
			// 调用Jar中的类方法

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

}
