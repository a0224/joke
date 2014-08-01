package com.joke.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpUtils
{
    private static final int TIMEOUT = 30 * 000;

    public static String getResponse(String url)
    {

        StringBuffer buffer = new StringBuffer();
        try
        {
            InputStream in = getInputStream(url, TIMEOUT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            reader.close();
            in.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return buffer.toString();
    }

    private static InputStream getInputStream(String url, int timeout)
    {

        HttpURLConnection conn = null;
        try
        {
            if (url == null || "".equals(url.trim()))
            {
                return null;
            }
            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();
            if (timeout != -1)
            {
                conn.setConnectTimeout(timeout);
            }
            else
            {
                conn.setConnectTimeout(TIMEOUT);
            }
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");

            if (conn.getResponseCode() == 404)
            {
            }
            else
            {
                return conn.getInputStream();
            }
        }
        catch (SocketTimeoutException e)
        {
        }
        catch (Exception e)
        {
        }
        finally
        {
        }
        return null;
    }
}
