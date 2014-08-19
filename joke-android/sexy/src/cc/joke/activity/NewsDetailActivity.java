package cc.joke.activity;

import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.entity.T_News;
import cc.joke.util.Util;

/**
 * 新闻消息详情界面
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener
{

    private WebView mWebView;

    private T_News mNews;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        int newsID = getIntent().getIntExtra("NEWS_ID", 0);
        List<T_News> list = GlobalApplication.dBHelper.query(T_News.class, "_id = ?",
                new String[] {String.valueOf(newsID)}, null);
        if (list == null || list.size() == 0)
        {
            Toast.makeText(this, "该消息不存在!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mNews = list.get(0);
        /** 初始化头部显示 **/
        ImageView backView = (ImageView) findViewById(R.id.goback);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(this);
        TextView titleView = (TextView) findViewById(R.id.product_top_title);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(mNews.getTitle());
        /** 初始化中间内容 **/
        mWebView = (WebView) findViewById(R.id.news_detail_web_view);
        /** 填充数据 **/
        mWebView.loadDataWithBaseURL(null, mNews.getDetailContent(), "text/html", "UTF-8", null);
    }

    @Override
    public void onClick(View v)
    {
        int viewID = v.getId();
        if (viewID == R.id.goback)
        {
            this.finish();
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
