package cc.joke.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import cc.joke.common.Constants;
import cc.joke.entity.CheckClient;
import cc.joke.entity.T_App;
import cc.joke.entity.T_Download;

public class JokeDownloadManager
{

    private Context mContext;

    private static JokeDownloadManager mJokeDownloadManager;

    private final String mDownloadPath = Constants.JOKE_ROOT + "/apk";

    DownloadManager mDownloadManager;

    Map<Long, T_Download> mMap = new HashMap<Long, T_Download>();

    private JokeDownloadManager(Context context)
    {
        mContext = context;

        mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static JokeDownloadManager getInstance(Context context)
    {
        if (mJokeDownloadManager == null)
        {
            mJokeDownloadManager = new JokeDownloadManager(context);
        }
        return mJokeDownloadManager;
    }

    public static JokeDownloadManager getInstance()
    {
        return mJokeDownloadManager;
    }

    public void clear()
    {
        mMap.clear();
        mMap = null;
        mContext.unregisterReceiver(receiver);
    }

    public void startApk(CheckClient cc)
    {
        if (cc == null)
        {
            return;
        }

        String url = cc.getBaseUrl();

        if (TextUtils.isEmpty(url))
        {
            return;
        }
        String fileName = cc.getVersionName() + ".apk";

        File folder = new File(mDownloadPath);
        if (!folder.exists() || !folder.isDirectory())
        {
            folder.mkdirs();
        }

        // 开始下载
        Uri resource = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(resource);
        request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        // 设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        // 在通知栏中显示
        request.setShowRunningNotification(true);
        request.setVisibleInDownloadsUi(true);
        // sdcard的目录下的文件夹
        request.setDestinationInExternalPublicDir(mDownloadPath, fileName);
        request.setTitle("当前版本：" + cc.getVersionName());

        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        long downloadId = mDownloadManager.enqueue(request);

        T_Download info = new T_Download(downloadId, Constants.DOWNLOAD_APK_DIR + "/" + fileName);

        mMap.put(downloadId, info);
    }

    public void startApk(T_App app)
    {
        if (app == null)
        {
            return;
        }

        String url = app.getBaseUrl();

        String fileName = app.getName() + ".apk";

        File folder = new File(mDownloadPath);
        if (!folder.exists() || !folder.isDirectory())
        {
            folder.mkdirs();
        }

        // 开始下载
        Uri resource = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(resource);
        request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        // 设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        // 在通知栏中显示
        request.setShowRunningNotification(true);
        request.setVisibleInDownloadsUi(true);
        // sdcard的目录下的文件夹
        request.setDestinationInExternalPublicDir(mDownloadPath, fileName);
        request.setTitle("正在下载：" + app.getName());

        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        long downloadId = mDownloadManager.enqueue(request);

        T_Download info = new T_Download(downloadId, Constants.DOWNLOAD_APK_DIR + "/" + fileName);

        mMap.put(downloadId, info);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            T_Download info = mMap.get(completeDownloadId);
            if (info != null)
            {
                Util.install(mContext, info.getFileName());
                mMap.remove(completeDownloadId);
            }
        }
    };
}
