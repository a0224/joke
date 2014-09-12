package cc.joke.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.util.Util;

public class HeadlinesDetailActivity extends Activity
{
    // 应用名称
    private TextView top_title;

    private WebView contentWebView = null;

    // 第一次请求已完成
    public boolean mTimerCount = false;

    /** 网络状态广播 **/
    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            if (mTimerCount == false)
            {
                mTimerCount = true;
                mHandler.sendEmptyMessageDelayed(0, 5000);
            }
        }
    };

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    mTimerCount = false;
                    break;
            }
        };
    };

    @SuppressLint("NewApi")
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.headlines_detail_activity);
        GlobalApplication.activityManager.pushActivity(this);

        // 获取htmlUrl
        String htmlUrl = getIntent().getStringExtra("htmlUrl");

        // title
        top_title = (TextView) findViewById(R.id.product_top_title);
        top_title.setVisibility(View.VISIBLE);
        top_title.setText("头条");
        top_title.requestFocus();

        contentWebView = (WebView) findViewById(R.id.headlineWebView);

        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.getSettings().setAppCacheEnabled(true);
        contentWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 随便找了个带图片的网站
        contentWebView.loadUrl(htmlUrl);
        // 添加js交互接口类，并起别名 imagelistner
        contentWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        contentWebView.setWebViewClient(new MyWebViewClient());

        /** 返回 **/
        ImageView goback = (ImageView) findViewById(R.id.goback);
        goback.setVisibility(View.VISIBLE);
        goback.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Util.exitActivity(HeadlinesDetailActivity.this);
            }
        });

        /** 分享 **/
        ImageView shareView = (ImageView) findViewById(R.id.share);
        shareView.setVisibility(View.VISIBLE);
        shareView.setOnClickListener(new ImageView.OnClickListener()
        {
            public void onClick(View v)
            {

            }
        });

        // 注册网络变化时的刷新广播
        IntentFilter filter = new IntentFilter(Constants.APP_NETWORK_ACTION);
        registerReceiver(mNetworkReceiver, filter);

    }

    protected void onDestroy()
    {
        super.onDestroy();
        // 取消网络变化时的刷新广播
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Util.exitActivity(this);
        }
        return super.onKeyUp(keyCode, event);
    }

    // 注入js函数监听
    private void addImageClickListner()
    {
        if (true/* !Util.isNetworkConnected() */)
        {
            return;
        }
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        contentWebView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); "
                + "for(var i=0;i<objs.length;i++)  " + "{" + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imagelistner.openImage(this.src);  " + "    }  " + "}" + "})()");

        Logger.i("JavascriptInterface", "JavascriptInterface addImageClickListner:11");
    }

    // js通信接口
    public class JavascriptInterface
    {

        private Context context;

        public JavascriptInterface(Context context)
        {
            this.context = context;
        }

        public void openImage(String img)
        {
            Logger.i("JavascriptInterface", "JavascriptInterface openImage:" + img);
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
            System.out.println(img);
        }
    }

    // 监听
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {

            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }
}
