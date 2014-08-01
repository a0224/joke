//package test;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FilenameFilter;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.FileEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//
//import test.Aaaa.fileFilter;
//
//public class UploadFile {
//
//	/**
//	 * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能: <FORM METHOD=POST
//	 * ACTION="http://192.168.0.200:8080/ssi/fileload/test.do"
//	 * enctype="multipart/form-data"> <INPUT TYPE="text" NAME="name"> <INPUT
//	 * TYPE="text" NAME="id"> <input type="file" name="imagefile"/> <input
//	 * type="file" name="zip"/> </FORM>
//	 * 
//	 * @param path
//	 *            上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，
//	 *            因为它会指向手机模拟器，你可以使用http://
//	 *            www.baidu.com或http://192.168.1.10:8080这样的路径测试)
//	 * @param params
//	 *            请求参数 key为参数名,value为参数值
//	 * @param file
//	 *            上传文件
//	 */
//	public static boolean post(String path, Map<String, String> params) throws Exception {
//		// 数据分隔线
//		final String BOUNDARY = "---------------------------7da2137580612";
//		// 数据结束标志"---------------------------7da2137580612--"
//		final String endline = "--" + BOUNDARY + "--\r\n";
//
//		// 下面两个for循环都是为了得到数据长度参数，依据表单的类型而定
//		// 首先得到文件类型数据的总长度(包括文件分割线)
//		int fileDataLength = 0;
//		for (FormFile uploadFile : files) {
//			StringBuilder fileExplain = new StringBuilder();
//			fileExplain.append("--");
//			fileExplain.append(BOUNDARY);
//			fileExplain.append("\r\n");
//			fileExplain.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName()
//					+ "\";filename=\"" + uploadFile.getFilname() + "\"\r\n");
//			fileExplain.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
//			fileExplain.append("\r\n");
//			fileDataLength += fileExplain.length();
//			if (uploadFile.getInStream() != null) {
//				fileDataLength += uploadFile.getFile().length();
//			} else {
//				fileDataLength += uploadFile.getData().length;
//			}
//		}
//		// 再构造文本类型参数的实体数据
//		StringBuilder textEntity = new StringBuilder();
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//			textEntity.append("--");
//			textEntity.append(BOUNDARY);
//			textEntity.append("\r\n");
//			textEntity.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
//			textEntity.append(entry.getValue());
//			textEntity.append("\r\n");
//		}
//
//		// 计算传输给服务器的实体数据总长度(文本总长度+数据总长度+分隔符)
//		int dataLength = textEntity.toString().getBytes().length + fileDataLength + endline.getBytes().length;
//
//		URL url = new URL(path);
//		// 默认端口号其实可以不写
//		int port = url.getPort() == -1 ? 80 : url.getPort();
//		// 建立一个Socket链接
//		// Socket socket = new Socket(InetAddress.getByName(url.getHost()),
//		// port);
//		Socket socket = new Socket();
//		InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(url.getHost()), port);
//		socket.connect(isa, 15 * 1000);
//		// 获得一个输出流（从Android流到web）
//		OutputStream outStream = socket.getOutputStream();
//		// 下面完成HTTP请求头的发送
//		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
//		outStream.write(requestmethod.getBytes());
//		// 构建accept
//		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
//		outStream.write(accept.getBytes());
//		// 构建language
//		String language = "Accept-Language: zh-CN\r\n";
//		outStream.write(language.getBytes());
//		// 构建contenttype
//		String contenttype = "Content-Type: multipart/form-data; boundary=" + BOUNDARY + "\r\n";
//		outStream.write(contenttype.getBytes());
//		// 构建contentlength
//		String contentlength = "Content-Length: " + dataLength + "\r\n";
//		outStream.write(contentlength.getBytes());
//		// 构建alive
//		String alive = "Connection: Keep-Alive\r\n";
//		outStream.write(alive.getBytes());
//		// 构建host
//		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
//		outStream.write(host.getBytes());
//		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
//		outStream.write("\r\n".getBytes());
//		// 把所有文本类型的实体数据发送出来
//		outStream.write(textEntity.toString().getBytes());
//
//		// 把所有文件类型的实体数据发送出来
//		for (FormFile uploadFile : files) {
//			StringBuilder fileEntity = new StringBuilder();
//			fileEntity.append("--");
//			fileEntity.append(BOUNDARY);
//			fileEntity.append("\r\n");
//			fileEntity.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName()
//					+ "\";filename=\"" + uploadFile.getFilname() + "\"\r\n");
//			fileEntity.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
//			outStream.write(fileEntity.toString().getBytes());
//			// 边读边写
//			if (uploadFile.getInStream() != null) {
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
//					outStream.write(buffer, 0, len);
//				}
//				uploadFile.getInStream().close();
//			} else {
//				outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
//			}
//			outStream.write("\r\n".getBytes());
//		}
//		// 下面发送数据结束标志，表示数据已经结束
//		outStream.write(endline.getBytes());
//		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
//		
////		Log.i("", "post img - 200:"+reader.readLine().indexOf("200"));
//		if (reader.readLine().indexOf("200") == -1) {
//			return false;
//		}
//		outStream.flush();
//		outStream.close();
//		reader.close();
//		socket.close();
//		return true;
//	}
//
//	/**
//	 * 提交数据到服务器
//	 * 
//	 * @param path
//	 *            上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://
//	 *            www.baidu.com或http://192.168.1.10:8080这样的路径测试)
//	 * @param params
//	 *            请求参数 key为参数名,value为参数值
//	 * @param file
//	 *            上传文件
//	 */
//	public static boolean post(String path, Map<String, String> params) throws Exception {
//		return post(path, params);
//	}
//	
//	public static void main(String[] str) throws Exception {
//		
//		String name = URLEncoder.encode("jinjin", "utf-8");
//		String pass = URLEncoder.encode("111111", "utf-8");
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("NAME", name);
//		params.put("PASSWORD", pass);
//
//		Map<String, File> upfiles = new HashMap<String, File>();
//		upfiles = getFile();
//		post("http://localhost:8080/JokeManager/upload/uploadImg.html?size=1024",
//				params, upfiles);
//		return post("http://localhost:8080/JokeManager/upload/uploadImg.html?size=1024", params);
//	}
//	
//	public static Map<String, File> getFile() {
//		Map<String, File> filesMap = new HashMap<String, File>();
//		File file = new File("F:/data/");
//		File[] files = file.listFiles(new fileFilter());
//
//		for (File f : files) {
//			filesMap.put(f.getName(), new File("F:/data/" + f.getName()));
//		}
//		return filesMap;
//		// Toast.makeText(this, filename, Toast.LENGTH_LONG).show();
//
//	}
//
//	static class fileFilter implements FilenameFilter {
//		@Override
//		public boolean accept(File dir, String filename) {
//			return filename.endsWith(".jpg");
//		}
//	}
// 
//}
