package cc.joke.wiipay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import cc.joke.application.GlobalApplication;
import cc.joke.entity.T_Pay;

import com.bx.pay.BXPay;
import com.bx.pay.backinf.PayCallback;

public class Wiipay
{
    private static Wiipay mWiipay;

    private BXPay bxpay;

    public static String payCode_1 = "0001";// 激活

    public static String payCode_2 = "0002";

    private Context mContext;

    private Wiipay(Context context)
    {
        mContext = context;
    }

    public static Wiipay getInstance(Context context)
    {
        if (mWiipay == null)
        {
            mWiipay = new Wiipay(context);
        }

        return mWiipay;
    }

    public static Wiipay getInstance()
    {
        return mWiipay;
    }

    public void pay(String payCode, final int payId, final PayListener listener)
    {
        if (bxpay == null)
        {
            bxpay = new BXPay(mContext);
        }

        if (listener == null)
        {
            return;
        }
        Map<String, String> devPrivate = new HashMap<String, String>();
        devPrivate.put("开发者要传的KEY值", "开发者要传的VALUE值");
        bxpay.setDevPrivate(devPrivate);// setDevPrivate方式是非必选
        bxpay.pay(payCode, new PayCallback()
        {
            @Override
            public void pay(Map resultInfo)
            {
                // Map resultInfo 主要返回6个字段
                String result = (String) resultInfo.get("result");
                String payType = (String) resultInfo.get("payType");
                String payCode = (String) resultInfo.get("payCode");
                String price = (String) resultInfo.get("price");
                String logCode = (String) resultInfo.get("logCode");
                String showMsg = (String) resultInfo.get("showMsg");

                if (result.equals("success"))
                {
                    savePay(payId);

                    listener.onPaySucceed();

                }
                else if (result.equals("pass"))
                {
                    listener.onPaySucceed();
                    if (!isPayed(payId))
                    {
                        savePay(payId);
                    }
                }
                else
                {
                    listener.onPayFailed();
                    new AlertDialog.Builder(mContext).setTitle("支付结果返回：").setMessage(
                            Html.fromHtml("支付结果：" + result + "<br>" + "支付类型：" + payType + "<br>" + "计费点：" + payCode
                                    + "<br>" + "计费价格：" + price + "<br>" + "订单编号：" + logCode + "<br>" + "支付结果描述："
                                    + showMsg + "<br>")).setPositiveButton("确定", null).show();

                }
            }
        });
    }

    public boolean isPayed(int payId)
    {
        /** 判断是否已经评论过 **/
        List<T_Pay> list = GlobalApplication.dBHelper.query(T_Pay.class, "pay = ?",
                new String[] {String.valueOf(payId)}, null);
        if (list != null && list.size() > 0)
        {
            return true;
        }
        return false;
    }

    private void savePay(int payId)
    {
        T_Pay pay = new T_Pay();
        pay.setPay(payId);
        GlobalApplication.dBHelper.insert(pay);
    }

    public interface PayListener
    {
        public void onPaySucceed();

        public void onPayFailed();
    }
}
