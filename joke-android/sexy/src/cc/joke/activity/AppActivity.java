package cc.joke.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.adapter.AppListAdapter;
import cc.joke.common.Constants;
import cc.joke.entity.T_Result;
import cc.joke.http.AppListRequest;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;
import cc.joke.view.refreshlist.PullToRefreshListView;
import cc.joke.view.refreshlist.PullToRefreshListView.PullAndRefreshListViewListener;

/**
 * 我的应用
 * 
 * @author wanghao
 */
public class AppActivity extends BaseActivity implements PullAndRefreshListViewListener
{

    private View mListEmpty;

    private View mProgress;

    protected PullToRefreshListView mListview;

    public List mDataList = new ArrayList();

    private BaseAdapter mAdapter;

    private int pageNo = 1;

    public boolean mLoadRunning = false;

    public Handler mHandler = new Handler()
    {

        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case -1:
                    if (mDataList.isEmpty())
                    {
                        mListEmpty.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mListEmpty.setVisibility(View.GONE);
                    }

                    mListview.stopRefresh();
                    mListview.stopLoadMore();

                    mLoadRunning = false;
                    break;
                case 1:// 加载数据
                {
                    final List list = (List) msg.obj;
                    mListview.stopRefresh();
                    mDataList.clear();
                    mDataList.addAll(list);

                    mAdapter.notifyDataSetChanged();
                    
                    if (mDataList.isEmpty())
                    {
                        mListEmpty.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mListEmpty.setVisibility(View.GONE);
                    }

                    mLoadRunning = false;
                }
                    break;
                case 2:// 更新adapter
                {
                    final List list = (List) msg.obj;
                    mDataList.addAll(list);
                    mListview.stopLoadMore();

                    mAdapter.notifyDataSetChanged();

                    if (mDataList.isEmpty())
                    {
                        mListEmpty.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mListEmpty.setVisibility(View.GONE);
                    }

                    mLoadRunning = false;
                }
                    break;
                case 7:
                    Util.toast(AppActivity.this, AppActivity.this.getString(R.string.app_no_more_data));

                    mLoadRunning = false;
                    break;
            }
            if (mProgress.getVisibility() != View.GONE)
            {
                mProgress.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onLoadMore()
    {

        if (!Util.isNetworkConnected())
        {
            return;
        }

        if (mLoadRunning)
        {
            return;
        }

        mLoadRunning = true;

        ThreadPool.add(new Runnable()
        {
            @Override
            public void run()
            {
                T_Result result = sendRequest(++pageNo);

                if (result.mData == null)
                {
                    mHandler.sendEmptyMessage(-1);
                }
                else
                {
                    final List list = (List) result.mData;
                    if (list != null)
                    {
                        Message msg = mHandler.obtainMessage(2);
                        msg.obj = list;
                        mHandler.sendMessage(msg);
                    }
                }
            }
        });
    }

    // 下拉刷新
    public void onRefresh()
    {
        if (!Util.isNetworkConnected())
        {
            Toast.makeText(this, "请检查网络！", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(-1);
            return;
        }
        if (mLoadRunning)
        {
            return;
        }
        mLoadRunning = true;

        ThreadPool.add(new Runnable()
        {
            @Override
            public void run()
            {

                pageNo = 1;
                T_Result result = sendRequest(pageNo);
                if (result.mData != null)
                {
                    List list = (List) result.mData;
                    Message msg = mHandler.obtainMessage(1);
                    msg.obj = list;

                    mHandler.sendMessage(msg);
                }
                else
                {
                    mLoadRunning = false;
                    mHandler.sendEmptyMessage(-1);
                }
            }
        });
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.joke_app);
      
        initViews();

        onRefresh();
    }

    private void initViews(){
    	
    	  /** 初始化头部显示 **/
        ImageView backView = (ImageView) findViewById(R.id.goback);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new ImageView.OnClickListener()
        {
            public void onClick(View v)
            {
                Util.exitActivity(AppActivity.this);
            }
        });
        // 标题
        TextView titleView = (TextView) findViewById(R.id.product_top_title);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText("热门应用");

        mListview = (PullToRefreshListView) findViewById(R.id.listView);
        mAdapter = getAdapter(AppActivity.this, mDataList);
        mListview.setPullLoadEnable(true);
        mListview.setPullRefreshEnable(true);
        mListview.setPullAndRefreshListViewListener(AppActivity.this);
        mListview.setAdapter(mAdapter);
        
        mListEmpty = findViewById(R.id.empty_view);
        mListEmpty.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                mProgress.setVisibility(View.VISIBLE);
                mListEmpty.setVisibility(View.GONE);
                onRefresh();
            }
        });

        mProgress = findViewById(R.id.progress_bar);
    }
    
    @Override
    protected void onDestroy()
    {
        System.gc();
        super.onDestroy();
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

    public BaseAdapter getAdapter(Context context, List dataList)
    {
        return new AppListAdapter(context, dataList);
    }

    public T_Result sendRequest(int pageNo)
    {
        return new AppListRequest().getApkList(Constants.OP_APK_LIST, pageNo);
    }

}
