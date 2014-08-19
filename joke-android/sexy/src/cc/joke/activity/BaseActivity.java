package cc.joke.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import cc.joke.application.GlobalApplication;

public class BaseActivity extends Activity
{

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GlobalApplication.activityManager.pushActivity(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            GlobalApplication.activityManager.popActivity(this);
            // 程序中已经没有Activity了
            if (GlobalApplication.activityManager.getCount() == 0)
            {
                // 开启首页
                Intent intent = new Intent();
                intent.setClass(this, SlidingActivity.class);
                startActivity(intent);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
