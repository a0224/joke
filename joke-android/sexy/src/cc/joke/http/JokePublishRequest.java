package cc.joke.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.entity.T_App;
import cc.joke.entity.T_Publish;
import cc.joke.entity.T_Result;

public class JokePublishRequest extends BaseRequest
{

    public T_Publish getPublisht(JSONObject o) throws Exception
    {
        if (o == null)
        {
            return null;
        }
        T_Publish publish = new T_Publish();
        publish.setUsername(o.getString("title"));
        publish.setContent(o.getString("msg"));
        publish.setStatus(o.getInt("status"));
        publish.setUsericon(o.getString("titleimg"));
        return publish;
    }

    // 获取评论信息
    public T_Result getPublishs(Integer userId, int pageNo)
    {

        putParameter("op", Constants.OP_JOKE_FUSERS);
        putParameter("page", pageNo);
        putParameter("pagesize", 8);
        putParameter("userid", userId);
        JSONObject resultObject = getResponse(Constants.OP_JOKE_FUSERS_URL);

        T_Result obj = new T_Result();
        try
        {
            if (resultObject == null)
            {
                return null;
            }
            if (resultObject.getInt("status") != 1)
            {
                return null;
            }

            JSONArray arr = resultObject.getJSONArray("data");
            if (arr != null && arr.length() > 0)
            {
                List<T_Publish> list = new ArrayList<T_Publish>(arr.length());
                for (int i = 0; i < arr.length(); i++)
                {
                    list.add(getPublisht(arr.getJSONObject(i)));
                }
                obj.mState = 0;
                obj.mData = list;
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        return obj;
    }

    // 提交评论信息
    public void commitPublish(T_Publish publish)
    {
        try
        {
            putParameter("op", Constants.OP_JOKE_ADD_JOKE);
            putParameter("msg", publish.getContent());
            putParameter("userid", publish.getUserid());
            putParameter("imgurl", publish.getImgUrl());

            getResponse(Constants.OP_JOKE_ADD_JOKE_URL);
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
    }
}
