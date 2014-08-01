package com.joke.handler;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.joke.bean.NewsBean;
import com.joke.util.SouEnum;
import com.joke.util.Utils;

public class NewsHandler
{

    public String getNewList(String jsonString)
    {
        List<NewsBean> list = new ArrayList<NewsBean>();
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonString); // 将json字符串转换成JsonElement
        JsonObject jo = jsonElement.getAsJsonObject();
        jsonElement = jo.get("data");
        jo = jsonElement.getAsJsonObject();
        jsonElement = jo.get("news");

        JsonArray jsonArray = jsonElement.getAsJsonArray(); // 将JsonElement转换成JsonArray
        NewsBean bean = null;

        for (JsonElement job : jsonArray)
        {
            jo = job.getAsJsonObject();
            bean = new NewsBean();
            bean.setTitle(jo.get("title").getAsString());
            bean.setUrl(jo.get("url").getAsString());
            bean.setImg(getImageUrl(jo.get("imageurls").getAsJsonArray()));
            bean.setDescrip(jo.get("abs").getAsString());
            bean.setUserId(1);
            bean.setCreateUser("admin");
            bean.setCreateTime(Utils.GetNowDateTime());
            bean.setModifyUser("admin");
            bean.setModifyTime(Utils.GetNowDateTime());
            bean.setStatus(1);
            bean.setSource(SouEnum.SOU_3.getId());
            bean.setSid(jo.get("nid").getAsString());
            list.add(bean);
        }
        return gson.toJson(list);
    }

    public String getImageUrl(JsonArray jsonArray)
    {
        if (jsonArray == null)
        {
            return null;
        }
        StringBuffer buff = new StringBuffer();
        for (JsonElement job : jsonArray)
        {
            JsonObject jo = job.getAsJsonObject();
            String url = jo.get("url").getAsString();
            buff.append(url).append(",");
        }

        return buff.toString();
    }

}
