package cc.joke.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.ImageDownLoadCallback;
import cc.joke.cache.ImageOption;

public class ImageGridViewAdapter extends BaseAdapter
{

	/**
	 * 每行显示3个
	 */
	public static final int TYPE_3 = 3;
	/**
	 * 每行显示一个
	 */
	public static final int TYPE_1 = 1;
	
    private String[] images = null;

    private Context mContext;

    private int mType = TYPE_3;
    
    public ImageGridViewAdapter(Context context, String[] images ,int type)
    {
        mContext = context;
        this.images = images;
        this.mType = type;
    }

    @Override
    public int getCount()
    {
        return images != null ? images.length : null;
    }

    @Override
    public Object getItem(int position)
    {
        return images != null ? images[position] : null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
 {

		if (images == null) {
			return convertView;
		}

		final ViewHolder viewHolder;
		if (convertView == null) {
			if (mType == TYPE_1) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.image_gridview_item_1, null);
			} else {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.image_gridview_item_3, null);
			}
			viewHolder = new ViewHolder();

			viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.image);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String imgUrl = images[position];

		if (!TextUtils.isEmpty(imgUrl)) {
			setBitmap(viewHolder.mImageView, imgUrl, mType == TYPE_1);
		}

		return convertView;
	}
    
	private void setBitmap(final ImageView imageView, String imgUrl, boolean autoTensile) {
		
		if (autoTensile) {
			//自动拉伸
			GlobalApplication.bitmapCache.getBitmap(imgUrl, imageView, new ImageDownLoadCallback() {
				public void imageDownLoaded(Bitmap bm) {

					if (bm == null) {
						if (mDownLoadImageListener != null) {
							mDownLoadImageListener.onDownLoadError();
						}
						return;
					}
					imageView.getLayoutParams().height = GlobalApplication.widthPixels * bm.getHeight() / bm.getWidth();
				
					ImageGridViewAdapter.this.notifyDataSetChanged();
				}
			}, new ImageOption(GlobalApplication.widthPixels, true));
		} else {
			//不自动拉伸
			GlobalApplication.bitmapCache.getBitmap(imgUrl, imageView, new ImageDownLoadCallback() {
				@Override
				public void imageDownLoaded(Bitmap bm) {
					if (bm == null && mDownLoadImageListener != null) {
						mDownLoadImageListener.onDownLoadError();
					}
				}
			});
		}
	}

    class ViewHolder
    {
        public ImageView mImageView;
    }

    DownLoadImageListener mDownLoadImageListener;

    public void setDownLoadImageListener(DownLoadImageListener listener)
    {
        mDownLoadImageListener = listener;
    }

    public interface DownLoadImageListener
    {
        public void onDownLoadError();
    }
}
