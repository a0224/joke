package cc.joke.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.dialog.LoadingDialog;
import cc.joke.entity.CheckClient;
import cc.joke.http.CheckClientRequest;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;
import cc.joke.util.ViewUtil;

/**
 * 关于界面
 */
public class AboutActivity extends BaseActivity
{

    private LoadingDialog mDialog;

    private Dialog mMessageDialog;

    private Button mCheckButton;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            if (mDialog != null)
            {
                mDialog.dismiss();
                mDialog = null;
            }
            switch (msg.what)
            {
                case 0:
                    Toast.makeText(AboutActivity.this, "当前是最新版本!", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    CheckClient cc = (CheckClient) msg.obj;
                    if (cc != null)
                    {
                        new VerCheDilog().showCheckMessage(cc,AboutActivity.this,(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                    }
                    break;
            }
        };
    };

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_about);
        /** 初始化头部显示 **/
        ImageView backView = (ImageView) findViewById(R.id.goback);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new ImageView.OnClickListener()
        {
            public void onClick(View v)
            {
                Util.exitActivity(AboutActivity.this);
            }
        });
        TextView titleView = (TextView) findViewById(R.id.product_top_title);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText("关于");
        ImageView shareView = (ImageView) findViewById(R.id.share);
        shareView.setVisibility(View.VISIBLE);
        shareView.setOnClickListener(new ImageView.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", getString(R.string.share_app_market));
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                Intent localIntent2 = Intent.createChooser(intent, "分享...");
                startActivity(localIntent2);
            }
        });
        int w = GlobalApplication.widthPixels;
        int h = GlobalApplication.heightPixels;
        findViewById(R.id.about_layout).setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                AboutActivity.this.onTouchEvent(event);
                return false;
            }
        });
        findViewById(R.id.about_layout).getLayoutParams().height = h - Util.getStatusBarHeight(this);
        RelativeLayout.LayoutParams lp = null;
        /** 动态计算设置适配分辨率大小布局 **/
        ImageView iconImage = (ImageView) findViewById(R.id.about_icon);
        lp = (RelativeLayout.LayoutParams) iconImage.getLayoutParams();
        lp.topMargin = (int) (h * 0.04);
        int iconWidth = (int) (w * 0.28);
        ViewUtil.setView(iconImage, iconWidth, iconWidth);
        LinearLayout ll = (LinearLayout) findViewById(R.id.about_center_container);
        lp = (RelativeLayout.LayoutParams) ll.getLayoutParams();
        lp.topMargin = (int) (h * 0.035);
        /** 绑定业务处理事件 **/
        mCheckButton = (Button) findViewById(R.id.about_check);
        mCheckButton.setOnClickListener(mCheckListener);
        lp = (RelativeLayout.LayoutParams) mCheckButton.getLayoutParams();
        lp.topMargin = (int) (h * 0.035);
        lp = (RelativeLayout.LayoutParams) findViewById(R.id.about_bottom_container).getLayoutParams();
        lp.topMargin = (int) (h * 0.05);
    }


    /** 检测更新事件监听处理器 **/
    private Button.OnClickListener mCheckListener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            if (!Util.isNetworkConnected())
            {
                Toast.makeText(AboutActivity.this, "当前检测无网络!", Toast.LENGTH_SHORT).show();
                return;
            }
            mDialog = new LoadingDialog(AboutActivity.this);
            mDialog.show();
            ThreadPool.add(new CheckThread());
        }
    };

    class CheckThread implements Runnable
    {
        public void run()
        {
            CheckClientRequest request = new CheckClientRequest();
            int versionCode = 0;
            try
            {
                PackageInfo pkg = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
                if (pkg != null)
                {
                    versionCode = pkg.versionCode;
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            CheckClient cc = request.getCheckClient(versionCode);
            if (cc == null)
            {
                mHandler.sendEmptyMessage(0);
            }
            else
            {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = cc;
                mHandler.sendMessage(msg);
            }
        }
    }

    boolean isFirst = true;

    int mStartPosition = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN)
        {
            mStartPosition = (int) event.getX();
        }
        else if (action == MotionEvent.ACTION_MOVE)
        {
            if (isFirst)
            {
                isFirst = false;
                mStartPosition = (int) event.getX();
            }
        }
        else if (action == MotionEvent.ACTION_UP)
        {
            int moveX = ((int) event.getX()) - mStartPosition;
            if (moveX > Constants.MOVE_SIZE)
            {
                Util.exitActivity(this);
            }
            isFirst = true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Util.exitActivity(this);
        }
        return false;
    }

}
