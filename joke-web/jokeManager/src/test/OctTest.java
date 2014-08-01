package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OctTest {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String url = req.getParameter("j_imgurl");
		InputStream instream = InitEnv.class
				.getResourceAsStream(InitEnv.CERTPATH);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String protocol = "http";
		byte[] b = null;
		try {
			if (url.startsWith("https")) {
				protocol = "https";
			}
			PicUtil.getPic(protocol, url, InitEnv.PORT, InitEnv.PWD, instream,
					outputStream);
			b = outputStream.toByteArray();
			ImageFilter imageFilter = new ImageFilter(new ByteArrayInputStream(
					b));
			outputStream.close();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIOHelper.createImage(imageFilter.changeGrey(),
					byteArrayOutputStream);
			byte[] imgData = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();

			if (imgData == null || imgData.length <= 0) {
				return;
			}
			TesseractEntity tesseractEntity = new TesseractEntity();
			tesseractEntity.setImgByte(imgData);
			tesseractEntity.setIdentifyingCode("-1");
			tesseractEntity.setLength(imgData.length);
			tesseractEntity.setPath(InitEnv.unZipOcrPath);
			tesseractEntity.setLang_option("eng");

			// 调用JNI 获得图片文字
			String result = AnalysePictureEx.analysePicEx(tesseractEntity);
			String uuid = UUID.randomUUID().toString();
			ImageData.imags.put(uuid, b);

			// 获得验证码
			req.setAttribute("identifyingCode",
					tesseractEntity.getIdentifyingCode());
			req.setAttribute("uuid", uuid);
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher("/result.jsp");
			requestDispatcher.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
