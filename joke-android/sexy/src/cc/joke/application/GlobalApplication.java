package cc.joke.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.frontia.FrontiaApplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cc.joke.cache.BitmapCache;
import cc.joke.db.DBHelper;
import cc.joke.entity.T_Login;
import cc.joke.service.DsGameMarketService;
import cc.joke.util.ActivityManager;
import cc.joke.util.SystemInfo;

public class GlobalApplication extends FrontiaApplication
{

    public static Context context;// 上下文

    public static String deviceid;// 手机唯一标识

    public static final int projectid = 55555;// 项目ID

    public static int widthPixels;// 手机宽(px)

    public static int heightPixels;// 手机高(px)

    public static float density;// 密度

    private static T_Login mLogin;// 用户登陆信息

    public static ActivityManager activityManager = ActivityManager.getInstance(); // activity管理

    public static BitmapCache bitmapCache;// 图片缓存

    public static DBHelper dBHelper;// 数据库帮助类

    /** 可升级应用程序数据缓存 **/
    public static Map<String, String> mUpgradeCache = new HashMap<String, String>();

    public void onCreate()
    {
        super.onCreate();
        context = this;
        bitmapCache = BitmapCache.getInstance();
        density = this.getResources().getDisplayMetrics().density;
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        dBHelper = new DBHelper(this);
        deviceid = SystemInfo.getInstance().deviceid;
        mLogin = getLoginInfo();
    }

    public static T_Login getLoginInfo()
    {
        if (mLogin == null)
        {
            List<T_Login> list = GlobalApplication.dBHelper.query(T_Login.class, null, null, null);
            if (list != null && list.size() > 0)
            {
                mLogin = list.get(0);
                return mLogin;
            }
        }
        return mLogin;
    }

    public static void clearLoginInfo()
    {
        GlobalApplication.dBHelper.delete(T_Login.class, null, null);
        mLogin = null;
    }
}
