package cc.joke.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{

    public static String getNowDateTimeStr()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(new Date());
    }

    public static String getDateStrByss(long ss)
    {
        return null;
    }

    public static String format(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static Date parse(String date, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try
        {
            return sdf.parse(date);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取2个日期时间的差距天数
     * 
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    public static int getDiffDays(Date start, Date end)
    {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(start);
        c2.setTime(end);
        int counter = 0;
        while (true)
        {
            if (c1.after(c2))
            {
                break;
            }
            if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE)
                    && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
            {
                break;
            }
            counter++;
            c1.add(Calendar.DATE, 1);
        }
        return counter;
    }

}
