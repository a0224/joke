package cc.joke.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.activity.AboutActivity.CheckThread;
import cc.joke.adapter.MyFragmentPagerAdapter;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.BitmapCache;
import cc.joke.debug.Logger;
import cc.joke.dialog.LoadingDialog;
import cc.joke.dialog.VerCheDilog;
import cc.joke.entity.CheckClient;
import cc.joke.entity.ListType;
import cc.joke.http.ActivationRequest;
import cc.joke.http.CheckClientRequest;
import cc.joke.http.LoginRequest;
import cc.joke.http.MessageRequest;
import cc.joke.service.DsGameMarketService;
import cc.joke.util.BaiduPushUtils;
import cc.joke.util.JokeDownloadManager;
import cc.joke.util.PreferencesUtil;
import cc.joke.util.QQ;
import cc.joke.util.QQ.LoginListener;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;
import cc.joke.util.WqAd;
import cc.joke.view.MyViewPager;
import cc.joke.view.SlidingMenu;
import cc.joke.view.TitleFlowIndicator;
import cc.joke.wiipay.Wiipay;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.silentupdate.SilentManager;

public class SlidingActivity extends FragmentActivity
{
    
    private static final String preference = "salidingActivity preference";
    
    private static final String key_first_start = "first start";
    
    public SlidingMenu mSlidingMenu;

    // 是否正在加载的标识
    private boolean isLoadFlag = true;
    
    private LoadingDialog mDialog;

    // 滚球个数
    private Integer[] balls = {R.id.ball1, R.id.ball2, R.id.ball3, R.id.ball4, R.id.ball5, R.id.ball6};

    // 等待首页加载完毕合计锁
    private CountDownLatch endIndexOver = new CountDownLatch(1);

    // 是否退出
    private boolean isExit = false;

    // 右侧菜单
    private View mRightView;

    /** 左侧菜单 **/
    // private View mLeftView;
    // 正文
    private View centerContent;

    // viewPager
    private MyViewPager viewPager;

    // 页签
    private List<Fragment> fragments = new ArrayList<Fragment>();

    // 页签对应的标题
    private List<String> titles = new ArrayList<String>();

    // 当前下拉菜单选中对象
    private ListType.type type = ListType.type.values()[0];

    private LoadingDialog mLoadingDialog;

    private Dialog mMessageDialog;

    // 首页下拉菜单
    // private PopupWindow indexPop;
    // 首页下拉菜单view
    // private View indexPopView;
    // 标题
    private TitleFlowIndicator mIndicator;

    // 中间部分的滚动条
    private ImageView seekBar;

    // 接收消息处理
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:// 滚球
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    for (int i = 0; i < balls.length; i++)
                    {
                        ImageView ball = (ImageView) findViewById(balls[i]);
                        if (msg.what == i)
                        {
                            GlobalApplication.bitmapCache.getBitmap(R.drawable.ball_2 + "", ball, null);
                        }
                        else
                        {
                            GlobalApplication.bitmapCache.getBitmap(R.drawable.ball_1 + "", ball, null);
                        }
                    }
                    break;
                case 6:// 加载mSlidingMenu
                       // 不能滚动
                    mSlidingMenu.setCanSliding(false, false);
                    // 初始化 viewPager 页签 & 标题
                    // fragments.add(new MainFragment(SlidingActivity.this,
                    // endIndexOver));
                    fragments.add(new JokeFragment());
                    fragments.add(new JokeImgFragment());
                    fragments.add(new HeadlinesFragment());
                    fragments.add(new SexyFragment());
                    for (int i = 0; i < ListType.typeStr.values().length; i++)
                    {
                        titles.add(ListType.typeStr.values()[i].name());
                    }
                    // 设置adapter
                    MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments,
                            titles);
                    
//                    viewPager.setOffscreenPageLimit(3);
                    viewPager.setAdapter(adapter);
                    viewPager.setSlidingActivity(SlidingActivity.this);
                    mIndicator.setTitleProvider(adapter);
                    viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
                    // 添加菜单和正文
                    // mSlidingMenu.setLeftView(mLeftView);
                    mSlidingMenu.setRightView(mRightView);
                    mSlidingMenu.setCenterView(centerContent);
                    viewPager.setFlowIndicator(mIndicator);

                    break;
                case 7:
                    Toast.makeText(SlidingActivity.this, "当前是最新版本!", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    CheckClient cc = (CheckClient) msg.obj;
                    if (cc != null)
                    {
                        new VerCheDilog().showCheckMessage(cc,SlidingActivity.this,(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                    }
                    break;
                case 9:
                    LinearLayout balls = (LinearLayout)findViewById(R.id.loading_balls);
                    balls.setVisibility(View.GONE);
                    
                    Button btnStart = (Button)findViewById(R.id.btn_start);
                    btnStart.setVisibility(View.VISIBLE);
                    
                    ImageView illustration = (ImageView)findViewById(R.id.illustration);
                    illustration.setVisibility(View.GONE);
                    
                    TextView introduction = (TextView)findViewById(R.id.introduction);
                    introduction.setVisibility(View.VISIBLE);
                    
                    btnStart.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mHandler.sendMessage(mHandler.obtainMessage(10000));
                            
                            PreferencesUtil.setPreferences(SlidingActivity.this, preference, key_first_start, false);
                        }
                    });
                    break;
                case 1000:// SD卡不存在
                    Util.toast(SlidingActivity.this, Util.getResourcesStr(R.string.sd_no_found));
                    break;
                case 10000:// 退出加载状态
                    RelativeLayout loadWindow = (RelativeLayout) findViewById(R.id.initWindow);
                    loadWindow.setVisibility(View.GONE);
                    // final PayView payView = (PayView)
                    // findViewById(R.id.payWindow);

                    // if (Wiipay.getInstance().isPayed(-1)) {
                    // payView.setVisibility(View.GONE);
                    // } else {
                    // payView.setPayListener(new PayListener() {
                    // @Override
                    // public void onPaySucceed() {
                    // payView.setVisibility(View.GONE);
                    // }
                    //
                    // public void onPayFailed() {
                    //
                    // };
                    // });
                    // }

                    break;
                case 20001:
                    Bundle bundle = (Bundle) msg.obj;
                    final String userName = bundle.getString("username");
                    final String userIcon = bundle.getString("usericon");
                    TextView loginTextView = (TextView) mRightView.findViewById(R.id.right_menu_txt_login);
                    loginTextView.setText(userName);
                    final ImageView loginImageView = (ImageView) mRightView.findViewById(R.id.right_menu_img_login);
                    BitmapCache.getInstance().getBitmap(userIcon, loginImageView, null, null);
                    break;
            }
        }
    };
    
    
    private Handler checkHandler = new Handler()
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
                case 0:Toast.makeText(SlidingActivity.this, "当前是最新版本!", Toast.LENGTH_SHORT).show();break;
                case 1:
                    CheckClient cc = (CheckClient) msg.obj;
                    if (cc != null)
                    {
                        new VerCheDilog().showCheckMessage(cc,SlidingActivity.this,(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                    }
                    break;
            }
        };
    };

    public boolean getTitleVisiable()
    {
        return titleVisiable;
    }

    // 是否有移动
    private boolean isFirst = true;

    private float start;

    private float startTrans;

    private int times = 0;

    // 是否显示
    private boolean titleVisiable = false;

    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.main);
        GlobalApplication.activityManager.pushActivity(this);
        QQ.getInstance(this);
        Wiipay.getInstance(this);
        WqAd.getInstance(this);
        JokeDownloadManager.getInstance(this);

        //push：
        if (!BaiduPushUtils.hasBind(getApplicationContext())) {
            PushManager.startWork(getApplicationContext(),
                    PushConstants.LOGIN_TYPE_API_KEY,
                    BaiduPushUtils.getMetaValue(SlidingActivity.this, "api_key"));
        }
        
        // 检查SD卡
        if (!Util.hasSdcard())
        {
            mHandler.sendMessage(mHandler.obtainMessage(1000));
            return;
        }

        // 开始滚球动画
        ThreadPool.add(new BeginRollBall());

        // 等待首页加载完毕
        ThreadPool.add(new WaitIndex());

        // 运营消息
        ThreadPool.add(new MessageRequest(SlidingActivity.this));

        /** 请求激活 **/
        ThreadPool.add(new Runnable()
        {

            @Override
            public void run()
            {
                new ActivationRequest().activation();
            }
        });
        
		if (Util.isNetworkConnected()) {
			mDialog = new LoadingDialog(SlidingActivity.this);
			mDialog.show();
			ThreadPool.add(new CheckThread());
		} else {
			Toast.makeText(this, "请检查网络！", Toast.LENGTH_SHORT).show();
		}


        // 初始化
        ThreadPool.add(new InitThread());
        
        startPush();
        
        InitLogo();
    }

    private void InitLogo()
    {
        ImageView illustration = (ImageView) findViewById(R.id.illustration);
        illustration.setBackgroundResource(R.drawable.illustration_def);
        BitmapCache.getInstance().getBitmap("", illustration, null);
    }
    
    
	private void startPush() {
	    //关闭自带push 用百度的
		// 启动推送服务service
//		if (true) {
//			Log.i("", "connectionThread -- ：startPush");
//			Intent i = new Intent(this, DsGameMarketService.class);
//			i.setAction(DsGameMarketService.START_ACTION);
//			startService(i);
//		} else {
//			Intent i = new Intent(this, DsGameMarketService.class);
//			stopService(i);
//		}
	}
	
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    // 初始化
    class InitThread implements Runnable
    {
        public void run()
        {

            centerContent = getLayoutInflater().inflate(R.layout.super_activity, null);

            TextView title = (TextView) centerContent.findViewById(R.id.title);
            title.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.app_name));
            titleVisiable = true;

            mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
            mRightView = getLayoutInflater().inflate(R.layout.right_menu, null);

            seekBar = (ImageView) centerContent.findViewById(R.id.seekBar);
            // 添加设置标题栏
            mIndicator = (TitleFlowIndicator) centerContent.findViewById(R.id.vfc);
            viewPager = (MyViewPager) centerContent.findViewById(R.id.viewPager);
            // 设置标题
            mIndicator.setViewPager(viewPager);

            viewPager.setOnPageChangeListener(new OnPageChangeListener()
            {
                public void onPageSelected(int arg0)
                {
                    // 换头部显示
                    if (isFirst())
                    {
                        mSlidingMenu.setCanSliding(false, false);
                    }
                    else if (isEnd())
                    {
                        mSlidingMenu.setCanSliding(false, true);
                    }
                    else
                    {
                        mSlidingMenu.setCanSliding(false, false);
                    }
                    isExit = false;
                }

                // 这里做悬浮标题的操作
                public void onPageScrolled(int position, float param1, int param2)
                {
                    if (times == 0)
                    {
                        seekBar.setVisibility(View.VISIBLE);
                        mIndicator.setVisibility(View.VISIBLE);
                    }

                    // 四分之一个屏幕之前开启一个动画
                    if (param1 < 0.25f)
                    {
                        float gradual = 1.0f - (4 * param1);
                        AlphaAnimation seekAnima = new AlphaAnimation(1.0f, gradual);
                        seekAnima.setFillAfter(true);
                        seekBar.startAnimation(seekAnima);
                    }
                    // 超过四分之三屏幕后再开启一个动画
                    if (0.75 <= param1 && param1 < 1.0)
                    {
                        float gradual = (4 * param1) - 3.0f;
                        AlphaAnimation seekAnima = new AlphaAnimation(0.0f, gradual);
                        seekAnima.setFillAfter(true);
                        seekBar.startAnimation(seekAnima);
                    }
                    times++;
                    mIndicator.onSwitched(position);
                }

                public void onPageScrollStateChanged(int param)
                {

                }
            });
            // 我的应用
            ImageView mail_btn = (ImageView) centerContent.findViewById(R.id.mail);
            mail_btn.setVisibility(View.VISIBLE);
            mail_btn.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    Intent intent = new Intent();
                    intent.setClass(SlidingActivity.this, AppActivity.class);
                    startActivity(intent);
                    // WqAd.getInstance().showAppMarket();
                }
            });

            // 设置
            ImageView setting_btn = (ImageView) centerContent.findViewById(R.id.setting);
            setting_btn.setVisibility(View.VISIBLE);
            setting_btn.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    showRight();
                }
            });

            // 我的应用
            LinearLayout myAppLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_myapps);
            myAppLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    showRight();
                    WqAd.getInstance().showAppMarket();
                    // Intent intent = new Intent(SlidingActivity.this,
                    // AppActivity.class);
                    // startActivity(intent);
                }
            });

            // 我的发布
            LinearLayout downloadLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_mypublish);
            downloadLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    showRight();
                    Intent intent = new Intent(SlidingActivity.this, PublishActivity.class);
                    startActivity(intent);
                }
            });

            int rightMenuListHeight = (int) (GlobalApplication.heightPixels * 0.082);
            // 登陆
            LinearLayout loginLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_login);
            loginLayout.getLayoutParams().height = rightMenuListHeight;
            loginLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    if (GlobalApplication.getLoginInfo() == null)
                    {
                        showRight();
                        // Intent intent = new Intent(SlidingActivity.this,
                        // LoginActivity.class);
                        // Util.enterActivity(SlidingActivity.this, intent);
                        QQ.getInstance().loginQQ();
                    }
                    else
                    {
                        Util.toast(SlidingActivity.this, "已登陆");
                    }
                }
            });
            loginLayout.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    TextView txt = (TextView) findViewById(R.id.right_menu_txt_login);
                    ImageView image = (ImageView) findViewById(R.id.right_menu_img_login);
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text_pressed));
                    }
                    else if (action == MotionEvent.ACTION_UP)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text));
                    }
                    return false;
                }
            });
            // 消息
            LinearLayout messageLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_message);
            messageLayout.getLayoutParams().height = rightMenuListHeight;
            messageLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    showRight();
                    Intent intent = new Intent(SlidingActivity.this, NewsActivity.class);
                    Util.enterActivity(SlidingActivity.this, intent);
                }
            });
            messageLayout.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    TextView txt = (TextView) findViewById(R.id.right_menu_txt_message);
                    ImageView image = (ImageView) findViewById(R.id.right_menu_img_message);
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text_pressed));
                        image.setImageResource(R.drawable.rightmenu_message_pressed);
                    }
                    else if (action == MotionEvent.ACTION_UP)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text));
                        image.setImageResource(R.drawable.rightmenu_message);
                    }
                    return false;
                }
            });
            // 更新
            LinearLayout updateLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_update);
            updateLayout.getLayoutParams().height = rightMenuListHeight;
            updateLayout.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    TextView txt = (TextView) findViewById(R.id.right_menu_txt_update);
                    ImageView image = (ImageView) findViewById(R.id.right_menu_img_update);
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text_pressed));
                        image.setImageResource(R.drawable.rightmenu_update_pressed);
                    }
                    else if (action == MotionEvent.ACTION_UP)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text));
                        image.setImageResource(R.drawable.rightmenu_update);
                    }
                    return false;
                }
            });
            updateLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    showRight();
                    if (!Util.isNetworkConnected())
                    {
                        Toast.makeText(SlidingActivity.this, "当前检测无网络!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mLoadingDialog == null)
                    {
                        mLoadingDialog = new LoadingDialog(SlidingActivity.this);
                    }
                    mLoadingDialog.show();
                    // 请求
                    ThreadPool.add(new Runnable()
                    {
                        @Override
                        public void run()
                        {

                            CheckClientRequest request = new CheckClientRequest();
                            int versionCode = 0;
                            try
                            {
                                PackageInfo pkg = getPackageManager().getPackageInfo(getPackageName(),
                                        PackageManager.GET_ACTIVITIES);
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
                            if (cc != null && cc.getVersionCode() > versionCode)
                            {
                                Message msg = new Message();
                                msg.what = 8;
                                msg.obj = cc;
                                mHandler.sendMessage(msg);
                            }
                            else
                            {
                                mHandler.sendEmptyMessage(7);
                            }
                            mLoadingDialog.cancel();
                        }
                    });
                }
            });

            // 关于
            LinearLayout aboutLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_about);
            aboutLayout.getLayoutParams().height = rightMenuListHeight;
            aboutLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    showRight();
                    Intent intent = new Intent(SlidingActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
            });
            aboutLayout.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    TextView txt = (TextView) findViewById(R.id.right_menu_txt_about);
                    ImageView image = (ImageView) findViewById(R.id.right_menu_img_about);
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text_pressed));
                        image.setImageResource(R.drawable.rightmenu_about_pressed);
                    }
                    else if (action == MotionEvent.ACTION_UP)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text));
                        image.setImageResource(R.drawable.rightmenu_about);
                    }
                    return false;
                }
            });
            // 刷新
            LinearLayout refrenshLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_refrensh);
            refrenshLayout.getLayoutParams().height = rightMenuListHeight;
            refrenshLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    refresh();
                    showRight();
                }
            });
            refrenshLayout.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    TextView txt = (TextView) findViewById(R.id.right_menu_txt_refersh);
                    ImageView image = (ImageView) findViewById(R.id.right_menu_img_refersh);
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text_pressed));
                        image.setImageResource(R.drawable.rightmenu_refrenshs_pressed);
                    }
                    else if (action == MotionEvent.ACTION_UP)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text));
                        image.setImageResource(R.drawable.rightmenu_refrenshs);
                    }
                    return false;
                }
            });
            // 退出
            LinearLayout exitLayout = (LinearLayout) mRightView.findViewById(R.id.rightmenu_exit);
            exitLayout.getLayoutParams().height = rightMenuListHeight;
            exitLayout.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    exit();
                }
            });
            exitLayout.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event)
                {
                    TextView txt = (TextView) findViewById(R.id.right_menu_txt_exit);
                    ImageView image = (ImageView) findViewById(R.id.right_menu_img_exit);
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text_pressed));
                        image.setImageResource(R.drawable.rightmenu_exit_pressed);
                    }
                    else if (action == MotionEvent.ACTION_UP)
                    {
                        txt.setTextColor(getResources().getColor(R.color.right_menu_list_text));
                        image.setImageResource(R.drawable.rightmenu_exit);
                    }
                    return false;
                }
            });
            LoginListener loginListener = new LoginListener()
            {

                @Override
                public void onError()
                {

                }

                @Override
                public void onCancel()
                {

                }

                @Override
                public void doComplete(final String iconUrl, final String userName, final String openid,
                        final String token)
                {
                    if (GlobalApplication.getLoginInfo() == null)
                    {
                        updateLoginUI(iconUrl, userName);
                        ThreadPool.add(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                new LoginRequest().login(iconUrl, userName, openid, token);
                            }
                        });
                    }
                }

            };
            QQ.getInstance().setLoginListener(loginListener);

            if (GlobalApplication.getLoginInfo() != null)
            {
                String iconUrl = GlobalApplication.getLoginInfo().getUsericon();
                String userName = GlobalApplication.getLoginInfo().getUsername();
                updateLoginUI(iconUrl, userName);
            }

            // 加载视图
            mHandler.sendMessage(mHandler.obtainMessage(6));
        }
    }

    private void updateLoginUI(final String iconUrl, final String userName)
    {
        if (userName == null || iconUrl == null)
        {
            return;
        }
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("username", userName);
        bundle.putString("usericon", iconUrl);
        msg.what = 20001;
        msg.obj = bundle;
        mHandler.sendMessage(msg);
    }

    // 滚球动画线程
    class BeginRollBall implements Runnable
    {
        private int rollCount = 0;// 已经滚了多少次

        public void run()
        {
            while (isLoadFlag)
            {
                try
                {
                    int what = rollCount % balls.length;
                    mHandler.sendMessage(mHandler.obtainMessage(what));
                    rollCount++;
                    Thread.sleep(300);// 0.3秒滚一次
                }
                catch (Exception e)
                {
                    Logger.error(e);
                }
            }
            // 不再滚，退出加载中的状态
            boolean isFirstStart = PreferencesUtil.getPreference(SlidingActivity.this, preference, key_first_start,
                    true);
            if (isFirstStart)
            {
                mHandler.sendMessage(mHandler.obtainMessage(9));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(10000));
            }
        }
    }

    // 等待 首页 加载完毕线程
    class WaitIndex implements Runnable
    {
        public void run()
        {
            try
            {
                // endIndexOver.await();
                Thread.sleep(2300);
                isLoadFlag = false;
            }
            catch (Exception e)
            {
                Logger.error(e);
            }
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isExit = false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // 监听返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if (mSlidingMenu != null && mSlidingMenu.getCenterView() != null
                    && mSlidingMenu.getCenterView().getScrollX() != 0)
            {
                if (mSlidingMenu.isShowLeft())
                {
                    mSlidingMenu.showLeftView();
                }
                if (mSlidingMenu.isShowRight())
                {
                    mSlidingMenu.showRightView();
                }
                return false;
            }
            if (!isExit)
            {
                Util.toast(SlidingActivity.this, getResources().getString(R.string.again_exit));
                isExit = true;
            }
            else
            {
                exit();
            }
            return false;
            // 菜单键
        }
        else if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0)
        {
            showRight();
            // onClickLogin();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 退出
    public void exit()
    {
        QQ.getInstance().LogoutQQ();
        GlobalApplication.activityManager.exit();
        GlobalApplication.bitmapCache.clearCache();
        GlobalApplication.bitmapCache = null;
        GlobalApplication.mUpgradeCache.clear();
        GlobalApplication.mUpgradeCache = null;
        JokeDownloadManager.getInstance().clear();
        System.gc();
    }

    // 刷新
    public void refresh()
    {
        if (fragments.size() == 0)
        {
            return;
        }
        Fragment f = fragments.get(viewPager.getCurrentItem());
        if (f instanceof NetWorkListener)
        {
            NetWorkListener r = (NetWorkListener) f;
            r.startNetwork();
        }
    }

    public void showRight()
    {
        mSlidingMenu.showRightView();
    }

    public void switchPager(ListType.type type)
    {
        viewPager.setCurrentItem(type.ordinal(), false);
    }

    public boolean isFirst()
    {
        if (viewPager.getCurrentItem() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isEnd()
    {
        if (viewPager.getCurrentItem() == fragments.size() - 1)
            return true;
        else
            return false;
    }

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
            if (cc != null && cc.getVersionCode() > versionCode)
            {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = cc;
                checkHandler.sendMessage(msg);
            }else{
                mDialog.dismiss();
            }
        }
    }
    
}
