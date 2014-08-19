package cc.joke.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryFlow extends Gallery
{

    // 相机类
    private Camera mCamera = new Camera();

    // 最大转动角度
    private int mMaxRotationAngle = 30;

    // 最大缩放值
    private int mMaxZoom = -300;

    // 半径值
    private int mCoveflowCenter;

    // 方向
    private int direc = KeyEvent.KEYCODE_DPAD_RIGHT;

    public GalleryFlow(Context context)
    {
        super(context);
        this.setStaticTransformationsEnabled(true);
    }

    public GalleryFlow(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setStaticTransformationsEnabled(true);
    }

    public GalleryFlow(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.setStaticTransformationsEnabled(true);
    }

    public int getMaxRotationAngle()
    {
        return mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle)
    {
        mMaxRotationAngle = maxRotationAngle;
    }

    public int getMaxZoom()
    {
        return mMaxZoom;
    }

    public void setMaxZoom(int maxZoom)
    {
        mMaxZoom = maxZoom;
    }

    private int getCenterOfCoverflow()
    {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    private static int getCenterOfView(View view)
    {
        return view.getLeft() + view.getWidth() / 2;
    }

    // 控制gallery中每个图片的旋转
    protected boolean getChildStaticTransformation(View child, Transformation t)
    {

        // 取得当前子View的半径值
        final int childCenter = getCenterOfView(child);
        final int childWidth = child.getWidth();

        // 旋转角度
        int rotationAngle = 0;
        // 重置转换状态
        t.clear();
        // 设置转换类型
        t.setTransformationType(Transformation.TYPE_MATRIX);
        // 如果图片位于中心位置则不需要进行旋转
        if (childCenter == mCoveflowCenter)
        {
            transformImageBitmap((ImageView) child, t, 0);
        }
        else
        {

            // 根据图片在gallery中得位置来计算图片的旋转角度
            rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
            // 如果旋转角度绝对值大于最大旋转角度返回（-mMaxRotationAngle或mMaxRotationAngle;）
            if (Math.abs(rotationAngle) > mMaxRotationAngle)
            {
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
            }
            transformImageBitmap((ImageView) child, t, rotationAngle);
        }

        return true;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle)
    {
        // 对效果进行保存
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        final int rotation = Math.abs(rotationAngle);

        // 在Z轴上正向移动camera的视角，实际效果为放大图片。
        // 如果在Y轴上移动，则图片上下移动；X轴上对应图片左右移动。
        mCamera.translate(0.0f, 0.0f, 100.0f);

        if (rotation < mMaxRotationAngle)
        {
            float zoomAmount = (float) (mMaxZoom + (rotation * 4));
            mCamera.translate(0.0f, 0.0f, zoomAmount);
        }

        // 在Y轴上旋转，对应图片竖向向里翻转。
        // 如果在X轴上旋转，则对应图片横向向里翻转。
        mCamera.rotateY(rotationAngle);
        mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        mCamera.restore();
    }

    private boolean isScrollingLeft(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2)
    {// 判断向左还是向右滑动
        float f2 = paramMotionEvent2.getX();
        float f1 = paramMotionEvent1.getX();
        if (f2 > f1)
            return true;
        return false;
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1,
            float paramFloat2)
    {
        if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2))
        {
            direc = KeyEvent.KEYCODE_DPAD_LEFT;
        }
        else
        {
            direc = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        return super.onFling(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    /** 由于定时器不可重复启动运行,这里需要每次使用时创建,用完释放 **/
    private Timer timer = new Timer();

    private TimerTask task = null;

    public void onPause()
    {
        if (task != null)
        {
            task.cancel();
            task = null;
        }
    }

    public void onResume()
    {
        autoRoll();
    }

    // auto
    public void autoRoll()
    {
        if (task != null)
        {
            task.cancel();
            task = null;
        }
        task = new TimerTask()
        {
            public void run()
            {
                mHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task, 3000, 3000);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    onKeyDown(direc, null);
                    break;
                default:
                    break;
            }
        };
    };
}
