package cc.joke.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.entity.T_App;
import cc.joke.util.JokeDownloadManager;

public class AppListAdapter extends BaseAdapter
{

    public List apps;

    public Context context;

    public AppListAdapter(Context context, List apps)
    {
        this.apps = apps;
        this.context = context;
    }

    public void setData(List apps)
    {
        this.apps = apps;
    }

    public int getCount()
    {
        if (apps != null)
        {
            return apps.size();
        }
        return 0;
    }

    public Object getItem(int position)
    {
        return apps.get(position);
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
            view = inflater.inflate(R.layout.app_list_item, null);
            holder.appIcon = (ImageView) view.findViewById(R.id.app_list_item_icon);
            holder.appName = (TextView) view.findViewById(R.id.app_list_item_title);
            holder.appDescription = (TextView) view.findViewById(R.id.app_list_item_description);
            holder.appDownload = (ImageView) view.findViewById(R.id.app_down);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        // 设值
        final T_App app = (T_App) apps.get(position);
        if (app != null)
        {
            holder.appName.setText(app.getName());
            holder.appDescription.setText(app.getDescrip());
            String iconUrl = app.getIconUrl();
            // 先给默认图片
            GlobalApplication.bitmapCache.getBitmap(R.drawable.loading_background + "", holder.appIcon, null);
            GlobalApplication.bitmapCache.getBitmap(iconUrl, holder.appIcon, null);

            view.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    JokeDownloadManager.getInstance().startApk(app.getBaseUrl(),app.getName());
                }
            });
        }

        return view;
    }

    class ViewHolder
    {
        ImageView appIcon;

        TextView appName;

        TextView appDescription;

        ImageView appDownload;
    }
}
