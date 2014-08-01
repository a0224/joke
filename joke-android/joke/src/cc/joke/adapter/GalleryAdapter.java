package cc.joke.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cc.joke.R;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.ImageDownLoadCallback;
import cc.joke.cache.ImageOption;
import cc.joke.entity.ImageInfo;
import cc.joke.entity.T_Gallery;
import cc.joke.util.Util;

/**
 * 图片填充适配器
 */
public class GalleryAdapter extends BaseAdapter
{

    private Context mContext;

    private ImageView[] mImages;

    // 单张图片高宽
    private int w_ = 0;

    private int h_ = 0;

    // 正常网络请求
    public GalleryAdapter(Context context, ImageInfo[] mGallerys)
    {
        this.mContext = context;
        w_ = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.45);
        h_ = (int) (Util.dip2px(250) * 0.7);

        if (mGallerys != null && mGallerys.length > 0)
        {
            mImages = new ImageView[mGallerys.length];
            for (int i = 0; i < mImages.length; i++)
            {
                ImageView iv = new ImageView(context);
                mImages[i] = iv;

                iv.setTag(mGallerys[i].getId());
                // imageview参数设置
                setImageViewOption(iv);
                // 先给默认图片
                GlobalApplication.bitmapCache.getBitmap(R.drawable.loading_background + "", iv, null);
            }
        }

        // 查看上一次下载的记录
        List<T_Gallery> list = GlobalApplication.dBHelper.query(T_Gallery.class, null, null, "_id asc");
        if (mGallerys != null)
        {
            // 然后比对，看哪些需要重新下载
            int r = list.size() - mGallerys.length;
            if (r > 0)
            {
                // 删除多余的记录
                GlobalApplication.dBHelper.delete(T_Gallery.class, "_id>?", new String[] {(mGallerys.length - 1) + ""});
            }
            for (int i = 0; i < mGallerys.length; i++)
            {
                ImageInfo image = mGallerys[i];
                if (list.size() < i + 1)
                {// 第 (i+1) 条在数据库中不存在
                    T_Gallery tg = new T_Gallery();
                    tg.set_id(i);
                    tg.setSid(image.getId());
                    tg.setImageurl(image.getUrl());
                    // 向数据库插入当前索引的一条记录
                    GlobalApplication.dBHelper.insert(tg);
                }
                else
                {// 第 (i+1)在数据库中有
                    T_Gallery tg = list.get(i);
                    // 当前图片在服务器中信息和本地数据库是否一致
                    if (image.getId() != null && image.getId().equals(tg.getSid()) && image.getUrl() != null
                            && (image.getUrl()).equals(tg.getImageurl()))
                    {

                    }
                    else
                    { // 不一致
                        tg.setSid(image.getId());
                        tg.setImageurl(image.getUrl());
                        // 更新当前记录
                        GlobalApplication.dBHelper.update(tg, "_id=?", new String[] {i + ""});
                    }
                }

                // 加载真实图片
                ImageView iv = mImages[i];
                GlobalApplication.bitmapCache.getBitmap(image.getUrl(), iv, new MyImageDownLoadCallback(iv),
                        new ImageOption(w_, h_));
            }
        }

    }

    // 本地数据请求
    public GalleryAdapter(Context context, List<T_Gallery> gallery_list)
    {
        this.mContext = context;
        w_ = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.45);
        h_ = (int) (Util.dip2px(250) * 0.7);
        if (gallery_list != null && gallery_list.size() > 0)
        {
            mImages = new ImageView[gallery_list.size()];

            for (int i = 0; i < gallery_list.size(); i++)
            {
                T_Gallery tg = gallery_list.get(i);
                ImageView iv = new ImageView(context);

                iv.setTag(tg.getSid());
                mImages[i] = iv;

                // imageview参数设置
                setImageViewOption(iv);
                // 先给默认图片
                GlobalApplication.bitmapCache.getBitmap(R.drawable.loading_background + "", iv, null);
                // 加载真实图片
                GlobalApplication.bitmapCache.getBitmap(tg.getImageurl(), null, new MyImageDownLoadCallback(iv));
            }
        }
    }

    class MyImageDownLoadCallback implements ImageDownLoadCallback
    {
        private ImageView iv;

        public MyImageDownLoadCallback(ImageView iv)
        {
            this.iv = iv;
        }

        public void imageDownLoaded(Bitmap bm)
        {
            if (iv != null && bm != null)
            {
                createReflectedImages(iv, bm);
                notifyDataSetChanged();
            }
        }
    }

    // 图片设置
    public void setImageViewOption(ImageView iv)
    {
        iv.setLayoutParams(new Gallery.LayoutParams(w_, h_));
        iv.setPadding(0, Util.dip2px(15), 0, 0);
        iv.setScaleType(ScaleType.CENTER_INSIDE);
    }

    // 图片倒影
    public void createReflectedImages(ImageView imageView, Bitmap originalImage)
    {
        // 倒影图和原图之间的距离
        final int reflectionGap = 1;
        // 返回原图解码之后的bitmap对象
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        // 创建矩阵对象
        Matrix matrix = new Matrix();
        // 指定矩阵(x轴不变，y轴相反)
        matrix.preScale(1, -1);
        // 将矩阵应用到该原图之中，返回一个宽度不变，高度为原图1/2的倒影位图
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height * 7 / 8, width, height / 8, matrix, false);

        // 创建一个宽度不变，高度为原图+倒影图高度的位图
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 8), Config.ARGB_8888);

        // 将上面创建的位图初始化到画布
        Canvas canvas = new Canvas(bitmapWithReflection);

        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint deafaultPaint = new Paint();
        deafaultPaint.setColor(Color.WHITE);
        canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();

        /**
         * 参数一:为渐变起初点坐标x位置， 参数二:为y轴位置， 参数三和四:分辨对应渐变终点， 最后参数为平铺方式，
         * 这里设置为镜像Gradient是基于Shader类，所以我们通过Paint的setShader方法来设置这个渐变
         */
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.MIRROR);

        // 设置阴影
        paint.setShader(shader);

        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

        // 用已经定义好的画笔构建一个矩形阴影渐变效果
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        // 创建一个ImageView用来显示已经画好的bitmapWithReflection

        imageView.setImageBitmap(bitmapWithReflection);
        // 设置imageView大小 ，也就是最终显示的图片大小

        // 设置的抗锯齿,防止图像在旋转的时候出现锯齿
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        drawable.setAntiAlias(true);
    }

    @SuppressWarnings("unused")
    private Resources getResources()
    {
        return null;
    }

    public int getCount()
    {
        if (mImages == null)
        {
            return 0;
        }
        return Integer.MAX_VALUE;
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
        return mImages[position % mImages.length];
    }

    public float getScale(boolean focused, int offset)
    {
        return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
    }

}
