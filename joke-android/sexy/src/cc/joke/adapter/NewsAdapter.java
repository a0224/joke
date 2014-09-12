package cc.joke.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cc.joke.R;
import cc.joke.activity.NewsDetailActivity;
import cc.joke.application.GlobalApplication;
import cc.joke.entity.T_News;
import cc.joke.util.Util;
import cc.joke.util.ViewUtil;

/**
 * 新闻消息数据适配器
 */
public class NewsAdapter extends BaseAdapter
{

    private List<T_News> mList;

    private Context mContext;

    private PopupWindow mPopupWindow;

    private boolean isTop = false;

    public NewsAdapter(Context context, List<T_News> news)
    {
        this.mContext = context;
        this.mList = news;
    }

    public int getCount()
    {
        return mList.size();
    }

    public Object getItem(int position)
    {
        return mList.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    private T_News mSelectedItem;

    private View.OnClickListener mPopupMenuListener = new View.OnClickListener()
    {
        public void onClick(View view)
        {
            int viewID = view.getId();
            if (viewID == R.id.app_list_item_popup_start)
            {
                Intent i = new Intent(mContext, NewsDetailActivity.class);
                i.putExtra("NEWS_ID", mSelectedItem.get_id());
                mContext.startActivity(i);
            }
            else if (viewID == R.id.app_list_item_popup_detail)
            {
                GlobalApplication.dBHelper.delete(T_News.class, "_id = ?",
                        new String[] {String.valueOf(mSelectedItem.get_id())});
                mList.remove(mSelectedItem);
                notifyDataSetChanged();
                Toast.makeText(mContext, "删除消息成功", Toast.LENGTH_SHORT).show();
            }
            mPopupWindow.dismiss();
        };
    };

    private void showPopupWindow(T_News item, View targetView, int position)
    {
        mSelectedItem = item;
        View view = null;
        if (mPopupWindow == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.app_list_item_popup_window, null);
            mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        }
        else
        {
            view = mPopupWindow.getContentView();
        }
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.anim.slide_down);
        TextView txt1 = (TextView) view.findViewById(R.id.app_list_item_popup_start);
        TextView txt2 = (TextView) view.findViewById(R.id.app_list_item_popup_detail);
        view.findViewById(R.id.app_list_item_popup_uninstall).setVisibility(View.GONE);
        view.findViewById(R.id.app_list_item_popup_move).setVisibility(View.GONE);
        view.findViewById(R.id.app_list_item_popup_upgrade).setVisibility(View.GONE);
        txt1.setText("查看");
        txt2.setText("删除");
        txt1.setOnClickListener(mPopupMenuListener);
        txt2.setOnClickListener(mPopupMenuListener);
        int x = 0, y = 0;
        int location[] = new int[2];
        targetView.getLocationOnScreen(location);
        int endY = Util.getStatusBarHeight(mContext) + Util.dip2px(50) + Util.dip2px(20) + (Util.dip2px(80) / 2);
        if (location[1] < endY)
        {
            isTop = true;
        }
        else
        {
            isTop = false;
        }
        if (isTop)
        {
            int paddingTop = ViewUtil.dip2px(mContext, 14);
            txt1.setPadding(0, paddingTop, 0, 0);
            txt2.setPadding(0, paddingTop, 0, 0);
            x = 0;
            y = 5;
        }
        else
        {
            int paddingTop = ViewUtil.dip2px(mContext, 7);
            txt1.setPadding(0, paddingTop, 0, 0);
            txt2.setPadding(0, paddingTop, 0, 0);
            int marginTop = ViewUtil.dip2px(mContext, 88);
            x = 0;
            y = -marginTop;
        }
        mPopupWindow.showAsDropDown(targetView, x, y);
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        final T_News news = this.mList.get(position);
        if (convertView == null)
        {
            LayoutInflater layoutService = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutService.inflate(R.layout.news_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mIconView = (ImageView) convertView.findViewById(R.id.news_list_item_icon);
            viewHolder.mTitleView = (TextView) convertView.findViewById(R.id.news_list_item_title);
            viewHolder.mContentView = (TextView) convertView.findViewById(R.id.news_list_item_message);
            viewHolder.mDateView = (TextView) convertView.findViewById(R.id.news_list_item_time);
            viewHolder.mItemView = (RelativeLayout) convertView.findViewById(R.id.news_item);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /** 设置ICON **/
        if (news.getIconUrl() != null && !"".equals(news.getIconUrl()))
        {
            GlobalApplication.bitmapCache.getBitmap(news.getIconUrl(), viewHolder.mIconView, null);
        }
        else
        {
            viewHolder.mIconView.setImageResource(R.drawable.icon);
        }
        viewHolder.mIconView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showPopupWindow(news, v, position);
            }
        });
        viewHolder.mTitleView.setText(news.getTitle());
        viewHolder.mContentView.setText(news.getContent());
        viewHolder.mDateView.setText(news.getCreateTime().split(" ")[0]);
        viewHolder.mItemView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (news.getExistsDetail() == 1)
                {
                    Intent i = new Intent(mContext, NewsDetailActivity.class);
                    i.putExtra("NEWS_ID", news.get_id());
                    mContext.startActivity(i);
                }
            }
        });
        viewHolder.mItemView.setOnLongClickListener(new RelativeLayout.OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                showPopupWindow(news, viewHolder.mIconView, position);
                return true;
            }
        });
        return convertView;
    }

    class ViewHolder
    {
        public ImageView mIconView;

        public TextView mTitleView;

        public TextView mContentView;

        public TextView mDateView;

        public RelativeLayout mItemView;
    }

}
