package cc.joke.view;

import cc.joke.activity.SlidingActivity;
import cc.joke.entity.ListType;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class MyViewPager extends ViewPager
{

    // 悬浮标题栏
    private TitleFlowIndicator mIndicator;

    // 是否显示
    private boolean visiable;

    private int screenWidth;

    private SlidingActivity slidactivity;

    public void setSlidingActivity(SlidingActivity slidactivity)
    {
        this.slidactivity = slidactivity;
    }

    public void setFlowIndicator(TitleFlowIndicator flowIndicator)
    {
        mIndicator = flowIndicator;
        mIndicator.setViewPager(this);

        // 点击悬浮标题栏切换事件监听
        mIndicator.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                // 菜单的宽度
                int btnWidth = 50;
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        if (slidactivity.getTitleVisiable())
                        {
                            ListType.type type = ListType.type.values()[getCurrentItem()];
                            switch (type)
                            {
                                case joke:
                                    if (event.getRawX() >= screenWidth - btnWidth && event.getRawX() < screenWidth)
                                    {
                                        slidactivity.switchPager(ListType.type.headlines);
                                    }
                                    break;
                                case headlines:
                                    if (event.getRawX() > 0 && event.getRawX() <= btnWidth)
                                    {
                                        slidactivity.switchPager(ListType.type.joke);
                                    }
                                    if (event.getRawX() >= screenWidth - btnWidth && event.getRawX() < screenWidth)
                                    {
                                        slidactivity.switchPager(ListType.type.sexy);
                                    }
                                    break;
                                case sexy:
                                    if (event.getRawX() > 0 && event.getRawX() <= btnWidth)
                                    {
                                        slidactivity.switchPager(ListType.type.headlines);
                                    }
                                    break;
                            }
                            return true;
                        }
                }
                return false;
            }
        });
    }

    public MyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        WindowManager windowManager = ((Activity) getContext()).getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        // TODO Auto-generated method stub
        super.onScrollChanged(l, t, oldl, oldt);
        mIndicator.setIsDraw(true);
        mIndicator.onScrolled(l, t, oldl, oldt);
    }
}
