package cc.joke.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import cc.joke.activity.NetWorkListener;
import cc.joke.receiver.NetWorkStatusChangeReceiver;
import cc.joke.util.PreferencesUtil;

public class DsGameMarketService extends Service implements NetWorkListener
{
    private PendingIntent pendingIntent = null;

    public static final String START_ACTION = "start";

    public static final String GET_MESSGE_ACTION = "get message";
    
    private final String PREFERENCE = "push preference";
    
    private final String LAST_TIME_KEY = "last time key";
    
    private NetWorkStatusChangeReceiver mNetWorkStatusChangeReceiver;
 
    public void onCreate()
    {
		/** 注册监听网络广播消息 **/
		mNetWorkStatusChangeReceiver = new NetWorkStatusChangeReceiver(this);
		IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(mNetWorkStatusChangeReceiver, filter);
		Log.i("", "connectionThread -- ：onCreate");
    }
    
    @Override
    public void onDestroy()
    {
    	Log.i("", "connectionThread -- ：onDestroy");
        super.onDestroy();
        unregisterReceiver(mNetWorkStatusChangeReceiver);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
    	Log.i("", "connectionThread -- ：onStartCommand");
        if (intent != null)
        {
            String action = intent.getAction();

            if (START_ACTION.equals(action))
            {
            	startAlarm();
            }
            else if (GET_MESSGE_ACTION.equals(action))
            {
                handleCommand(intent);
            }
        }
        return START_STICKY;
    }

    private void stopAlarm()
    {
        if (pendingIntent != null)
        {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }

    private void startAlarm()
    {
    	Log.i("", "connectionThread -- ：start");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (pendingIntent == null)
        {
            Intent intent = new Intent(this, DsGameMarketService.class);
            intent.setAction(GET_MESSGE_ACTION);
            pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        if (pendingIntent != null)
        {
            // 上次拉取时间，默认单位  1分钟
        	long lastTime = PreferencesUtil.getPreference(this, PREFERENCE, LAST_TIME_KEY, (long)0);

            // 测试周期，使用1分钟
            long period = 60 * 1000;

            // 判断上次拉取时间距离现在多久，如果超过周期，马上拉取
            long delay = (System.currentTimeMillis() - lastTime) > period ? 60 * 1000
                    : (System.currentTimeMillis() - lastTime);

            if (delay < 60 * 1000)
            {
                delay = 60 * 1000;
            }

            // 启动一分钟后获取推送消息，然后每小时获取一次推送消息
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, period,
                    pendingIntent);
        }
    }
    private Thread connectionThread = null;

    int i = 0;
    protected final void handleCommand(Intent intent)
    {
    	Log.i("", "connectionThread -- ：handle");
    	
        if (connectionThread != null && connectionThread.isAlive())
        {
            return;
        }
        PreferencesUtil.setPreferences(this, PREFERENCE, LAST_TIME_KEY, System.currentTimeMillis());

        connectionThread = new Thread()
        {
            @Override
            public void run()
            {
            	Log.i("", "connectionThread -- ："+(i++));
                // 获取广播信息
                // 服务端对广播信息会保留最近三条，所以客户端要把上次广播item的id发过去增量获取最新的广播消息
//                ArrayList<MessageItem> messages = getMessages(1);
//                if (messages != null)
//                {
//                    for (MessageItem item : messages)
//                    {
//                        processMessage(item, true, true);
//                    }
//                }
            }

        };
        connectionThread.setName("Connection Thread");
        connectionThread.setDaemon(true);
        connectionThread.start();
    }

	@Override
	public void startNetwork() {
 
		 startAlarm();
	}
	
	@Override
	public void stopNetwork() {
		
		stopAlarm();
	}
	
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

}
