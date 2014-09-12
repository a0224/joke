package cc.joke.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.joke.common.Constants;
import cc.joke.entity.T_App;
import cc.joke.entity.T_Result;

public class AppListRequest extends BaseRequest
{
    private T_App buildProduct(JSONObject o)
    {
        T_App p = new T_App();
        try
        {
            if (!o.isNull("id"))
            {
            	 p.setId(o.getInt("id"));
            }
            if (!o.isNull("name"))
            {
            	p.setName(o.getString("name"));
            }
            if (!o.isNull("descrip"))
            {
            	p.setDescrip(o.getString("descrip"));
            }
            if (!o.isNull("img"))
            {
            	p.setIconUrl(o.getString("img"));
            }
            if (!o.isNull("url"))
            {
            	p.setBaseUrl(o.getString("url"));
            }
            if (!o.isNull("channel"))
            {
            	p.setChannel(o.getString("channel"));
            }
            if (!o.isNull("price"))
            {
            	p.setPrice(o.getInt("price"));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return p;
    }

    public T_Result getApkList(String op, int page)
    {
        putParameter("op", op);
        putParameter("page", page);
        putParameter("pagesize", Constants.OP_JOKE_PAGE_SIZE);
        T_Result obj = new T_Result();
        JSONObject resultObject = getResponse(Constants.OP_APK_LIST_URL);
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
                        List<T_App> list = new ArrayList<T_App>(array.length());
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
