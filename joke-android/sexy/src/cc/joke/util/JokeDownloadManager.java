package cc.joke.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import cc.joke.R;
import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.entity.CheckClient;
import cc.joke.entity.T_App;
import cc.joke.entity.T_Download;

@SuppressLint("NewApi")
public class JokeDownloadManager {

	private Context mContext;

	private static JokeDownloadManager mJokeDownloadManager;

	private final String mDownloadPath = Constants.JOKE_ROOT + "/apk";

	DownloadManager mDownloadManager;

	Map<Long, T_Download> mMap = new HashMap<Long, T_Download>();

	private JokeDownloadManager(Context context) {
		mContext = context;

		mContext.registerReceiver(receiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	public static JokeDownloadManager getInstance(Context context) {
		if (mJokeDownloadManager == null) {
			mJokeDownloadManager = new JokeDownloadManager(context);
		}
		return mJokeDownloadManager;
	}

	public static JokeDownloadManager getInstance() {
		return mJokeDownloadManager;
	}

	public void clear() {
		mMap.clear();
		mMap = null;
		mContext.unregisterReceiver(receiver);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			long completeDownloadId = intent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			T_Download info = mMap.get(completeDownloadId);
			if (info != null) {
				Util.install(mContext, info.getFileName());
				mMap.remove(completeDownloadId);
			}
		}
	};

	public void startApkBySysDown(String url,String name) {
		String fileName = name + ".apk";

		// 开始下载
		Uri resource = Uri.parse(url);
		DownloadManager.Request request = new DownloadManager.Request(resource);
		request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
				| Request.NETWORK_WIFI);
		request.setAllowedOverRoaming(false);
		// 设置文件类型
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
		String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap
				.getFileExtensionFromUrl(url));
		request.setMimeType(mimeString);
		// 在通知栏中显示
		request.setShowRunningNotification(true);
		request.setVisibleInDownloadsUi(true);
		// sdcard的目录下的文件夹
		request.setDestinationInExternalPublicDir(mDownloadPath, fileName);
		request.setTitle("正在下载：" + name);

		mDownloadManager = (DownloadManager) mContext
				.getSystemService(Context.DOWNLOAD_SERVICE);

		long downloadId = mDownloadManager.enqueue(request);

		T_Download info = new T_Download(downloadId, Constants.DOWNLOAD_APK_DIR
				+ "/" + fileName);

		mMap.put(downloadId, info);
	}


	public void startApkByOwn(String url,String name) {

		String fileName = name + ".apk";
		String newFilename = Constants.DOWNLOAD_APK_DIR + File.separator
				+ fileName;
		File newFile = new File(newFilename);

		Logger.i("urlDownload", url);
		Logger.i("fileName", fileName);
		Logger.i("newFilename", newFilename);


		// 如果目标文件已经存在，则删除，产生覆盖旧文件的效果（此处以后可以扩展为已经存在图片不再重新下载功能）
		if (newFile.exists()) {
			this.installApk(newFile);
			return;
		}

		if (this.fileDown(url, newFilename) == 1) {
			this.installApk(newFile);
		}
	}

	public int fileDown(String urlDownload, String newFilename) {
		try {
			// 构造URL
			URL url = new URL(urlDownload);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			// System.out.println("长度 :"+contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(newFilename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();

			Logger.i("download success");
			return 1;

		} catch (Exception e) {
			Logger.e("download error");
			Logger.e("Exception" + e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	public void installApk(File file) {

		Logger.e("installApk", file.getName());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		mContext.getApplicationContext().startActivity(intent);

	}

	private void unstallApk(File file) {

		Logger.e("unstallApk", file.getName());
		Uri packageURI = Uri.parse("package:com.demo.CanavaCancel");
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		mContext.getApplicationContext().startActivity(uninstallIntent);

	}
	
	public void startApk(String url,String name) {

		if (TextUtils.isEmpty(url)) {
			return;
		}
		
		File folder = new File(mDownloadPath);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		

		// 开始下载
		try {
			this.startApkBySysDown(url,name);
		} catch (Exception e) {
			Logger.e("系统下载失败 使用自己的下载工具!!!", e.toString());
			Logger.error(e);
			Util.toast(mContext, mContext.getApplicationContext()
					.getResources().getString(R.string.downloading));

			ThreadPool.add(new FileDown(url,name));
		}

	}
	
	class FileDown implements Runnable {
		private String url;
		private String name;
		
		public FileDown(String url,String name){
			this.url = url;
			this.name = name;
		}

		public void run() {
			try {
				JokeDownloadManager.getInstance()
						.startApkByOwn(url,name);
			} catch (Exception e) {
				Logger.error(e);
			}
		}
	}

}
