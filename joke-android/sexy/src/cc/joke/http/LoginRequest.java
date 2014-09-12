package cc.joke.http;

import org.json.JSONObject;

import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.entity.T_Login;

/**
 * 激活请求
 * 
 * @author wanghao
 */
public class LoginRequest extends BaseRequest
{

    public int login(String userIcon, String userName, String openid, String token)
    {

        putParameter("op", Constants.OP_LOGIN);
        putParameter("username", userName);
        putParameter("usericon", userIcon);
        putParameter("openid", openid);
        putParameter("token", token);

        try
        {
            JSONObject resultObject = getResponse(Constants.OP_LOGIN_URL);
            if (resultObject != null && !resultObject.isNull("status"))
            {
                int result = resultObject.getInt("status");
                if (result == 1)
                {
                    /** 记录数据到本地 **/
                    if (!resultObject.isNull("id"))
                    {
                        int userId = resultObject.getInt("id");
                        T_Login o = new T_Login();
                        o.setStatus(result);
                        o.setUsericon(userIcon);
                        o.setUserid(userId);
                        o.setUsername(userName);
                        o.setOpenid(openid);
                        o.setToken(token);
                        GlobalApplication.dBHelper.insert(o);
                        return userId;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }
}
