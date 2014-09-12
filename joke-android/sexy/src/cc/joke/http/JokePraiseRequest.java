package cc.joke.http;

import java.util.List;

import org.json.JSONObject;

import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.entity.T_Praise;

/**
 * 产品好差评请求服务
 * 
 * @author wanghao
 */
public class JokePraiseRequest extends BaseRequest
{

    public int doCommit(int jokeID, int praiseType)
    {
        /** 判断是否已经评论过 **/
        List<T_Praise> list = GlobalApplication.dBHelper.query(T_Praise.class, "productID = ?",
                new String[] {String.valueOf(jokeID)}, null);
        if (list != null && list.size() > 0)
        {
            return 1;
        }
        putParameter("op", Constants.OP_JOKE_VALUATE);
        putParameter("userid", jokeID);
        putParameter("jokeid", jokeID);
        putParameter("type", praiseType);
        JSONObject resultObject = getResponse(Constants.OP_JOKE_VALUATE_URL);
        try
        {
            if (resultObject != null && !resultObject.isNull("status"))
            {
                T_Praise o = new T_Praise();
                int result = resultObject.getInt("status");
                if (result == 1)
                {
                    /** 记录数据到本地 **/
                    o.setPraiseType(praiseType);
                    o.setProductID(jokeID);
                    GlobalApplication.dBHelper.insert(o);
                    return 0;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return -1;
    }

}
