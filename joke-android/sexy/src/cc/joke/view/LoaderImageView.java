package cc.joke.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import cc.joke.R;

public class LoaderImageView extends ImageView
{

    private boolean mShowLoading = true;

    private Context mContext;

    private int mRotate = 0;

    private Bitmap mBackground;

    private Bitmap mLoaderView;

    public LoaderImageView(Context context)
    {
        super(context);
        this.mContext = context;
        init();
    }

    public LoaderImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public LoaderImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    private void init()
    {
        mLoaderView = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.loading);
        mBackground = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.loading_background);
    }

    private Handler mHander = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            invalidate();
        };
    };

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (mShowLoading)
        {
            canvas.drawColor(Color.rgb(222, 222, 222));
            canvas.drawBitmap(mBackground, getWidth() / 2 - mBackground.getWidth() / 2,
                    getHeight() / 2 - mBackground.getHeight() / 2, null);
            canvas.save();
            canvas.rotate(mRotate, getWidth() / 2, getHeight() / 2);
            canvas.drawBitmap(mLoaderView, getWidth() / 2 - mLoaderView.getWidth() / 2,
                    getHeight() / 2 - mLoaderView.getHeight() / 2, null);
            canvas.restore();
            mRotate += 30;
            mHander.sendEmptyMessageDelayed(0, 100);
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        super.setImageBitmap(bm);
        mShowLoading = false;
    }

    @Override
    public void setImageDrawable(Drawable drawable)
    {
        super.setImageDrawable(drawable);
        mShowLoading = false;
    }

    @Override
    public void setImageResource(int resId)
    {
        super.setImageResource(resId);
        mShowLoading = false;
    }

}
