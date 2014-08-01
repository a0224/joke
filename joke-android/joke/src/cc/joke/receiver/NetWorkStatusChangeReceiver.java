package cc.joke.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;
import cc.joke.activity.NetWorkListener;
import cc.joke.debug.Logger;

public class NetWorkStatusChangeReceiver extends BroadcastReceiver
{
    public static boolean NETWORK_EABLE = false;

    private NetWorkListener refreshable;

    public NetWorkStatusChangeReceiver(NetWorkListener refreshable)
    {
        this.refreshable = refreshable;
    }

    public void onReceive(Context context, Intent intent)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        // 有网络连接了
        if (wifiState != null && mobileState != null
                && (wifiState == State.CONNECTED || mobileState == State.CONNECTED))
        {
            NETWORK_EABLE = true;
            if (refreshable != null)
            {
                try
                {
                    // 刷新页面
                    refreshable.startNetwork();
                }
                catch (Exception e)
                {
                    Logger.error(e);
                }
            }
        }
        else
        {
            NETWORK_EABLE = false;
        }

    }
}
