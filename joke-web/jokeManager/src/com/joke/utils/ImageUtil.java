package com.joke.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

	/** log */
	private static Log log = LogFactory.getLog(ImageUtil.class);

	/**
	 * 切割图片
	 * 
	 * @param x
	 *            截点横坐标 (从左开始计数)
	 * @param y
	 *            截点纵坐标 (从上开始计数)
	 * @param width
	 *            截取的宽度
	 * @param height
	 *            截取的长度
	 * @param oldpath
	 *            图片位置
	 * @param newpath
	 *            新生成的图片位置
	 */
	public static void cutImage(BufferedImage image, String descpath) {
		// FileInputStream is = null;
		// ImageInputStream iis = null;
		//
		// // 这个是获取图片扩展名的方法，比如：jpg。我这里有现成的，如果没有，自己实现
		// String imgType = getExt(descpath);
		try {
			//
			// // 判断图片格式是否正确
			// if (image.getWidth(null) == -1) {
			// return 0;
			//
			// } else {
			//
			// int newWidth = (int) (((double) image.getWidth(null)) / 4);
			// int newHeight = (int) (((double) image.getHeight(null)) / 4);
			// BufferedImage tag = new BufferedImage((int) newWidth,
			// (int) newHeight, BufferedImage.TYPE_INT_RGB);
			//
			// tag.getGraphics().drawImage(
			// image.getScaledInstance(newWidth, newHeight,
			// Image.SCALE_SMOOTH), 0, 0, null);
			// FileOutputStream out = new FileOutputStream(new File(new File(
			// savePath), saveFileName + "icon" + extendName));
			// // JPEGImageEncoder可适用于其他图片类型的转换
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// encoder.encode(tag);
			// out.close();
			// }
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 缩略图片
	 * 
	 * @param oldpath
	 *            原图片
	 * @param newpath
	 *            新生成的图片存放地址
	 * @param wdith
	 *            缩略后的宽
	 * @param height
	 *            缩略后的高
	 * @throws IOException
	 */
	public static void scaleImage(String oldpath, String newpath, int wdith,
			int height) throws IOException {
		log.info("oldpath---" + oldpath);
		log.info("newpath---" + newpath);
		log.info("wdith---" + wdith);
		log.info("height---" + height);
		// 获取老的图片
		File oldimg = new File(oldpath);
		if (!oldimg.exists()) {
			log.info("scaleImage---" + oldpath + "文件位找到!");
			return;
		}
		try {
			BufferedImage bi = ImageIO.read(oldimg);
			Image Itemp = bi.getScaledInstance(wdith, height,
					BufferedImage.SCALE_SMOOTH);
			BufferedImage thumbnail = new BufferedImage(wdith, height,
					BufferedImage.TYPE_INT_RGB);
			thumbnail.getGraphics().drawImage(Itemp, 0, 0, null);
			// 缩略后的图片路径
			File newimg = new File(newpath);
			FileOutputStream out = new FileOutputStream(newimg);
			// 绘图
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder
					.getDefaultJPEGEncodeParam(thumbnail);
			param.setQuality(1.0f, false);
			encoder.encode(thumbnail);
			out.close();
			bi.flush();
			bi = null;
		} catch (IOException e) {
			log.error(e);
			throw e;
		}
	}

	public static String getExt(String fileName) {
		int index = fileName.lastIndexOf(".");
		// 取得文件扩展名
		String extendName = fileName.substring(index + 1).toLowerCase();
		return extendName;
	}

	public static void main(String[] args) throws IOException {
		scaleImage("D:/data/image/wallpaper_default.jpg",
				"D:/data/image/wallpaper_default_small.jpg", 213, 189);
		// 获取图片源文件
		// BufferedImage img = ImageIO.read(new File("图片路径").toURL());
		//
		// // 切割图片，开始位置Start_X,Start_Y,切图的大小，Windth,Height
		// ImageFilter cropFilter = new CropImageFilter(Start_X, Start_Y,
		// Windth,
		// Height);
		//
		// // 创建好切图
		// Image imgs = Toolkit.getDefaultToolkit().createImage(
		// new FilteredImageSource(img.getSource(), cropFilter));
	}
}