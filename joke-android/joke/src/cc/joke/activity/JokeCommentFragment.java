package cc.joke.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.adapter.CommentListAdapter;
import cc.joke.adapter.JokeListAdapter;
import cc.joke.application.GlobalApplication;
import cc.joke.entity.T_Comment;
import cc.joke.entity.T_Result;
import cc.joke.http.JokeCommentRequest;
import cc.joke.util.DateUtil;
import cc.joke.util.ThreadPool;
import cc.joke.util.Util;
import cc.joke.view.refreshlist.PullToRefreshListView;
import cc.joke.view.refreshlist.PullToRefreshListView.PullAndRefreshListViewListener;

@SuppressLint("ValidFragment")
public class JokeCommentFragment extends Fragment implements PullAndRefreshListViewListener
{
    // 笑话ID
    private Integer jokeId;

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
                {//error
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
                case 4:
                {
                    T_Comment comment = (T_Comment) msg.obj;
                    mDataList.add(0, comment);
                    mListview.scrollTo(0, 0);
                    
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

    // 发送评论事件
    public void comment(final int userId, final String userName, final String userIcon, final String content)
    {

        final String model = Build.MODEL;
        if (jokeId != null)
        {
            final T_Comment comment = new T_Comment();
            comment.setContent(content);
            comment.setCreateDate(DateUtil.getNowDateTimeStr());
            comment.setModel(model);
            comment.setPid(jokeId);
            comment.setUserid(userId);
            comment.setUsername(userName);
            comment.setUsericon(userIcon);

            Message msg = mHandler.obtainMessage(4);
            msg.obj = comment;
            mHandler.sendMessage(msg);

            // 异步提交
            ThreadPool.add(new Runnable()
            {
                @Override
                public void run()
                {
                    commentRequest(comment);
                }
            });

            // 用于同步本地笑话列表中的评论数（否则只能每次通过刷新来更新）
            int talkNum = JokeListAdapter.getCurItem().getTalknum();
            JokeListAdapter.getCurItem().setTalknum(++talkNum);
        }
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

    public JokeCommentFragment(Integer jokeid)
    {
        this.jokeId = jokeid;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mDataList.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mView == null)
        {
            mView = inflater.inflate(R.layout.joke_comment, container, false);

            initViews(mView);

            onRefresh();

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

    private void initViews(View rootView)
    {
        mListview = (PullToRefreshListView) rootView.findViewById(R.id.listView);

        mListview.setPullLoadEnable(true);
        mListview.setPullRefreshEnable(true);
        mListview.setPullAndRefreshListViewListener(JokeCommentFragment.this);

        mAdapter = getAdapter(getActivity(), mDataList);

        mListview.setAdapter(mAdapter);
        
        mListEmpty = rootView.findViewById(R.id.empty_view);
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

        final EditText commentContent = (EditText) rootView.findViewById(R.id.comment_edit);

        Button sendButton = (Button) rootView.findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String content = commentContent.getText().toString();
                if (TextUtils.isEmpty(content))
                {
                    return;
                }
                if (content.length() > 50)
                {
                    Util.toast(JokeCommentFragment.this.getActivity(),
                            Util.getResourcesStr(R.string.commit_comment_large));
                    return;
                }
                if (GlobalApplication.getLoginInfo() != null)
                {
                    int userId = GlobalApplication.getLoginInfo().getUserid();
                    String userName = GlobalApplication.getLoginInfo().getUsername();
                    String userIcon = GlobalApplication.getLoginInfo().getUsericon();
                    JokeCommentFragment.this.comment(userId, userName, userIcon, content);
                }
                else
                {
                    Util.toast(JokeCommentFragment.this.getActivity(), "请先去登陆！");
                }

                InputMethodManager imm = (InputMethodManager) JokeCommentFragment.this.getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(commentContent.getWindowToken(), 0);

                commentContent.setText("");
            }
        });
        mProgress = rootView.findViewById(R.id.progress_bar);
        
        mProgress.setVisibility(View.VISIBLE);
    }

    public BaseAdapter getAdapter(Context context, List dataList)
    {
        return new CommentListAdapter(context, dataList);
    }

    public T_Result sendRequest(int pageNo)
    {
        return new JokeCommentRequest().getComments(jokeId, pageNo);
    }

    public void commentRequest(T_Comment comment)
    {
        new JokeCommentRequest().commitComment(comment);
    }

}
