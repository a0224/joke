package cc.joke.adapter;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.activity.HeadlinesDetailActivity;
import cc.joke.activity.SexyDetailActivity;
import cc.joke.adapter.ImageGridViewAdapter.DownLoadImageListener;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.ImageDownLoadCallback;
import cc.joke.entity.T_Sexy;
import cc.joke.util.SouEnum;
import cc.joke.util.Util;
import cc.joke.view.ImageGridView;
import cc.joke.wiipay.Wiipay;
import cc.joke.wiipay.Wiipay.PayListener;
import cc.joke.util.StringUtils;

/**
 * 两性数据适配器
 * 
 * @author wanghao
 */
public class SexyListAdapter extends BaseAdapter
{

    private List<T_Sexy> mList;

    private Context mContext;

    public SexyListAdapter(Context context, List<T_Sexy> list)
    {
        this.mList = list;
        this.mContext = context;
    }

    public int getCount()
    {
        return this.mList.size();
    }

    public Object getItem(int position)
    {
        return this.mList.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        final T_Sexy obj = this.mList.get(position);
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sexy_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemView = convertView.findViewById(R.id.list_item_view);
            viewHolder.mIconView = (ImageView) convertView.findViewById(R.id.list_item_icon);
            viewHolder.mTitleView = (TextView) convertView.findViewById(R.id.list_item_title);
            viewHolder.mContentView = (TextView) convertView.findViewById(R.id.list_item_content);
            viewHolder.mImageView = (ImageGridView) convertView.findViewById(R.id.list_item_image);
            viewHolder.mSource  = (TextView) convertView.findViewById(R.id.list_item_source);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (TextUtils.isEmpty(obj.getTitle()))
        {
            setVisibility(viewHolder.mTitleView, View.GONE);
        }
        else
        {
            setVisibility(viewHolder.mTitleView, View.VISIBLE);
            viewHolder.mTitleView.setText(obj.getTitle());
        }

        if (TextUtils.isEmpty(obj.getDescription()))
        {
            setVisibility(viewHolder.mContentView, View.GONE);
        }
        else
        {
            setVisibility(viewHolder.mContentView, View.VISIBLE);
            viewHolder.mContentView.setText("    "+StringUtils.omitString(obj.getDescription(),130));
        }
        
        String source = SouEnum.getSourceName(obj.getSource());
        if (TextUtils.isEmpty(source))
        {
            setVisibility(viewHolder.mSource, View.GONE);
        }
        else
        {
            viewHolder.mSource.setText(source);
            setVisibility(viewHolder.mSource, View.VISIBLE);
        }
        
        String imgUrl = obj.getImgUrl();
        if (TextUtils.isEmpty(imgUrl))
        {
            setVisibility(viewHolder.mImageView, View.GONE);
        }
        else
        {
            String[] images = imgUrl.split(",");
            setVisibility(viewHolder.mImageView, View.VISIBLE);
            
            ImageGridViewAdapter gridViewAdapter= new ImageGridViewAdapter(mContext, images, ImageGridViewAdapter.TYPE_3);
            gridViewAdapter.setDownLoadImageListener(new DownLoadImageListener()
            {
                @Override
                public void onDownLoadError()
                {
                    setVisibility(viewHolder.mImageView, View.GONE);
                }
            });
            viewHolder.mImageView.setAdapter(gridViewAdapter);  
        }

        /** Item事件 **/
        viewHolder.mItemView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                boolean ischarge = obj.getIscharge() == 1;
                if (ischarge)
                {
                    if (Wiipay.getInstance().isPayed(1))
                    {
                        todo();
                    }
                    else
                    {
                        Wiipay.getInstance().pay(Wiipay.payCode_2, 1, new PayListener()
                        {

                            @Override
                            public void onPaySucceed()
                            {
                                todo();
                            }

                            public void onPayFailed()
                            {
                            };
                        });
                    }
                }
                else
                {
                    todo();
                }
            }

            public void todo()
            {
                Intent intent = new Intent(mContext, SexyDetailActivity.class);
                intent.putExtra("htmlUrl", obj.getHtmlUrl());
                Util.enterActivity((Activity) mContext, intent);
            }
        });
        return convertView;
    }

    private void setVisibility(View view, int visible)
    {
        if (view.getVisibility() != visible)
        {
            view.setVisibility(visible);
        }
    }

    class ViewHolder
    {
        /** ICON视图 **/
        public ImageView mIconView;

        /** 标题视图 **/
        public TextView mTitleView;

        /** 内容视图 **/
        public TextView mContentView;
        
        /** 来源 **/
        public TextView mSource;

        /** 点击视图 **/
        public TextView mClickCountView;

        /** 图片视图 **/
        public ImageGridView mImageView;

        /** Item视图 **/
        public View mItemView;
    }

}
