package cc.joke.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.os.Environment;
import android.util.Log;
import cc.joke.util.DateUtil;

public class DsLog
{
    public static void i(String tag, String str)
    {
        if (true)
        {
            Log.i("==========>>>" + tag + "<<<==========", str);
        }
    }

    public static void e(String tag, String str)
    {
        if (true)
        {
            Log.e("==========>>>" + tag + "<<<==========", str);
        }
    }

    public static void error(Exception e)
    {
        Log.e("", "", e);
        StringWriter sw = null;
        PrintWriter pw = null;
        PrintWriter p = null;
        try
        {
            sw = new StringWriter();
            pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            String str = DateUtil.getNowDateTimeStr() + " " + sw.toString();
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/droxy_error.txt");
            p = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            p.write(str);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (p != null)
            {
                p.close();
            }
            if (pw != null)
            {
                pw.close();
            }
            if (sw != null)
            {
                try
                {
                    sw.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
}
