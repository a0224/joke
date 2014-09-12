package cc.joke.http;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.util.Util;

public class DownloadRequest extends BaseRequest
{
    public int getFileLength(String url)
    {
        // try {
        // String flag = "?id=";
        // String product_id =
        // url.substring(url.indexOf(flag)+flag.length(),url.length());
        // putParameter("product_id", product_id);
        // JSONObject resultObject = getResponse(Constants.GET_FILE_LENGTH);
        // if(resultObject==null){
        // return -1;
        // }
        // int len = resultObject.getInt("Data");
        // return len;
        // } catch (Exception e) {
        // Logger.error(e);
        // }

        return -1;
    }

    // 返回流
    public InputStream getInputStream(String url, int begin, int end)
    {
        if (!Util.isNetworkConnected())
        {
            return null;
        }
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        try
        {
            if (url == null || "".equals(url.trim()))
            {
                return null;
            }
            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestProperty("Range", "bytes=" + begin + "-" + end);
            conn.setConnectTimeout(Constants.TIMEOUT);

            if (conn.getResponseCode() == 404)
            {
                Logger.i("警告", url + "文件不存在!");
            }
            else
            {
                return conn.getInputStream();
            }
        }
        catch (SocketTimeoutException e)
        {
            Logger.i("-_-!", url + "下载超时...");
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            try
            {
                if (dos != null)
                    dos.close();
            }
            catch (Exception ex)
            {
            }
            Logger.i("", "下载文件" + url);
        }
        return null;
    }
}
