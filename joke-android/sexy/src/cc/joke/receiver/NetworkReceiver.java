package cc.joke.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Handler;
import cc.joke.common.Constants;
import cc.joke.http.MessageRequest;
import cc.joke.util.ThreadPool;

public class NetworkReceiver extends BroadcastReceiver
{

    private Context mContext;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    Intent i = new Intent(Constants.APP_NETWORK_ACTION);
                    mContext.sendBroadcast(i);
                    ThreadPool.add(new MessageRequest(mContext));
                    break;
            }
        };
    };

    public void onReceive(Context context, Intent intent)
    {
        mContext = context;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null
                && (wifiState == State.CONNECTED || mobileState == State.CONNECTED))
        {
            mHandler.sendEmptyMessage(0);
        }
    }
}
