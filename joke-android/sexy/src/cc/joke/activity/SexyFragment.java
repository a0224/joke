package cc.joke.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.adapter.SexyListAdapter;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.entity.T_Result;
import cc.joke.entity.T_Sexy;
import cc.joke.http.SexyListRequest;
import cc.joke.util.PreferencesUtil;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;
import cc.joke.view.refreshlist.PullToRefreshListView;
import cc.joke.view.refreshlist.PullToRefreshListView.PullAndRefreshListViewListener;

@SuppressLint("ValidFragment")
@SuppressWarnings({"unchecked", "rawtypes"})
public class SexyFragment extends Fragment implements PullAndRefreshListViewListener, OnScrollListener
{
    private final String PREFERENCE_PAGE = "SexyFragment Preference";
    private final String PAGE_KEY = "SexyFragment Preference Page Key";
    private final String POSTION_INDEX_KEY = "SexyFragment Preference Postion Index Key";
    private final String POSTION_TOP_KEY = "SexyFragment Preference Postion Top Key";

    private View mView;

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
                case 0://加载本地数据
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
                
                	pageNo = PreferencesUtil.getPreference(getActivity(), PREFERENCE_PAGE, PAGE_KEY, 1);
                    
                    int index = PreferencesUtil.getPreference(getActivity(), PREFERENCE_PAGE, POSTION_INDEX_KEY, 0);
                    int top = PreferencesUtil.getPreference(getActivity(), PREFERENCE_PAGE, POSTION_TOP_KEY, 0);
                    mListview.setSelectionFromTop(index, top);
                }
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
                    Util.toast(getActivity(), getActivity().getResources().getString(R.string.app_no_more_data));

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
                T_Result result = sendRequest(++pageNo);

                if (result.mData == null)
                {
                    mHandler.sendEmptyMessage(-1);
                }
                else
                {
                	PreferencesUtil.setPreferences(getActivity(), PREFERENCE_PAGE, PAGE_KEY, pageNo);
                	
                    final List list = (List) result.mData;
                    if (list != null)
                    {
                        // 防止数据过多，大于1000条强制清空之前的数据
                        if (mDataList.size() > 1000)
                        {
                            GlobalApplication.dBHelper.clear(T_Sexy.class);
                            
                            Message msg = mHandler.obtainMessage(3);
                            msg.obj = list;
                            mHandler.sendMessage(msg);
                        }else{
                            Log.i("", "onLoadMore  -- "+pageNo);
                            Message msg = mHandler.obtainMessage(2);
                            msg.obj = list;
                            mHandler.sendMessage(msg);
                        }
                        
                        saveToDB(list);
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
            Toast.makeText(getActivity(), "请检查网络！", Toast.LENGTH_SHORT).show();
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
                	GlobalApplication.dBHelper.clear(T_Sexy.class);
                	
                    List list = (List) result.mData;
                    Message msg = mHandler.obtainMessage(1);
                    msg.obj = list;
                    saveToDB(list);
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mDataList.clear();
        mDataList = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.joke_listview, container, false);

            initViews(mView);
            
            /** 加载本地数据 **/
            loadLocalData();
        }
        else
        {
            View oldParent = (View) mView.getParent();
            if (oldParent != container)
            {
                ((ViewGroup) oldParent).removeView(mView);
            }
        }
        return mView;
    }

    private void initViews(View root){
    	mListview = (PullToRefreshListView) root.findViewById(R.id.listView);
        
        mAdapter = getAdapter(getActivity(), mDataList);
        mListview.setPullLoadEnable(true);
        mListview.setPullRefreshEnable(true);
        mListview.setPullAndRefreshListViewListener(SexyFragment.this);
        mListview.setAdapter(mAdapter);
        mListview.setOnScrollListener(SexyFragment.this);
        
        mListEmpty = root.findViewById(R.id.empty_view);
        mListEmpty.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                mProgress.setVisibility(View.VISIBLE);
                mListEmpty.setVisibility(View.GONE);
                onRefresh();
            }
        });

        mProgress = root.findViewById(R.id.progress_bar);
        
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        if (mAdapter != null)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    // 加载本地数据
    public void loadLocalData()
    {
        List tmpList = null;

        tmpList = GlobalApplication.dBHelper.query(T_Sexy.class, null, null, null);

        if (tmpList == null || tmpList.size() <= 0)
        {
            mProgress.setVisibility(View.VISIBLE);
            mListEmpty.setVisibility(View.GONE);
            onRefresh();
        }
        else
        {
            Message msg = mHandler.obtainMessage(0);
            msg.arg1 = 0;
            msg.obj = tmpList;
            mHandler.sendMessage(msg);
        }
    }
    
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
        {
            int index = view.getFirstVisiblePosition();
            View v = view.getChildAt(0);
            int top = (v == null) ? 0 : v.getTop();

            PreferencesUtil.setPreferences(getActivity(),PREFERENCE_PAGE, POSTION_INDEX_KEY, index);
            PreferencesUtil.setPreferences(getActivity(), PREFERENCE_PAGE, POSTION_TOP_KEY, top);
        }
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
	}
	
    public BaseAdapter getAdapter(Context context, List dataList)
    {
        return new SexyListAdapter(context, dataList);
    }

    public T_Result sendRequest(int pageNo)
    {
        return new SexyListRequest().getSexyList(Constants.OP_SEXY_LIST, pageNo);
    }

    public <T> void saveToDB(List<T> list)
    {
        try
        {
            if (list != null && list.size() > 0)
            {
                Iterator iter = list.iterator();
                while (iter.hasNext())
                {
                    Object t = iter.next();
                    GlobalApplication.dBHelper.insert(t);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
