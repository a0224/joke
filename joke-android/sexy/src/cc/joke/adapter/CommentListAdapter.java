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
import cc.joke.entity.T_Comment;
import cc.joke.util.Util;

public class CommentListAdapter extends BaseAdapter
{

    public List comments;

    public Context context;

    public CommentListAdapter(Context context, List comments)
    {
        this.comments = comments;
        this.context = context;
    }

    public void setData(List comments)
    {
        this.comments = comments;
    }

    public int getCount()
    {
        if (comments != null)
        {
            return comments.size();
        }
        return 0;
    }

    public Object getItem(int position)
    {
        return comments.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.comment_list_item, null);
            holder.user_icon = (ImageView) view.findViewById(R.id.user_icon);
            holder.username = (TextView) view.findViewById(R.id.username);
            holder.os_model = (TextView) view.findViewById(R.id.os_model);
            holder.content = (TextView) view.findViewById(R.id.content);
            holder.create_date = (TextView) view.findViewById(R.id.create_date);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        T_Comment comment = (T_Comment) comments.get(position);

        GlobalApplication.bitmapCache.getBitmap(comment.getUsericon(), holder.user_icon, null);
        holder.username.setText(comment.getUsername());
        holder.os_model.setText(Util.buildString("使用 ", comment.getModel()));
        holder.content.setText(comment.getContent());
        holder.create_date.setText(comment.getCreateDate());
        return view;
    }

    class ViewHolder
    {
        ImageView user_icon;

        TextView username;

        TextView os_model;

        TextView content;

        TextView create_date;
    }

}
