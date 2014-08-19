package cc.joke.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.adapter.PublishListAdapter;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.dialog.PublishDialog;
import cc.joke.entity.T_Publish;
import cc.joke.entity.T_Result;
import cc.joke.http.JokePublishRequest;
import cc.joke.util.Baidu;
import cc.joke.util.HttpRequestUtil;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;
import cc.joke.view.refreshlist.PullToRefreshListView;
import cc.joke.view.refreshlist.PullToRefreshListView.PullAndRefreshListViewListener;

/**
 * 我的发布
 * 
 * @author wanghao
 */
public class PublishActivity extends BaseActivity implements PullAndRefreshListViewListener
{

    // 用户id
    public int userId = -1;

    private View mListEmpty;

    private View mProgress;

    private View mSendLayout;

    protected PullToRefreshListView mListview;

    public List<T_Publish> mDataList = new ArrayList();

    private BaseAdapter mAdapter;

    private int pageNo = 1;

    public boolean mLoadRunning = false;

    PublishDialog mPublishDialog;

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
                case 4:
                {
                    T_Publish comment = (T_Publish) msg.obj;
                    mDataList.add(0, comment);
                    mListview.scrollTo(0, 0);

                    mAdapter.notifyDataSetChanged();

                    if (mDataList.isEmpty())
                    {
                        mListEmpty.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mListEmpty.setVisibility(View.GONE);
                    }
                }
                    break;
                case 6:
                    Util.toast(PublishActivity.this, PublishActivity.this.getString(R.string.upload_error));
                case 7:
                    Util.toast(PublishActivity.this, PublishActivity.this.getString(R.string.app_no_more_data));

                    mLoadRunning = false;
                    break;
            }
            if (mProgress.getVisibility() != View.GONE)
            {
                mProgress.setVisibility(View.GONE);
            }
        }
    };

    // 发布笑话
    public void publish(final int userId, final String userName, final String userIcon, final String content,
            final List<String> imgPath)
    {
        final T_Publish publish = new T_Publish();
        publish.setContent(content);
        publish.setUsername(userName);
        publish.setUsericon(userIcon);
        publish.setUserid(userId);
        publish.setStatus(2);// 默认审核中

        Message msg = mHandler.obtainMessage(4);
        msg.obj = publish;
        mHandler.sendMessage(msg);

        // 异步提交
        ThreadPool.add(new Runnable()
        {
            @Override
            public void run()
            {
                commitPublish(publish, imgPath);
            }
        });
    }

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
        if (GlobalApplication.getLoginInfo() != null)
        {
            userId = GlobalApplication.getLoginInfo().getUserid();
        }

        setContentView(R.layout.joke_publish);

        initViews();

        onRefresh();
    }

    private void initViews()
    {

        /** 初始化头部显示 **/
        ImageView backView = (ImageView) findViewById(R.id.goback);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new ImageView.OnClickListener()
        {
            public void onClick(View v)
            {
                Util.exitActivity(PublishActivity.this);
            }
        });
        // 标题
        TextView titleView = (TextView) findViewById(R.id.product_top_title);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText("我的发布");

        mListview = (PullToRefreshListView) findViewById(R.id.listView);

        mAdapter = getAdapter(PublishActivity.this, mDataList);
        mListview.setPullLoadEnable(true);
        mListview.setPullRefreshEnable(true);
        mListview.setPullAndRefreshListViewListener(PublishActivity.this);
        mListview.setAdapter(mAdapter);

        mListEmpty = findViewById(R.id.empty_view);
        mListEmpty.setVisibility(View.GONE);

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
        mProgress.setVisibility(View.VISIBLE);

        mSendLayout = findViewById(R.id.publish_text);

        mSendLayout.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (mPublishDialog == null)
                {
                    mPublishDialog = new PublishDialog(PublishActivity.this);
                }
                mPublishDialog.show();
            }
        });

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
        return new PublishListAdapter(context, dataList);
    }

    public T_Result sendRequest(int pageNo)
    {
        return new JokePublishRequest().getPublishs(userId, pageNo);
    }

    private void commitPublish(T_Publish publish, final List<String> imgPath)
    {

        StringBuffer buff = new StringBuffer();
        try
        {
            if (imgPath != null)
            {
                for (String path : imgPath)
                {
                    File file = new File(path);

                    if (file.exists())
                    {
                        String url = Baidu.getInstance().uploadImage(file);
                        if(!TextUtils.isEmpty(url)){
                            buff.append(url).append(",");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }

        String url = buff.toString();
        if (!TextUtils.isEmpty(url))
        {
            publish.setImgUrl(url);

            new JokePublishRequest().commitPublish(publish);
        }
        else
        {
            mHandler.sendEmptyMessage(6);
        }

        // JSONObject str =
        // HttpRequestUtil.post(Constants.OP_JOKE_ADD_JOKE_IMAGE_URL,
        // file);
        // if (str != null && str.getInt("status") == 1)
        // {
        // url = str.getString("url");
        // publish.setImgUrl(url);
        // }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i("", "onActivityResult:"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            if (mPublishDialog != null)
            {
                mPublishDialog.showImage(picturePath, requestCode);
            }
        }

    }
}
