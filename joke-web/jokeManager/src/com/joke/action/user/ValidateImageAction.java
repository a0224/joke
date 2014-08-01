package com.joke.action.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.joke.action.BaseAction;
import com.joke.utils.Constant;
import com.joke.utils.RandomImage;

public class ValidateImageAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6495747608833924696L;
	/** log */
	private static Log log = LogFactory.getLog(ValidateImageAction.class);

	private InputStream inputStream;

	private String randomString; // 图片上的字符串
	private int length; // 图片上字符的个数
	private int width; // 图片的宽度
	private int height; // 图片的高度

	@Override
	protected String executeProcess() throws Exception {
		Constant.PROJECT_PATH = getRequest().getSession().getServletContext()
				.getRealPath("");

		RandomImage validateImage = new RandomImage(getRandomString(),
				getLength(), getWidth(), getHeight());

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
			ImageIO.write(validateImage.getValidateImage(), "JPEG", ios);
			inputStream = new ByteArrayInputStream(bos.toByteArray());
			bos.close();
		} catch (IOException e) {
			log.error(e);
		}

		Map<String, Object> session = getSession(); // 获得session

		session.put(Constant.VALIDATE_STRING, validateImage.getValidateString()
				.toLowerCase());
		// 将验证码转小写放入session
		log.debug("验证码:" + validateImage.getValidateString().toLowerCase());
		return "success";
	}

	public String getRandomString() {
		if (randomString == null) {
			randomString = "23456789abcdefghkmnpqrstuvwxyABCDEFGHKMNPQRSTUVWXY";
		}
		return randomString;
	}

	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}

	public int getLength() {
		if (length == 0) {
			length = 6;
		}
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		if (width == 0) {
			width = 80;
		}
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		if (height == 0) {
			height = 35;
		}
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
