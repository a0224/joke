package com.joke.action.upload;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.free.apkparser.FreeAmlParser;
import com.free.apkparser.Manifest;
import com.joke.action.BaseAction;
import com.joke.utils.ApkTool;
import com.joke.utils.BdBucUtil;
import com.joke.utils.Constant;
import com.joke.utils.DwindlePic;
import com.joke.utils.DateUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class FileUploadAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8628490433899540531L;
	/** log */
	private static Log log = LogFactory.getLog(FileUploadAction.class);

	private String result;
	private String message;
	private long size;
	private String lastFloder;
	private String status;
	private String downloadUrl;
	private String url;
	private String compareFlag;
	private String remoteRealUrl;
	private String channelId;
	private String usedSdk;
	private String packageName;
	private String versionCode;
	private String versionName;
	private String type;
	private String minSdkVersion;
	private String maxSdkVersion;
	private String websiteId;
	private String resPackageId;
	private String remoteUrl;
	private String resCloudId;
	private String realName;
	private String appKey;
	private String iconUrl;

	// 封装上传文件域的属性
	private File file;
	// 封装上传文件类型的属性
	private String fileContentType;
	// 封装上传文件名的属性
	private String fileFileName;
	// 接受依赖注入的属性
	private String savePath;	

	private HashMap<String, Comparable> resultMap = new HashMap<String, Comparable>();

	@Override
	protected String executeProcess() throws Exception {
		return "success";
	}

	public String uploadImg() {

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			log.info("IOException: " + e1);
		}

		// 取得文件扩展名
		String extendName = fileFileName.substring(
				fileFileName.lastIndexOf(".")).toLowerCase();
		String saveFileName = DateUtil.fileName();
		savePath = DateUtil.filePath(Constant.IMAGE_PATH);

		try {
			log.info("savePath: " + savePath);
			if (file != null) {

				File savefile = new File(new File(savePath), saveFileName
						+ extendName);
				if (!savefile.getParentFile().exists()) {
					savefile.getParentFile().mkdirs();
				}

				if (fileFileName.endsWith(".jpg")
						|| fileFileName.endsWith(".png")) {
				} else {
					setResult("failed");
					setMessage("上传格式错误");
					setStatus("0");
					throw new Exception();
				}

				log.info("FileUtils: " + savePath);
				BdBucUtil.putFile(file,savePath);

				// 判断图片格式是否正确
				BufferedImage image;
				image = ImageIO.read(new FileInputStream(savefile));
				if (image.getWidth(null) == -1) {
					setResult("failed");
					setMessage("上传失败");
					setStatus("0");
					throw new Exception();

				}
				// else {
				// log.info("image: " + savePath);
				// DwindlePic mypic = new DwindlePic();
				// mypic.s_pic(savePath + "/", savePath + "/", saveFileName
				// + extendName, saveFileName + "_mic" + extendName,
				// 300, 300, true);
				// }

				int newWidth = 0;
				int newHeight = 0;
				if (image.getWidth(null) <= 100) {
					newWidth = (int) (((double) image.getWidth(null)) / 1);
					newHeight = (int) (((double) image.getHeight(null)) / 1);
				} else if (image.getWidth(null) <= 400
						&& image.getWidth(null) > 100) {
					newWidth = (int) (((double) image.getWidth(null)) / 2);
					newHeight = (int) (((double) image.getHeight(null)) / 2);
				} else if (image.getWidth(null) <= 800
						&& image.getWidth(null) > 400) {
					newWidth = (int) (((double) image.getWidth(null)) / 6);
					newHeight = (int) (((double) image.getHeight(null)) / 6);
				} else if (image.getWidth(null) <= 2000
						&& image.getWidth(null) > 800) {
					newWidth = (int) (((double) image.getWidth(null)) / 10);
					newHeight = (int) (((double) image.getHeight(null)) / 10);
				} else {
					newWidth = (int) (((double) image.getWidth(null)) / 20);
					newHeight = (int) (((double) image.getHeight(null)) / 20);
				}

				log.info("BufferedImage: " + savePath);
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				tag.getGraphics().drawImage(
						image.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream outputStream = new FileOutputStream(new File(
						new File(savePath), saveFileName + "_mic" + extendName));
				// JPEGImageEncoder可适用于其他图片类型的转换
				log.info("JPEGImageEncoder: " + savePath);
				log.info("JPEGImageEncoder: " + extendName);
				JPEGImageEncoder encoder = JPEGCodec
						.createJPEGEncoder(outputStream);
				encoder.encode(tag);
				log.info("encoder: " + savePath);
				outputStream.close();

				// ImageFilter cropFilter;
				// // 读取源图像
				// int srcWidth = image.getWidth(); // 源图宽度
				// int srcHeight = image.getHeight(); // 源图高度
				// int destWidth = (int) (((double) image.getWidth(null)) / 2);
				// int destHeight = srcHeight;
				// if (srcWidth >= destWidth && srcHeight >= destHeight) {
				// Image imageScale = image.getScaledInstance(srcWidth,
				// srcHeight, Image.SCALE_DEFAULT);
				// // 改进的想法:是否可用多线程加快切割速度
				// // 四个参数分别为图像起点坐标和宽高
				// // 即: CropImageFilter(int x,int y,int width,int height)
				// cropxFilter = new CropImageFilter(srcWidth / 4, 0,
				// destWidth, destHeight);
				// Image imgeToolkit = Toolkit.getDefaultToolkit()
				// .createImage(
				// new FilteredImageSource(imageScale
				// .getSource(), cropFilter));
				// BufferedImage tag = new BufferedImage(destWidth, srcHeight,
				// BufferedImage.TYPE_INT_RGB);
				// Graphics g = tag.getGraphics();
				// g.drawImage(imgeToolkit, 0, 0, null); // 绘制缩小后的图
				// g.dispose();
				// // 输出为文件
				// ImageIO.write(tag, getExt(fileFileName), new File(savePath
				// + "/" + saveFileName + "_preview" + extendName));
				// }

				// url = Constant.readValue("down")
				// + DateUtil.filePath(Constant.IMAGE_PATH) + "/"
				// + saveFileName + extendName;
				url = DateUtil.filePath(Constant.IMAGE_PATH) + "/"
						+ saveFileName + extendName;
				log.info(url);
				log.info("resultMap: " + savePath);
				resultMap.put("url", url);
				resultMap.put("status", 1);
				log.info(Constant.resultJson.toJson(resultMap));
				out.print(Constant.resultJson.toJson(resultMap));
				out.flush();
				out.close();
				return null;

			} else {
				setResult("failed");
				setMessage("上传失败");
				setStatus("0");
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			resultMap.put("result", getResult());
			resultMap.put("message", getMessage() == null ? "上传失败"
					: getMessage());
			resultMap.put("status", 0);
			log.info(Constant.resultJson.toJson(resultMap));
			out.print(Constant.resultJson.toJson(resultMap));
			out.flush();
			out.close();
			return null;
		}

		// setMessage("文件上传成功");
		// setResult("success");
		// setStatus("1");
		// return "success";

	}
	
	public String apkUpload() throws Exception {

		int index = fileFileName.lastIndexOf(".");
		// 取得文件扩展名
		String extendName = fileFileName.substring(index).toLowerCase();
		String saveFileName = DateUtil.fileName() + extendName;
		String aaptpath = getRequest().getSession().getServletContext()
				.getRealPath("")
				+ "/apkTool";
		savePath =  DateUtil.filePath(Constant.APK_PATH);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			log.debug("savePath: " + savePath);
			if (file != null) {

				if (fileFileName.endsWith(".apk")
						|| fileFileName.endsWith(".APK")) {
				} else {
					setResult("failed");
					setMessage("上传包格式错误");
					setStatus("0");
					throw new Exception();
				}

				File savefile = new File(new File(savePath), saveFileName);
				if (!savefile.getParentFile().exists())
					savefile.getParentFile().mkdirs();
				BdBucUtil.putFile(file,savePath);

				FreeAmlParser freeAmlParser = new FreeAmlParser();
				Manifest manifest = null;

				try {
					manifest = freeAmlParser.parserAml(aaptpath,
							savefile.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
					log.info("apk解析失败");
					log.info(e);
					setMessage("apk解析失败");
					setStatus("0");
					throw new Exception();
				}

				if (manifest != null) {

					if (!"null".equals(manifest.getIconPath())) {
						setIconUrl(DateUtil.filePath(Constant.APK_PATH)
								+ "/"
								+ manifest.getIconPath().substring(
										manifest.getIconPath().lastIndexOf(
												File.separator) + 1));
						// System.out.println("####apk.getIconUrl=" +
						// apk.getIconUrl());
					}
					minSdkVersion = String.valueOf(manifest.getMinSdk());
					setMaxSdkVersion(String.valueOf(manifest.getMaxSdk()));
					size = savefile.length() / 1024;
					remoteRealUrl = DateUtil.filePath(Constant.APK_PATH) + "/"
							+ saveFileName;
					url = DateUtil.filePath(Constant.APK_PATH) + "/"
							+ saveFileName;

					remoteUrl = DateUtil.filePath(Constant.APK_PATH) + "/"
							+ saveFileName;

					packageName = manifest.getpName();
					versionName = manifest.getvName();
					versionCode = String.valueOf(manifest.getvCode());

				} else {
					log.info("apk解析失败,manifest is null");
					setResult("failed");
					setMessage("apk解析失败");
					setStatus("0");
					throw new Exception();
				}

				lastFloder = "20130808/1375923844468";
				downloadUrl = "http://moguyun2-cms.plat56.com/";
				websiteId = "51109411";

				resultMap.put("minSdkVersion", minSdkVersion);
				resultMap.put("maxSdkVersion", maxSdkVersion);
				resultMap.put("size", size);
				resultMap.put("remoteRealUrl", remoteRealUrl);
				resultMap.put("url", url);
				resultMap.put("packageName", packageName);
				resultMap.put("versionName", versionName);
				resultMap.put("remoteUrl", remoteUrl);
				resultMap.put("versionCode", versionCode);
				resultMap.put("status", 1);
				resultMap.put("iconUrl", iconUrl);

				log.info(Constant.resultJson.toJson(resultMap));
				out.print(Constant.resultJson.toJson(resultMap));
				out.flush();
				out.close();
				return null;

			} else {
				setResult("failed");
				setMessage("上传失败");
				setStatus("0");
				throw new Exception();
			}

		} catch (Exception e) {
			resultMap.put("result", getResult());
			resultMap.put("message", getMessage() == null ? "上传失败"
					: getMessage());
			resultMap.put("status", 0);
			log.error(Constant.resultJson.toJson(resultMap));
			out.print(Constant.resultJson.toJson(resultMap));
			e.printStackTrace();
			log.error(e);
			out.flush();
			out.close();
			return null;
		}

		// setMessage("文件上传成功");
		// setResult("success");
		// setStatus("1");
		// return "success";

	}
	
	public String fileUpload() {

		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			log.info("IOException: " + e1);
		}

		// 取得文件扩展名
		String extendName = fileFileName.substring(
				fileFileName.lastIndexOf(".")).toLowerCase();
		String saveFileName = DateUtil.fileName();
		savePath =  DateUtil.filePath(Constant.UPLOADS_FILE);

		try {
			log.info("savePath: " + savePath);
			if (file != null) {

				File savefile = new File(new File(savePath), saveFileName
						+ extendName);
				if (!savefile.getParentFile().exists()) {
					savefile.getParentFile().mkdirs();
				}

				log.info("FileUtils: " + savePath);
				BdBucUtil.putFile(file,savePath);
				
				url = DateUtil.filePath(Constant.UPLOADS_FILE) + "/"
						+ saveFileName + extendName;
				log.info(url);
				log.info("resultMap: " + savePath);
				resultMap.put("url", url);
				resultMap.put("status", 1);
				log.info(Constant.resultJson.toJson(resultMap));
				out.print(Constant.resultJson.toJson(resultMap));
				out.flush();
				out.close();
				return null;

			} else {
				setResult("failed");
				setMessage("上传失败");
				setStatus("0");
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			resultMap.put("result", getResult());
			resultMap.put("message", getMessage() == null ? "上传失败"
					: getMessage());
			resultMap.put("status", 0);
			log.info(Constant.resultJson.toJson(resultMap));
			out.print(Constant.resultJson.toJson(resultMap));
			out.flush();
			out.close();
			return null;
		}

	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the lastFloder
	 */
	public String getLastFloder() {
		return lastFloder;
	}

	/**
	 * @param lastFloder
	 *            the lastFloder to set
	 */
	public void setLastFloder(String lastFloder) {
		this.lastFloder = lastFloder;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the downloadUrl
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param downloadUrl
	 *            the downloadUrl to set
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the compareFlag
	 */
	public String getCompareFlag() {
		return compareFlag;
	}

	/**
	 * @param compareFlag
	 *            the compareFlag to set
	 */
	public void setCompareFlag(String compareFlag) {
		this.compareFlag = compareFlag;
	}

	/**
	 * @return the remoteRealUrl
	 */
	public String getRemoteRealUrl() {
		return remoteRealUrl;
	}

	/**
	 * @param remoteRealUrl
	 *            the remoteRealUrl to set
	 */
	public void setRemoteRealUrl(String remoteRealUrl) {
		this.remoteRealUrl = remoteRealUrl;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId
	 *            the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the usedSdk
	 */
	public String getUsedSdk() {
		return usedSdk;
	}

	/**
	 * @param usedSdk
	 *            the usedSdk to set
	 */
	public void setUsedSdk(String usedSdk) {
		this.usedSdk = usedSdk;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the versionCode
	 */
	public String getVersionCode() {
		return versionCode;
	}

	/**
	 * @param versionCode
	 *            the versionCode to set
	 */
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return versionName;
	}

	/**
	 * @param versionName
	 *            the versionName to set
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the minSdkVersion
	 */
	public String getMinSdkVersion() {
		return minSdkVersion;
	}

	/**
	 * @param minSdkVersion
	 *            the minSdkVersion to set
	 */
	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}

	/**
	 * @return the websiteId
	 */
	public String getWebsiteId() {
		return websiteId;
	}

	/**
	 * @param websiteId
	 *            the websiteId to set
	 */
	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}

	/**
	 * @return the resPackageId
	 */
	public String getResPackageId() {
		return resPackageId;
	}

	/**
	 * @param resPackageId
	 *            the resPackageId to set
	 */
	public void setResPackageId(String resPackageId) {
		this.resPackageId = resPackageId;
	}

	/**
	 * @return the remoteUrl
	 */
	public String getRemoteUrl() {
		return remoteUrl;
	}

	/**
	 * @param remoteUrl
	 *            the remoteUrl to set
	 */
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	/**
	 * @return the resCloudId
	 */
	public String getResCloudId() {
		return resCloudId;
	}

	/**
	 * @param resCloudId
	 *            the resCloudId to set
	 */
	public void setResCloudId(String resCloudId) {
		this.resCloudId = resCloudId;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey
	 *            the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType
	 *            the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the fileFileName
	 */
	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * @param fileFileName
	 *            the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @return the savePath
	 */
	public String getSavePath() {
		return savePath;
	}

	/**
	 * @param savePath
	 *            the savePath to set
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * @return the maxSdkVersion
	 */
	public String getMaxSdkVersion() {
		return maxSdkVersion;
	}

	/**
	 * @param maxSdkVersion
	 *            the maxSdkVersion to set
	 */
	public void setMaxSdkVersion(String maxSdkVersion) {
		this.maxSdkVersion = maxSdkVersion;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * @param iconUrl
	 *            the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

}
