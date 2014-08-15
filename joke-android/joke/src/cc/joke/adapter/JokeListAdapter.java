package cc.joke.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.activity.JokeDetailActivity;
import cc.joke.adapter.ImageGridViewAdapter.DownLoadImageListener;
import cc.joke.application.GlobalApplication;
import cc.joke.entity.T_JokeInfo;
import cc.joke.http.JokePraiseRequest;
import cc.joke.util.Util;
import cc.joke.view.ImageGridView;

/**
 * 笑话数据适配器
 * 
 * @author wanghao
 */
public class JokeListAdapter extends BaseAdapter
{

    private static int mPosition;

    private static List<T_JokeInfo> mList;

    private Context mContext;

    public JokeListAdapter(Context context, List<T_JokeInfo> list)
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

    public static T_JokeInfo getCurItem()
    {
        return mList.get(mPosition);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final T_JokeInfo obj = this.mList.get(position);
        final ViewHolder viewHolder;
        boolean isVibrate = true;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.joke_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemView = convertView.findViewById(R.id.list_item_view);
            viewHolder.mIconView = (ImageView) convertView.findViewById(R.id.list_item_icon);
            viewHolder.mTitleView = (TextView) convertView.findViewById(R.id.list_item_title);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.list_item_txt);
            viewHolder.mImageView = (ImageGridView) convertView.findViewById(R.id.list_item_image);
            viewHolder.mHighPraiseView = convertView.findViewById(R.id.pro_detail_good);
            viewHolder.mBadPraiseView = convertView.findViewById(R.id.pro_detail_bad);
            viewHolder.mPraiseView = convertView.findViewById(R.id.pro_praise);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String title = obj.getTitle();
        if (!TextUtils.isEmpty(title))
        {
            viewHolder.mTitleView.setText(title);

            setVisibility(viewHolder.mImageView, View.VISIBLE);
        }
        else
        {
            setVisibility(viewHolder.mTitleView, View.GONE);
        }

        String description = obj.getDescription();
        if (!TextUtils.isEmpty(description))
        {
            viewHolder.mTextView.setText(description);
            setVisibility(viewHolder.mTextView, View.VISIBLE);
            isVibrate = true;
        }
        else
        {
            setVisibility(viewHolder.mTextView, View.GONE);
            isVibrate = false;
        }

        String iconUrl = obj.getIconUrl();
        if (!TextUtils.isEmpty(iconUrl))
        {
            // 先给默认图片
            GlobalApplication.bitmapCache.getBitmap(R.drawable.user + "", viewHolder.mIconView, null);
            GlobalApplication.bitmapCache.getBitmap(iconUrl, viewHolder.mIconView, null);
            setVisibility(viewHolder.mIconView, View.VISIBLE);
        }
        else
        {
            setVisibility(viewHolder.mIconView, View.GONE);
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

            ImageGridViewAdapter gridViewAdapter= new ImageGridViewAdapter(mContext, images, ImageGridViewAdapter.TYPE_1);
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

        final TextView highPraiseView = (TextView) viewHolder.mHighPraiseView.findViewById(R.id.txt_highPraise);
        highPraiseView.setText("好评(" + obj.getHighPraise() + ")");

        final TextView badPraiseView = (TextView) viewHolder.mBadPraiseView.findViewById(R.id.txt_badPraise);
        badPraiseView.setText("差评(" + obj.getBadPraise() + ")");

        final TextView praiseView = (TextView) viewHolder.mPraiseView.findViewById(R.id.txt_praise);
        praiseView.setText("评论(" + obj.getTalknum() + ")");

        /** Item事件 **/
        viewHolder.mItemView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mPosition = position;
                Intent intent = new Intent(mContext, JokeDetailActivity.class);
                intent.putExtra("jokeinfo", obj);
                intent.putExtra("currentItem", 0);
                Util.enterActivity((Activity) mContext, intent);
            }
        });
        /** 好评事件 **/
        viewHolder.mHighPraiseView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CommitPraiseListener listener = new CommitPraiseListener()
                {

                    @Override
                    public void doCommitPraise()
                    {
                        obj.setHighPraise(obj.getHighPraise() + 1);
                        highPraiseView.setText(Util.buildString("好评(", String.valueOf(obj.getHighPraise()), ")"));
                    }
                };
                doCommitPraise(obj, 1, listener);
            }
        });
        /** 差评事件 **/
        viewHolder.mBadPraiseView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CommitPraiseListener listener = new CommitPraiseListener()
                {

                    @Override
                    public void doCommitPraise()
                    {
                        obj.setBadPraise(obj.getBadPraise() + 1);
                        badPraiseView.setText(Util.buildString("差评(", String.valueOf(obj.getBadPraise()), ")"));
                    }
                };
                doCommitPraise(obj, 2, listener);
            }
        });
        /** 评论事件 **/
        viewHolder.mPraiseView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mPosition = position;
                Intent intent = new Intent(mContext, JokeDetailActivity.class);
                intent.putExtra("jokeinfo", obj);
                intent.putExtra("currentItem", 1);
                Util.enterActivity((Activity) mContext, intent);
            }
        });
        if (isVibrate)
        {
            viewHolder.mItemView.setOnLongClickListener(new OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(viewHolder.mTextView.getText());
                    Util.vibrate(mContext);
                    Util.toast(mContext, mContext.getString(R.string.copy_succeed));
                    return true;
                }
            });
        }

        return convertView;
    }

    private void setVisibility(View view, int visible)
    {
        if (view.getVisibility() != visible)
        {
            view.setVisibility(visible);
        }
    }

    public void doCommitPraise(final T_JokeInfo obj, final int type, final CommitPraiseListener listener)
    {
        if (!Util.isNetworkConnected())
        {
            Util.toast(mContext, mContext.getString(R.string.noNetwork));
            return;
        }

        new AsyncTask<Void, Void, Void>()
        {
            int result;

            protected Void doInBackground(Void... params)
            {
                JokePraiseRequest request = new JokePraiseRequest();
                result = request.doCommit(obj.getId(), type);
                return null;
            }

            protected void onPostExecute(Void v)
            {

                if (result == -1)
                {
                    Util.toast(mContext, mContext.getString(R.string.pro_detail_praise_fail));
                }
                else if (result == 1)
                {
                    Util.toast(mContext, mContext.getString(R.string.pro_detail_praise_exists));
                }
                else
                {
                    if (type == 1)
                    {
                        Util.toast(mContext, mContext.getString(R.string.pro_detail_praise_high));
                    }
                    else
                    {
                        Util.toast(mContext, mContext.getString(R.string.pro_detail_praise_bad));
                    }
                    listener.doCommitPraise();
                }
            }
        }.execute();
    }

    public interface CommitPraiseListener
    {
        public void doCommitPraise();
    }

    class ViewHolder
    {
        /** Item视图 **/
        public View mItemView;

        /** ICON视图 **/
        public ImageView mIconView;

        /** 标题视图 **/
        public TextView mTitleView;

        /** 文字视图 **/
        public TextView mTextView;

        /** 图片视图 **/
        public ImageGridView mImageView;

        /** 点击视图 **/
        public TextView mClickCountView;

        /** 好评 **/
        public View mHighPraiseView;

        /** 差评 **/
        public View mBadPraiseView;

        /** 评论 **/
        public View mPraiseView;
    }
}
