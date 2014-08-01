package cc.joke.util;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQ
{
    private Tencent mTencent;

    public static QQAuth mQQAuth;

    private UserInfo mInfo;

    private Activity mActivity;

    private LoginListener mLoginListener;

    private static QQ mQQ;

    private QQ(Activity activity)
    {
        mActivity = activity;
        mQQAuth = QQAuth.createInstance("1101767429", activity);
        mTencent = Tencent.createInstance("1101767429", activity);
    }

    public static QQ getInstance(Activity activity)
    {
        if (mQQ == null)
        {
            mQQ = new QQ(activity);
        }
        return mQQ;
    }

    public static QQ getInstance()
    {
        return mQQ;
    }

    public void loginQQ()
    {
        if (!mQQAuth.isSessionValid())
        {
            IUiListener listener = new IUiListener()
            {
                @Override
                public void onCancel()
                {

                }

                @Override
                public void onError(UiError arg0)
                {

                }

                @Override
                public void onComplete(Object arg0)
                {
                    getUserInfo();
                }
            };
             mQQAuth.login(mActivity, "all", listener);
//            mTencent.loginWithOEM(mActivity, "all", listener, "10000144", "10000144", "xxxx");
        }
    }

    public void LogoutQQ()
    {
        if (mQQAuth != null && mQQAuth.isSessionValid())
        {
            mQQAuth.logout(mActivity);
        }
    }

    // 判断是否登陆
    public boolean ready()
    {
        if (mQQAuth == null)
        {
            return false;
        }
        boolean ready = mQQAuth.isSessionValid() && mQQAuth.getQQToken().getOpenId() != null;
        if (!ready)
            Toast.makeText(mActivity, "login and get openId first, please!", Toast.LENGTH_SHORT).show();
        return ready;
    }

    private void getUserInfo()
    {
        if (mQQAuth != null && mQQAuth.isSessionValid())
        {
            IUiListener listener = new IUiListener()
            {
                @Override
                public void onError(UiError e)
                {
                    if (mLoginListener != null)
                    {
                        mLoginListener.onError();
                    }
                }

                @Override
                public void onComplete(final Object response)
                {
                    try
                    {
                        JSONObject json = (JSONObject) response;
                        String iconUrl = json.has("figureurl") ? json.getString("figureurl_qq_2") : "";
                        String userName = json.has("nickname") ? json.getString("nickname") : "";
                        if (mLoginListener != null)
                        {
                            mLoginListener.doComplete(iconUrl, userName, mQQAuth.getQQToken().getOpenId(),
                                    mQQAuth.getQQToken().getAccessToken());
                        }
                    }
                    catch (Exception e)
                    {
                    }
                }

                @Override
                public void onCancel()
                {
                    if (mLoginListener != null)
                    {
                        mLoginListener.onCancel();
                    }
                }
            };
            mInfo = new UserInfo(mActivity, mQQAuth.getQQToken());
            mInfo.getUserInfo(listener);

        }
        else
        {
        }
    }

    public void setLoginListener(LoginListener loginListener)
    {
        mLoginListener = loginListener;
    }

    public interface LoginListener
    {
        public void doComplete(String iconUrl, String userName, String openid, String token);

        public void onCancel();

        public void onError();
    }
}
