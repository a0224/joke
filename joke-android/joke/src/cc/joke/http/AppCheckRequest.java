package cc.joke.http;

import org.json.JSONArray;
import org.json.JSONObject;

import cc.joke.application.GlobalApplication;
import cc.joke.common.Constants;
import cc.joke.entity.T_App;
import cc.joke.util.Util;

/**
 * APP应用列表检测更新
 */
public class AppCheckRequest extends BaseRequest
{

    public boolean doCheckVersion(JSONArray paramArray)
    {
        putParameter("APPS", paramArray);
        JSONObject resultObject = getResponse(Constants.APP_CHECK_URL);
        try
        {
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
                            /** 判断本地是否还存在 **/
                            if (!Util.isInstallApplication(GlobalApplication.context, obj.getString("PKG")))
                            {
                                GlobalApplication.dBHelper.delete(T_App.class, "PackageName = ?",
                                        new String[] {obj.getString("PKG")});
                                continue;
                            }
                            if (obj.getInt("EXISTS") == 1)
                            {
                                T_App app = new T_App();
                                // app.setPackageName(obj.getString("PKG"));
                                // app.setExistsDetail(1);
                                // app.setProductID(obj.getInt("P_ID"));
                                // app.setNewSize(obj.getInt("P_SIZE"));
                                // app.setNewVersionCode(obj.getInt("P_VERSION_CODE"));
                                // app.setNewVersionName(obj.getString("P_VERSION_NAME"));
                                // app.setBaseUrl(obj.getString("BaseUrl"));
                                // app.setFileName(obj.getString("FileName"));
                                // app.setIconUrl(obj.getString("IconUrl"));
                                // GlobalApplication.dBHelper.updateApp(app);
                                // /** 将可更新的程序存入缓存中 **/
                                // if(app.getVersionCode() <
                                // app.getNewVersionCode()){
                                // GlobalApplication.mUpgradeCache.put(String.valueOf(app.getProductID()),
                                // app.getNewVersionName());
                                // }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

}
