package cc.joke.view.refreshlist;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.util.PreferencesUtil;

/**
 * 下拉刷新ExpandableListView
 * 
 * @author vivili
 * @version [版本号, 2013-8-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PullToRefreshExpandableListView extends ExpandableListView implements OnScrollListener
{
    private float mLastY = -1; // save event y

    private Scroller mScroller; // used for scroll back

    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private PullAndRefreshListViewListener pullAndRefreshListViewListener;

    // -- header view
    private PullToRefreshListViewHeader mHeaderView;

    // header view content, use it to calculate the Header's height. And hide
    // it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;

    private TextView mHeaderTimeView;

    private int mHeaderViewHeight; // header view's height

    private boolean mEnablePullRefresh = true;

    private boolean mPullRefreshing = false; // is refreashing.

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

    // -- footer view
    private PullToRefreshListViewFooter mFooterView;

    private boolean mEnablePullLoad;

    private boolean mPullLoading;

    private boolean mIsFooterReady = false;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;

    private final static int SCROLLBACK_HEADER = 0;

    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400; // scroll back duration

    // private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >=
    // 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
                                                    // feature.

    private long refreshTime = 0;

    private Context context = null;

    private String timeTag = "";

    private int totalItemCount = 1000;

    private static final int LAST_REFRESH_TIME = 800;

    public String getTimeTag()
    {
        return timeTag;
    }

    public void setTimeTag(String timeTag)
    {
        this.timeTag = timeTag;
        String last = getLastRefreshTime();
        mHeaderTimeView.setText(last);
    }

    public PullToRefreshExpandableListView(Context context)
    {
        super(context);
        initWithContext(context);
        init();
    }

    public PullToRefreshExpandableListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initWithContext(context);
        init();
    }

    public PullToRefreshExpandableListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initWithContext(context);
        init();
    }

    private void init()
    {
        // 组图标去掉
        setGroupIndicator(null);

        setCacheColorHint(Color.TRANSPARENT);

        // 去除分隔线
        // setGroupIndicator(null);
        // setChildDivider(null);
        // setDivider(null);
    }

    // @Override
    // public boolean performItemClick(View v, int position, long id)
    // {
    //
    // return true;// 不可点击
    //
    // }

    private void initWithContext(Context context)
    {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);
        this.context = context;
        // init header view
        mHeaderView = new PullToRefreshListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new PullToRefreshListViewFooter(context);
        mFooterView.hide();

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                mHeaderViewHeight = mHeaderViewContent.getHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter)
    {
        // make sure XListViewFooter is the last footer view, and only add
        // once.
        if (mIsFooterReady == false)
        {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     * 
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable)
    {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh)
        { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        }
        else
        {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     * 
     * @param enable
     */
    public void setPullLoadEnable(boolean enable)
    {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad)
        {
            mFooterView.hide();
        }
        else
        {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(PullToRefreshListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh()
    {
        if (mPullRefreshing == true)
        {
            mPullRefreshing = false;
            long times = System.currentTimeMillis() - refreshTime;
            if (times >= LAST_REFRESH_TIME)
            {
                resetHeaderHeight();
                changeRefreshTime();
            }
            else
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        resetHeaderHeight();
                        changeRefreshTime();
                    }
                }, LAST_REFRESH_TIME - times);
            }
        }
    }

    private void changeRefreshTime()
    {
        // 2 重置刷新时间
        setRefreshTime();
        String last = getLastRefreshTime();
        mHeaderTimeView.setText(last);
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore()
    {
        if (mPullLoading == true)
        {
            mPullLoading = false;
            mFooterView.hide();
            mFooterView.setState(PullToRefreshListViewFooter.STATE_NORMAL);
        }
    }

    private void invokeOnScrolling()
    {
        if (mScrollListener instanceof OnXScrollListener)
        {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta)
    {
        mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing)
        { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight)
            {
                mHeaderView.setState(PullToRefreshListViewHeader.STATE_READY);
            }
            else
            {
                mHeaderView.setState(PullToRefreshListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight()
    {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight)
        {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight)
        {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        // LogUtils.error("pullToRefresh::finalHeight:" + finalHeight +
        // "-getVisiableHeight:" + height);
        // trigger computeScroll
        invalidate();
    }

    public void showHeaderAndRefresh()
    {
        // 1 改变当前显示的时间，以便下一次显示
        String last = getLastRefreshTime();
        mHeaderTimeView.setText(last);
        mPullRefreshing = true;
        mHeaderView.setState(PullToRefreshListViewHeader.STATE_REFRESHING);
        if (pullAndRefreshListViewListener != null)
        {
            refreshTime = System.currentTimeMillis();
            pullAndRefreshListViewListener.onRefresh();
        }
        mScroller.startScroll(0, 0, 0, mHeaderViewHeight, 1);
        invalidate();
    }

    public void refresh()
    {
        if (pullAndRefreshListViewListener != null)
        {
            refreshTime = System.currentTimeMillis();
            pullAndRefreshListViewListener.onRefresh();
        }
    }

    private void startLoadMore()
    {
        mPullLoading = true;
        mFooterView.setState(PullToRefreshListViewFooter.STATE_LOADING);
        if (pullAndRefreshListViewListener != null)
        {
            mFooterView.show();
            pullAndRefreshListViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {

        if (mLastY == -1)
        {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                // LogUtils.error("pullToRefresh::ActionMove -- getVisiableHeight:"
                // + mHeaderView.getVisiableHeight());
                if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0))
                {
                    // the first item is showing, header has shown or pull
                    // down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                }
                else if (getLastVisiblePosition() == mTotalItemCount - 2)
                {
                    // last item, already pulled up or want to pull up.
                    // updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0)
                {
                    // invoke refresh
                    if (!mPullRefreshing && mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight)
                    {
                        mPullRefreshing = true;
                        mHeaderView.setState(PullToRefreshListViewHeader.STATE_REFRESHING);
                        if (pullAndRefreshListViewListener != null)
                        {
                            refreshTime = System.currentTimeMillis();
                            pullAndRefreshListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                }
                else if (getLastVisiblePosition() == mTotalItemCount - 2)
                {
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            if (mScrollBack == SCROLLBACK_HEADER)
            {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            }
            else
            {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l)
    {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if (mScrollListener != null)
        {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
        int lastVisiPos = this.getLastVisiblePosition();
        if (lastVisiPos == this.totalItemCount - 1 && !mPullLoading && !mPullRefreshing && mEnablePullLoad)
        {
            startLoadMore();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null)
        {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        this.totalItemCount = totalItemCount;
    }

    public void setPullAndRefreshListViewListener(PullAndRefreshListViewListener l)
    {
        pullAndRefreshListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener
    {
        public void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface PullAndRefreshListViewListener
    {
        public void onRefresh();

        public void onLoadMore();
    }

    private void setRefreshTime()
    {
        long currentTime = System.currentTimeMillis();
        PreferencesUtil.setPreferences(this.context, "PullToRefresh", timeTag, currentTime);

    }

    private String getLastRefreshTime()
    {
        long lastRefreshTime = PreferencesUtil.getPreference(this.context, "PullToRefresh", timeTag,
                System.currentTimeMillis());
        String time = dateFormat.format(lastRefreshTime);
        return time;
    }

    public void hideFooterView()
    {
        if (null != mFooterView)
        {
            mFooterView.hide();
        }
    }

}
