package com.joke;

import java.util.List;

import com.joke.bean.JokeBean;
import com.joke.bean.NewsBean;
import com.joke.bean.SexyBean;

/**
 * 鍔熻兘鎻忚堪<br>
 * 銆堝姛鑳借缁嗘弿杩般�绗戣瘽瑙ｆ瀽鎺ュ彛
 * 
 * @author zhangfujin
 * @see [鐩稿叧绫�鏂规硶]锛堝彲閫夛級
 * @since [浜у搧/妯″潡鐗堟湰] 锛堝彲閫夛級
 */
public interface HandlerInterface
{

    /**
     * 鍔熻兘鎻忚堪: <br>
     * 銆堝姛鑳借缁嗘弿杩般�鑾峰彇绗戣瘽鍒楄〃
     * 
     * @param url
     * @return List<JokeBean>
     * @see [鐩稿叧绫�鏂规硶](鍙�)
     * @since [浜у搧/妯″潡鐗堟湰](鍙�)
     */
    public String getJokeList(String url, Integer userid);

    /**
     * 鍔熻兘鎻忚堪: <br>
     * 銆堝姛鑳借缁嗘弿杩般�
     * 
     * @param url
     * @return List<SextBean>
     * @see [鐩稿叧绫�鏂规硶](鍙�)
     * @since [浜у搧/妯″潡鐗堟湰](鍙�)
     */
    public String getSexyList(String url, String backup);

    /**
     * 鍔熻兘鎻忚堪: <br>
     * 銆堝姛鑳借缁嗘弿杩般�
     * 
     * @param url
     * @return List<NewsBean>
     * @see [鐩稿叧绫�鏂规硶](鍙�)
     * @since [浜у搧/妯″潡鐗堟湰](鍙�)
     */
    public String getNewList(String url, String backup);

}
