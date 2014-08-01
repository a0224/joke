package cc.joke.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.Log;
import cc.joke.application.GlobalApplication;
import cc.joke.cache.ImageOption;
import cc.joke.common.Constants;
import cc.joke.debug.Logger;
import cc.joke.util.Util;

@SuppressLint("DefaultLocale")
public class BaseRequest
{

    /**
     * 客户端请求参数容器
     */
    // private JSONObject mParam = new JSONObject();

    private JSONObject mParameter = new JSONObject();

    public BaseRequest()
    {
        /** 初始化运营业务请求参数 **/
        try
        {
            // Context context = GlobalApplication.context;
            // mParam.put(RequestParam.APP_VERSION, "1.0.0.0");
            // mParam.put(RequestParam.DEVICE_ID, GlobalApplication.uuid);
            // mParam.put(RequestParam.IMEI, Util.getImei(context));
            // mParam.put(RequestParam.IMSI, Util.getImsi(context));
            // mParam.put(RequestParam.MODEL, Build.MODEL);
            // mParam.put(RequestParam.NETWORK_TYPE,
            // Util.getNetworkType(context));
            // mParam.put(RequestParam.OS_VERSION, Build.VERSION.RELEASE);
            // mParam.put(RequestParam.PHONE, Util.getPhone(context));
            // mParam.put(RequestParam.PROJECT_ID,
            // GlobalApplication.projectid);
            // mParam.put(RequestParam.SCREEN_HEIGHT,
            // GlobalApplication.heightPixels);
            // mParam.put(RequestParam.SCREEN_WIDTH,
            // GlobalApplication.widthPixels);
            // mParam.put(RequestParam.SYSTEM_STATUS,
            // Util.isSystemApplication(context, context.getPackageName()) ? 1
            // : 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public BaseRequest putParameter(String key, Object value)
    {
        try
        {
            mParameter.put(key, value);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return this;
    }

    @SuppressLint("DefaultLocale")
    public Bitmap getBitmapByUrl(String url, ImageOption... option)
    {
        InputStream is = null;
        try
        {
            URL myFileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }

        if (is == null)
        {
            return null;
        }
        // byte[] b = getBytes(is);
        // if(b==null){
        // return null;
        // }
        Bitmap bm = null;
        if (option != null && option.length > 0)
        {
            ImageOption op = option[0];

            int width = op.getWidth();
            int height = op.getHeight();

            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            // BitmapFactory.decodeByteArray(b, 0, b.length, opts);
            opts.inJustDecodeBounds = false;
            if (op.isInclinationWidth() && opts.outHeight > opts.outWidth)
            {// 倾向宽，但当前高比宽长的
                opts.inSampleSize = opts.outHeight / width;
                // bm = BitmapFactory.decodeByteArray(b, 0, b.length, opts);
                bm = BitmapFactory.decodeStream(is, null, opts);
                // 旋转90度
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            }
            else if (op.isInclinationHeight() && opts.outWidth > opts.outHeight)
            {
                opts.inSampleSize = opts.outWidth / height;
                // bm = BitmapFactory.decodeByteArray(b, 0, b.length, opts);
                bm = BitmapFactory.decodeStream(is, null, opts);
                // 旋转90度
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            }
            else
            {
                int x = opts.outWidth / width;
                int y = opts.outHeight / height;
                opts.inSampleSize = x > y ? x : y;
                // bm = BitmapFactory.decodeByteArray(b, 0, b.length, opts);
                bm = BitmapFactory.decodeStream(is, null, opts);
            }

        }
        else
        {
            // bm = BitmapFactory.decodeByteArray(b, 0, b.length);
            bm = BitmapFactory.decodeStream(is);
        }

        // 存入SD卡
        if (Util.hasSdcard() && bm != null)
        {
            try
            {
                String fileName = Util.getFileNameByUrl(url);
                File file = new File(Constants.DOWNLOAD_IMAGE_DIR + "/" + fileName);
                if (!file.getParentFile().exists())
                {
                    file.getParentFile().mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                if (fileName.toUpperCase().endsWith(".PNG"))
                {
                    bm.compress(CompressFormat.PNG, 100, fos);
                }
                else
                {
                    bm.compress(CompressFormat.JPEG, 100, fos);
                }
            }
            catch (FileNotFoundException e)
            {
                Logger.error(e);
            }
        }

        return bm;
    }

    // 返回字节
    public byte[] getBytes(InputStream is)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int imgSize = 1024;
        byte[] b = null;
        byte[] bytes = null;
        try
        {
            b = new byte[imgSize];
            int len = 0;
            while ((len = is.read(b, 0, imgSize)) != -1)
            {
                baos.write(b, 0, len);
                baos.flush();
            }
            bytes = baos.toByteArray();
        }
        catch (OutOfMemoryError e)
        {
            Logger.e("*_*", "悲剧，转换InputStream=>byte[]发生内存泄漏...");
            return null;
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            if (baos != null)
            {
                try
                {
                    baos.close();
                }
                catch (IOException e)
                {
                    Logger.error(e);
                }
                baos = null;
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    Logger.error(e);
                }
                is = null;
            }
        }
        return bytes;
    }

    // 返回流
    public InputStream getInputStream(String url, int timeout)
    {
        if (!Util.isNetworkConnected())
        {
            return null;
        }
        long time1 = System.currentTimeMillis();
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
            if (timeout != -1)
            {
                conn.setConnectTimeout(timeout);
            }
            else
            {
                conn.setConnectTimeout(Constants.TIMEOUT);
            }
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);// 是否输入参数
            conn.setDoInput(true);

            /** 设置属性协议头 **/
            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");

            dos = new DataOutputStream(conn.getOutputStream());
            mParameter.put(RequestParam.OP_DEVICE_ID, GlobalApplication.deviceid);
            dos.write(mParameter.toString().getBytes("UTF-8"));
            Logger.e("Request:", mParameter.toString());
            dos.flush();
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
            long time2 = System.currentTimeMillis();
            try
            {
                if (dos != null)
                    dos.close();
            }
            catch (Exception ex)
            {
            }
            Logger.i("请求访问", url + "(" + (time2 - time1) + "ms)");
        }
        return null;
    }

    // 获取文件长度
    public int getFileLength(String url)
    {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        try
        {
            if (url == null || "".equals(url.trim()))
            {
                return -1;
            }
            String product_id = url.substring(url.indexOf("?") + 1, url.length());
            putParameter("product_id", product_id).putParameter("findLen", "1");

            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);// 是否输入参数
            conn.setDoInput(true);

            /** 设置属性协议头 **/
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("Accept-Charset", "GB2312,GBK,utf-8;q=0.7,*;q=0.7");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("source", "source");

            dos = new DataOutputStream(conn.getOutputStream());
            // mParameter.put(RequestParam.OP_PARAM, mParam);
            dos.write(mParameter.toString().getBytes("UTF-8"));
            Logger.e("Request:", mParameter.toString());
            dos.flush();

            if (conn.getResponseCode() == 200)
            {
                Log.e("----------", "下载文件:OK,Length:" + conn.getContentLength());
                return conn.getContentLength();
            }
            else if (conn.getResponseCode() == 404)
            {
                Logger.i("警告", url + "文件不存在!");
            }
        }
        catch (SocketTimeoutException e)
        {
            Logger.i("-_-!", url + "超时...");
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            Logger.i("获取文件长度", url);
            try
            {
                if (dos != null)
                    dos.close();
            }
            catch (Exception ex)
            {
            }
        }
        return -1;
    }

    protected JSONObject getResponse(String url)
    {
        JSONObject json = null;
        try
        {
            InputStream in = getInputStream(url, Constants.TIMEOUT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            reader.close();
            in.close();
            json = (JSONObject) new JSONTokener(buffer.toString()).nextValue();
            Log.e("", "URL:" + url + ",获取到服务器数据:" + json);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return json;
    }
}
