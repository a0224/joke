package cc.joke.http;

import java.util.List;

import org.json.JSONObject;

import android.os.Build;
import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.entity.T_Activation;
import cc.joke.util.Util;

/**
 * 激活请求
 * 
 * @author wanghao
 */
public class ActivationRequest extends BaseRequest
{

    public boolean activation()
    {
        /** 判断是否已经激活过 **/
        List<T_Activation> list = GlobalApplication.dBHelper.query(T_Activation.class, null, null, null);
        if (list != null && list.size() > 0)
        {
            return true;
        }
        putParameter("op", Constants.OP_ACTIVATION);
        putParameter("imei", Util.getImei(GlobalApplication.context));
        String imsi = Util.getImsi(GlobalApplication.context);
        putParameter("imsi", (imsi == null) ? "" : imsi);
        putParameter("mac", Util.getMac(GlobalApplication.context));
        putParameter("os", Build.VERSION.RELEASE);
        putParameter("display", GlobalApplication.widthPixels + "*" + GlobalApplication.heightPixels);
        putParameter("product", Build.PRODUCT);
        putParameter("brand", Build.BRAND);
        putParameter("model", Build.MODEL);
        putParameter("language", Util.getLanguage());
        putParameter("operators", Util.getOperators(GlobalApplication.context));
        putParameter("vcode", 1);
        putParameter("vname", "vname");
        String phone = Util.getPhone(GlobalApplication.context);
        putParameter("phone", (phone == null) ? "" : phone);
        putParameter("channel", "channel");
        putParameter("appcode", "appcode");
        putParameter("network", Util.getNetworkType(GlobalApplication.context));

        try
        {
            JSONObject resultObject = getResponse(Constants.OP_ACTIVATION_URL);
            if (resultObject != null && !resultObject.isNull("status"))
            {
                int result = resultObject.getInt("status");
                if (result == 1)
                {
                    /** 记录数据到本地 **/
                    T_Activation o = new T_Activation();
                    o.setStatus(result);
                    GlobalApplication.dBHelper.insert(o);
                    return true;
                }
            }
        }
        catch (Exception e)
        {
        	Logger.error(e);
            e.printStackTrace();
        }
        return false;
    }
}
