package cc.joke.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cc.joke.application.GlobalApplication;

public class ImageUtil
{
    /**
     * 获取图片
     * 
     * @param id
     * @return
     */
    public static Bitmap getBitmapById(int id)
    {
        return BitmapFactory.decodeResource(GlobalApplication.context.getResources(), id);
    }

}
