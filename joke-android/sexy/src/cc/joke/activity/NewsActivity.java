package cc.joke.activity;

import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.adapter.NewsAdapter;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.entity.T_News;
import cc.joke.util.Util;

/**
 * 新闻消息列表界面
 */
public class NewsActivity extends BaseActivity implements View.OnClickListener
{

    private ListView mListView;

    private NewsAdapter mAdapter;

    private List<T_News> mList;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        /** 初始化头部显示 **/
        ImageView backView = (ImageView) findViewById(R.id.goback);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(this);
        TextView titleView = (TextView) findViewById(R.id.product_top_title);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText("消息");
        /** 初始化列表内容 **/
        mListView = (ListView) findViewById(R.id.news_list_view);
        mList = GlobalApplication.dBHelper.query(T_News.class, null, null, "_id DESC");
        mAdapter = new NewsAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(R.id.empty_view));
    }

    public void onClick(View v)
    {
        int viewID = v.getId();
        if (viewID == R.id.goback)
        {
            Util.exitActivity(this);
            return;
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
