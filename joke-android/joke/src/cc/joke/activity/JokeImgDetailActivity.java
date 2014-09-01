package cc.joke.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.adapter.MyFragmentPagerImgAdapter;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.entity.T_JokeImgInfo;
import cc.joke.util.Util;

public class JokeImgDetailActivity extends FragmentActivity
{
    // ViewPager
    private ViewPager viewPager;

    // 页签
    private List<Fragment> fragments = new ArrayList<Fragment>();

    // 页签对应的标题
    private List<String> titles = new ArrayList<String>();

    // 应用名称
    private TextView mTopTitle;

    boolean isFirst = true;

    int mStartPosition = 0;

    // 详情片段
    private JokeImgDetailFragment jokeDetailFragment;

    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
        setContentView(R.layout.joke_activity);
        GlobalApplication.activityManager.pushActivity(this);

        initViews();
    }

    private void initViews()
    {
        // title
        mTopTitle = (TextView) findViewById(R.id.product_top_title);
        mTopTitle.setVisibility(View.VISIBLE);
        mTopTitle.requestFocus();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        T_JokeImgInfo jokeInfo = (T_JokeImgInfo) getIntent().getSerializableExtra("jokeinfo");

        int currentItem = getIntent().getIntExtra("currentItem", 0);
        jokeDetailFragment = new JokeImgDetailFragment(jokeInfo, mTopTitle);

        fragments.add(jokeDetailFragment);
        fragments.add(new JokeCommentFragment(jokeInfo.getId()));

        titles.add("详情");
        titles.add("评论");

        viewPager.setAdapter(new MyFragmentPagerImgAdapter(getSupportFragmentManager(), fragments, titles));
        viewPager.setCurrentItem(currentItem);
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                if (viewPager.getCurrentItem() == 0)
                {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        mStartPosition = (int) event.getX();
                    }
                    if (action == MotionEvent.ACTION_MOVE)
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
                            Util.exitActivity(JokeImgDetailActivity.this);
                        }
                        isFirst = true;
                    }
                }
                return false;
            }
        });

        /** 返回 **/
        ImageView goback = (ImageView) findViewById(R.id.goback);
        goback.setVisibility(View.VISIBLE);
        goback.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Util.exitActivity(JokeImgDetailActivity.this);
            }
        });

        /** 分享 **/
        ImageView shareView = (ImageView) findViewById(R.id.share);
        shareView.setVisibility(View.VISIBLE);
        shareView.setOnClickListener(new ImageView.OnClickListener()
        {
            public void onClick(View v)
            {
                if (jokeDetailFragment != null)
                {
                    jokeDetailFragment.share();
                }
            }
        });
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
}
