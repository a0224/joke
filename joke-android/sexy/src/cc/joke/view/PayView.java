package cc.joke.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cc.joke.R;
import cc.joke.util.WqAd;
import cc.joke.wiipay.Wiipay;
import cc.joke.wiipay.Wiipay.PayListener;

public class PayView extends RelativeLayout
{

    private final int[] imgArray = new int[] {R.drawable.pay_background1, R.drawable.pay_background2,
            R.drawable.pay_background3};

    public PayView(Context context)
    {
        super(context);
    }

    public PayView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PayView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        init();
    }

    public void init()
    {
        ViewPager viewPager = (ViewPager) findViewById(R.id.payImage);

        viewPager.setAdapter(new PagerAdapter()
        {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1)
            {
                return arg0 == arg1;
            }

            @Override
            public int getCount()
            {
                return imgArray.length;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                if (position == 0)
                {
                    View ad = WqAd.getInstance().getFullAdView();
                    if (ad != null)
                    {
                        container.addView(ad);
                        return ad;
                    }
                }
                ImageView img = new ImageView(getContext());
                img.setBackgroundResource(imgArray[position]);
                container.addView(img);
                return img;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView((View) object);
                object = null;
            }
        });

        TextView payButton = (TextView) findViewById(R.id.pay_button);
        payButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PayView.this.setVisibility(View.GONE);
                // 激活id为-1
                // Wiipay.getInstance().pay(Wiipay.payCode_1, -1,
                // mPayListener);
            }
        });
    }
}
