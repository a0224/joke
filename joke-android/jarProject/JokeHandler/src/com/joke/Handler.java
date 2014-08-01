package com.joke;

import com.joke.handler.JokeHandler;
import com.joke.handler.NewsHandler;
import com.joke.handler.SexyHandler;
import com.joke.util.HttpUtils;

public class Handler implements HandlerInterface
{

    @Override
    public String getJokeList(String url, Integer userid)
    {
    	//文字
        String jsonTextString = HttpUtils.getResponse(url);
        //图片
        url = "http://ic.snssdk.com/2/essay/v3/image/hot/?count=20&iid=239399570&device_id=2438698769&ac=wifi&channel=tengxun&aid=7&app_name=joke_essay&version_code=268&device_platform=android&device_type=HUAWEI%20G610-U00&os_api=17&os_version=4.2.1&uuid=860623023321506&openudid=b75475664a9929e9";
        String jsonImageString = HttpUtils.getResponse(url);
        
        JokeHandler handler = new JokeHandler();

        return handler.getJokeList(jsonTextString,jsonImageString, userid);
    }

    @Override
    public String getSexyList(String url, String backup)
    {
        String jsonString = HttpUtils.getResponse(url);

        SexyHandler handler = new SexyHandler();

        return handler.getSexyList(jsonString);
    }

    @Override
    public String getNewList(String url, String backup)
    {
        String jsonString = HttpUtils.getResponse(url);

        NewsHandler handler = new NewsHandler();

        return handler.getNewList(jsonString);
    }

}
