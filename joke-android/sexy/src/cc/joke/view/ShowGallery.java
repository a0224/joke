package cc.joke.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import cc.joke.R;

public class ShowGallery extends Gallery implements OnItemSelectedListener
{

    private List<ImageView> balls;

    public ShowGallery(Context context)
    {
        super(context);
        init();
    }

    // public boolean onTouchEvent(MotionEvent event) {
    // if (balls == null || getSelectedItemPosition()>=balls.size()) {
    // return false;
    // }
    // return super.onTouchEvent(event);
    // }

    public ShowGallery(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ShowGallery(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public void setImages(List<ImageView> balls)
    {
        this.balls = balls;
    }

    public void init()
    {
        this.setStaticTransformationsEnabled(true);
        this.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
    {
        // 设置球显示
        for (int i = 0; i < balls.size(); i++)
        {
            ImageView iv = balls.get(i);
            if (i == position % balls.size())
            {
                iv.setImageResource(R.drawable.product_ball_2);
            }
            else
            {
                iv.setImageResource(R.drawable.product_ball_1);
            }
        }
    }

    public void onNothingSelected(AdapterView<?> arg0)
    {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        // TODO Auto-generated method stub
        int kEvent;
        if (isScrollingLeft(e1, e2))
        {
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        }
        else
        {
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null);
        return true;

    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2)
    {
        return e2.getX() > e1.getX();
    }

}
