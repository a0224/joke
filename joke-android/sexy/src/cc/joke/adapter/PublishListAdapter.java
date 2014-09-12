package cc.joke.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.entity.T_Publish;
import cc.joke.util.Util;

public class PublishListAdapter extends BaseAdapter
{

    private List publishs;

    public Context context;

    public PublishListAdapter(Context context, List publishs)
    {
        this.publishs = publishs;
        this.context = context;
    }

    public void setData(List publishs)
    {
        this.publishs = publishs;
    }

    public int getCount()
    {
        if (publishs != null)
        {
            return publishs.size();
        }
        return 0;
    }

    public Object getItem(int position)
    {
        return publishs.get(position);
    }

    public long getItemId(int position)
    {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.publish_list_item, null);
            holder.user_icon = (ImageView) view.findViewById(R.id.user_icon);
            holder.username = (TextView) view.findViewById(R.id.username);
            holder.content = (TextView) view.findViewById(R.id.content);
            holder.review_status = (TextView) view.findViewById(R.id.review_status);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        // 设值
        T_Publish publish = (T_Publish) publishs.get(position);
        if (publish != null)
        {
            GlobalApplication.bitmapCache.getBitmap(publish.getUsericon(), holder.user_icon, null);
            holder.username.setText(publish.getUsername());
            holder.content.setText(publish.getContent());
            holder.review_status.setText(getReviewStatus(publish));
            String iconUrl = publish.getUsericon();
            if (iconUrl != null && !("".equals(iconUrl)))
            {
                // 先给默认图片
                GlobalApplication.bitmapCache.getBitmap(R.drawable.loading_background + "", holder.user_icon, null);
                GlobalApplication.bitmapCache.getBitmap(iconUrl, holder.user_icon, null);
                holder.user_icon.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.user_icon.setVisibility(View.GONE);
            }
        }
        return view;
    }

    private String getReviewStatus(T_Publish t)
    {
        String status = "无法通过审核被后台删除了";
        switch (t.getStatus())
        {
            case 1:
                status = "通过";
                break;
            case 3:
                status = "未通过";
                break;
            case 2:
                status = "审核中";
                break;
        }
        return status;
    }

    class ViewHolder
    {
        ImageView user_icon;

        TextView username;

        TextView content;

        TextView review_status;
    }

}
