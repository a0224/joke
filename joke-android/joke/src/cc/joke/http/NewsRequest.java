package cc.joke.http;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.ImageDownLoadCallback;
import cc.joke.common.Constants;
import cc.joke.entity.T_News;
import cc.joke.util.Util;

/**
 * 新闻消息请求
 */
public class NewsRequest extends BaseRequest
{

    private T_News buildObject(JSONObject json)
    {
        T_News o = new T_News();
        try
        {
            if (!json.isNull("content"))
            {
            	o.setContent(json.getString("content"));
            }
            if (!json.isNull("createTime"))
            {
            	o.setCreateTime(json.getString("createTime"));
            }
            if (!json.isNull("detailContent"))
            {
            	o.setDetailContent(json.getString("detailContent"));
            }
            if (!json.isNull("existsDetail"))
            {
            	o.setExistsDetail(json.getInt("existsDetail"));
            }
            if (!json.isNull("icon"))
            {
            	o.setIconUrl(json.getString("icon"));
            }
            if (!json.isNull("title"))
            {
            	o.setTitle(json.getString("title"));
            }
            if (!json.isNull("id"))
            {
            	o.setNewsID(json.getInt("id"));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return o;
    }

    public boolean getMessage()
    {
        try
        {
            JSONObject resultObject = getResponse(Constants.NEWS_URL);
            if (resultObject != null && !resultObject.isNull("Result"))
            {
                int result = resultObject.getInt("Result");
                if (result == 0)
                {
                    if (!resultObject.isNull("Data"))
                    {
                        JSONArray array = new JSONArray(resultObject.getString("Data"));
                        for (int i = 0; i < array.length(); i += 1)
                        {
                            JSONObject obj = array.getJSONObject(i);
                            final T_News news = buildObject(obj);
                            GlobalApplication.dBHelper.insert(news);
                            String iconUrl = formatIconName(news);
                            if (!"".equals(iconUrl))
                            {
                                GlobalApplication.bitmapCache.getBitmap(iconUrl, null);
                            }
                            List<String> images = formatImageName(news);
                            if (images != null)
                            {
                                for (final String image : images)
                                {
                                    GlobalApplication.bitmapCache.getBitmap(image, new ImageDownLoadCallback()
                                    {
                                        public void imageDownLoaded(Bitmap bm)
                                        {
                                            /** 如果图片下载成功,则需要将图片地址都替换为本地图片地址 **/
                                            if (bm != null)
                                            {
                                                String localPath = Util.buildString("file://",
                                                        Constants.DOWNLOAD_IMAGE_DIR, "/", Util.getFileNameByUrl(image));
                                                news.setDetailContent(news.getDetailContent().replace(image, localPath));
                                                GlobalApplication.dBHelper.update(news, "_id = ?",
                                                        new String[] {String.valueOf(news.get_id())});
                                            }
                                        }
                                    });
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private String formatIconName(T_News news)
    {
        if (news != null && news.getIconUrl() != null && !"".equals(news.getIconUrl().trim()))
        {
            return news.getIconUrl();
        }
        return "";
    }

    private List<String> formatImageName(T_News news)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            if (news != null && news.getDetailContent() != null)
            {
                Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = pattern.matcher(news.getDetailContent());
                while (matcher.find())
                {
                    String url = matcher.group(1);
                    if (null != url && !"".equals(url.trim()))
                    {
                        list.add(url);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }

}
