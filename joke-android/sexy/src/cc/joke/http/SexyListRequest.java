package cc.joke.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.joke.common.Constants;
import cc.joke.entity.T_Result;
import cc.joke.entity.T_Sexy;

/**
 * 
 * @author wanghao
 */
public class SexyListRequest extends BaseRequest
{

    private T_Sexy buildProduct(JSONObject o)
    {
        T_Sexy p = new T_Sexy();
        try
        {
            if (!o.isNull("id"))
            {
            	//现在获取的头条和两性id比较乱，导致存储出现错乱，暂时屏蔽让其id自增
            	//p.set_id(o.getInt("id"));
            }
            if (!o.isNull("img"))
            {
            	p.setImgUrl(o.getString("img"));
            }
            if (!o.isNull("url"))
            {
            	p.setHtmlUrl(o.getString("url"));
            }
            if (!o.isNull("title"))
            {
            	p.setTitle(o.getString("title"));
            }
            if (!o.isNull("describe"))
            {
            	p.setDescription(o.getString("describe"));
            }
            if (!o.isNull("ischarge"))
            {
            	p.setIscharge(o.getInt("ischarge"));
            }
            if (!o.isNull("price"))
            {
            	p.setPrice(o.getInt("price"));
            }
            if (!o.isNull("source"))
            {
                p.setSource(o.getInt("source"));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return p;
    }

    public T_Result getSexyList(String op, int page)
    {
        putParameter("op", op);
        putParameter("page", page);
        putParameter("pagesize", Constants.OP_SEXY_PAGE_SIZE);
        T_Result obj = new T_Result();
        JSONObject resultObject = getResponse(Constants.OP_SEXY_LIST_URL);
        try
        {
            if (resultObject != null && !resultObject.isNull("status"))
            {
                int result = resultObject.getInt("status");
                if (result == 1)
                {
                    JSONArray array = new JSONArray(resultObject.getString("data"));
                    if (array.length() > 0)
                    {
                        List<T_Sexy> list = new ArrayList<T_Sexy>(array.length());
                        for (int i = 0; i < array.length(); i += 1)
                        {
                            list.add(buildProduct(array.getJSONObject(i)));
                        }
                        obj.mState = 0;
                        obj.mData = list;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            obj.mState = -1;
        }
        finally
        {
        }
        return obj;
    }
}
