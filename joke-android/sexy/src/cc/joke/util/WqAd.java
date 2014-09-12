package cc.joke.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;

import com.wqmobile.sdk.WQAdEventListener;
import com.wqmobile.sdk.WQAdMode;
import com.wqmobile.sdk.WQAdView;
import com.wqmobile.sdk.WQInterstitialAdView;

public class WqAd
{

    public static final String ADSLOT_BANNER = "66efc26e2189248bf494f44296f836c6";

    public static final String ADSLOT_WALL = "b033bebc1144cc8a61d44c7938454129";

    public static final String ACCOUNT_KEY = "c73d49b0e4a05f3993e4b25eee44623f";

    public static final String ADSLOT_INTERS_FULL = "f9828b5402c3e62a34d4c424e3fe3981";

    private Activity mActivity;

    private static WqAd wqad;

    private WQInterstitialAdView intersFullAdView;

    private WqAd(Activity activity)
    {
        mActivity = activity;

        // 插播全屏广告，WQAdMode中，POPUP表示弹出式，给插播全屏广告使用；EMBEDDED表示嵌入式，给插播半屏广告使用
        intersFullAdView = new WQInterstitialAdView(mActivity, WQAdMode.POPUP);
        intersFullAdView.init(ADSLOT_INTERS_FULL, ACCOUNT_KEY);
        if (!intersFullAdView.isInterstitialAdReady())
        {
            intersFullAdView.loadInterstitialAd();
        }
    }

    public static WqAd getInstance(Activity activity)
    {
        if (wqad == null)
        {
            wqad = new WqAd(activity);
        }
        return wqad;
    }

    public static WqAd getInstance()
    {
        return wqad;
    }

    public void showAppMarket()
    {

        WQAdView.openAdWall(mActivity, ADSLOT_WALL, ACCOUNT_KEY);
    }

    public View getFullAdView()
    {

        if (intersFullAdView.isInterstitialAdReady())
        {
            intersFullAdView.showInterstitialAd();
        }
        else
        {
            return null;
        }
        return intersFullAdView;
    }

    public void initWQAdView(WQAdView adView)
    {
        if (adView == null)
        {
            return;
        }

        adView.setAdEventListener(new WQAdEventListener()
        {
            @Override
            public void onWQAdReceived(WQAdView adView)
            {
            }

            @Override
            public void onWQAdFailed(WQAdView adView)
            {
            }

            @Override
            public void onWQAdDismiss(WQAdView arg0)
            {
            }
        });
        adView.init(WqAd.ADSLOT_BANNER, WqAd.ACCOUNT_KEY);
    }

    public void initWQAdView(ViewGroup rootView)
    {
        WQAdView adView = new WQAdView(mActivity);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        adView.setAdEventListener(new WQAdEventListener()
        {
            @Override
            // 接收到广告回调
            public void onWQAdReceived(WQAdView adView)
            {
            }

            @Override
            // 广告接收失败回调
            public void onWQAdFailed(WQAdView adView)
            {
            }

            @Override
            // 广告即将关闭回调
            public void onWQAdDismiss(WQAdView arg0)
            {
            }
        });
        adView.init(ADSLOT_BANNER, ACCOUNT_KEY);

        rootView.addView(adView, params);

    }
}
