package cc.joke.adapter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView.ScaleType;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.ImageDownLoadCallback;
import cc.joke.cache.ImageOption;
import cc.joke.view.LoaderImageView;
import cc.joke.view.ShowGallery;

public class ShowImagesAdapter extends BaseAdapter
{

    private ShowGallery showImage;

    private String[] imagesUrl;

    private boolean[] isDownLoad;// 是否下载过

    private LoaderImageView[] iv;

    // gallery是否已经自动调整过
    private boolean isAuto = false;

    private Matrix mMatrix = new Matrix();

    private int mRotate = 0;

    public ShowImagesAdapter(ShowGallery showImag, String[] imagesUrl)
    {
        this.showImage = showImag;
        this.imagesUrl = imagesUrl;
        isDownLoad = new boolean[imagesUrl.length];
        iv = new LoaderImageView[imagesUrl.length];
        for (int i = 0; i < iv.length; i++)
        {
            LoaderImageView obj = new LoaderImageView(GlobalApplication.context);
            obj.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT,
                    Gallery.LayoutParams.FILL_PARENT));
            obj.setScaleType(ScaleType.FIT_XY);
            iv[i] = obj;
        }
    }

    public void setRotate(int rotate)
    {
        this.mRotate = rotate;
    }

    public int getCount()
    {
        if (imagesUrl == null)
        {
            return 0;
        }
        else if (imagesUrl.length < 2)
        {
            return 1;
        }
        else
        {
            return imagesUrl.length;
        }
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int index = position % imagesUrl.length;
        if (!isDownLoad[index])
        {
            isDownLoad[index] = true;
            GlobalApplication.bitmapCache.getBitmap(imagesUrl[index], null, new ImageDownLoadCallback()
            {
                public void imageDownLoaded(Bitmap bm)
                {
                    if (bm == null)
                    {
                        return;
                    }
                    if (!isAuto)
                    {
                        showImage.getLayoutParams().height = GlobalApplication.widthPixels * bm.getHeight()
                                / bm.getWidth();
                        isAuto = true;
                    }
                    if (mRotate > 0)
                    {
                        if (bm.getWidth() < bm.getHeight())
                        {
                            iv[index].setImageBitmap(bm);
                        }
                        else
                        {
                            mMatrix.setRotate(mRotate);
                            Bitmap bmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mMatrix, true);
                            iv[index].setImageBitmap(bmp);
                        }
                    }
                    else
                    {
                        iv[index].setImageBitmap(bm);
                    }
                }
            }, new ImageOption(GlobalApplication.widthPixels, true));
        }
        return iv[position];
    }

}
