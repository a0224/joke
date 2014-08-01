package com.joke.util;

import java.sql.Timestamp;
import java.util.Date;

public class Utils
{
    public static Timestamp GetNowDateTime()
    {
        Date timeDate = new Date();
        return new Timestamp(timeDate.getTime());
    }
}
