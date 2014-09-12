package cc.joke.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import cc.joke.http.MessageRequest;
import cc.joke.util.ThreadPool;

public class BusinessMessageReceiver extends BroadcastReceiver
{

    public void onReceive(Context context, Intent intent)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        if (wifiState != null && mobileState != null
                && (wifiState == State.CONNECTED || mobileState == State.CONNECTED))
        {
            ThreadPool.add(new MessageRequest(context));
        }

    }
}
