package cc.joke.http;

import org.json.JSONObject;
import org.json.JSONTokener;

import cc.joke.common.Constants;
import cc.joke.entity.CheckClient;

/**
 * 检测客户端版本网络请求
 */
public class CheckClientRequest extends BaseRequest
{

    public CheckClient getCheckClient(int versionCode)
    {
        CheckClient cc = null;
        JSONObject json = getResponse(Constants.CHECK_CLIENT_URL);
        if (json != null)
        {
            try
            {
                int status = json.getInt("status");
                if (status == 1)
                {
                    cc = new CheckClient();
                    int id = json.has("id") ? json.getInt("id") : 0;
                    String vname = json.has("vname") ? json.getString("vname") : "";
                    int vcode = json.has("vcode") ? json.getInt("vcode") : 0;
                    String des = json.has("descrip") ? json.getString("descrip") : "";
                    String url = json.has("url") ? json.getString("url") : "";
                    cc.setId(id);
                    cc.setVersionName(vname);
                    cc.setVersionCode(vcode);
                    cc.setDescription(des);
                    cc.setBaseUrl(url);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return cc;
    }

}
