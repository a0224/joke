package cc.joke.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.joke.common.Constants;
import cc.joke.entity.T_JokeImgInfo;
import cc.joke.entity.T_JokeInfo;
import cc.joke.entity.T_Result;

/**
 * 货架产品请求服务
 * 
 * @author wanghao
 */
public class JokeListRequest extends BaseRequest
{

    private T_JokeInfo buildProduct(JSONObject o)
    {
        T_JokeInfo p = new T_JokeInfo();
        try
        {
            if (!o.isNull("id"))
            {
            	p.setId(o.getInt("id"));
            }
            if (!o.isNull("titleimg"))
            {
            	p.setIconUrl(o.getString("titleimg"));
            }
            if (!o.isNull("imgmic"))
            {
//            	 p.setImgUrl(o.getString("imgmic"));
            }
            if (!o.isNull("img"))
            {
                p.setImgUrl(o.getString("img"));
            	p.setDspImages(o.getString("img"));
            }
            if (!o.isNull("type"))
            {
            	p.setType(o.getString("type"));
            }
            if (!o.isNull("title"))
            {
            	p.setTitle(o.getString("title"));
            }
            if (!o.isNull("msg"))
            {
            	p.setDescription(o.getString("msg"));
            }
            if (!o.isNull("good"))
            {
            	p.setHighPraise(o.getInt("good"));
            }
            if (!o.isNull("bad"))
            {
            	p.setBadPraise(o.getInt("bad"));
            }
            if (!o.isNull("talk"))
            {
            	p.setTalknum(o.getInt("talk"));
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
    
    
    private T_JokeImgInfo buildImgProduct(JSONObject o)
    {
    	T_JokeImgInfo p = new T_JokeImgInfo();
        try
        {
            if (!o.isNull("id"))
            {
            	p.setId(o.getInt("id"));
            }
            if (!o.isNull("titleimg"))
            {
            	p.setIconUrl(o.getString("titleimg"));
            }
            if (!o.isNull("imgmic"))
            {
//            	 p.setImgUrl(o.getString("imgmic"));
            }
            if (!o.isNull("img"))
            {
                p.setImgUrl(o.getString("img"));
            	p.setDspImages(o.getString("img"));
            }
            if (!o.isNull("type"))
            {
            	p.setType(o.getString("type"));
            }
            if (!o.isNull("title"))
            {
            	p.setTitle(o.getString("title"));
            }
            if (!o.isNull("msg"))
            {
            	p.setDescription(o.getString("msg"));
            }
            if (!o.isNull("good"))
            {
            	p.setHighPraise(o.getInt("good"));
            }
            if (!o.isNull("bad"))
            {
            	p.setBadPraise(o.getInt("bad"));
            }
            if (!o.isNull("talk"))
            {
            	p.setTalknum(o.getInt("talk"));
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
    
    
    public T_Result getJokeImagList(String op, int page)
    {
        putParameter("op", op);
        putParameter("page", page);
        putParameter("pagesize", Constants.OP_JOKE_PAGE_SIZE);
        T_Result obj = new T_Result();
        JSONObject resultObject = getResponse(Constants.OP_JOKE_IMAG_URL);
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
                        List<T_JokeImgInfo> list = new ArrayList<T_JokeImgInfo>(array.length());
                        for (int i = 0; i < array.length(); i += 1)
                        {
                            list.add(buildImgProduct(array.getJSONObject(i)));
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

    public T_Result getJokeList(String op, int page)
    {
        putParameter("op", op);
        putParameter("page", page);
        putParameter("pagesize", Constants.OP_JOKE_PAGE_SIZE);
        T_Result obj = new T_Result();
        JSONObject resultObject = getResponse(Constants.OP_JOKE_URL);
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
                        List<T_JokeInfo> list = new ArrayList<T_JokeInfo>(array.length());
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



