package cc.joke.common;

import cc.joke.application.GlobalApplication;
import android.os.Environment;

public class Constants
{
    // host
    public static final String HOST = "http://114.215.170.91:3000/joke";

    /** 提交反馈地址 **/
    public static final String FEEDBACK_URL = HOST + "?op=FeedbackService";

    /** 程序包更新检测地址 **/
    public static final String APP_CHECK_URL = HOST + "?op=AppCheckService";

    /** 新闻消息地址 **/
    public static final String NEWS_URL = HOST + "?op=NewsService";

    /** 检测客户端版本更新 **/
    public static final String OP_UPVERSION = "upversion";

    /** 激活ID **/
    public static final String OP_ACTIVATION = "activation";

    /** 笑话列表ID **/
    public static final String OP_JOKE = "joke";

    /** 笑话详细ID **/
    public static final String OP_JOKE_INFO = "jokeinfo";

    /** 点赞ID **/
    public static final String OP_JOKE_VALUATE = "jokeevaluate";

    /** 获取评价ID **/
    public static final String OP_JOKE_TALK = "joketalk";

    /** 提交评价ID **/
    public static final String OP_JOKE_ADD_TALK = "jokeaddtalk";

    /** 登陆ID **/
    public static final String OP_LOGIN = "login";

    /** 获取发布的笑话ID **/
    public static final String OP_JOKE_FUSERS = "jokeofusers";

    /** 发布笑话ID **/
    public static final String OP_JOKE_ADD_JOKE = "jokeaddjoke";

    /** 头条ID **/
    public static final String OP_HEADLINES_LIST = "newlist";

    /** 两性ID **/
    public static final String OP_SEXY_LIST = "sexlist";

    /** 我的应用ID **/
    public static final String OP_APK_LIST = "apklist";

    /** 运营消息 **/
    public static final String OP_BUSINESS_MESSAGE = "message";

    /** 检测客户端版本更新 **/
    public static final String CHECK_CLIENT_URL = HOST + "?op=" + OP_UPVERSION;

    /** 激活URL **/
    public static final String OP_ACTIVATION_URL = HOST + "?op=" + OP_ACTIVATION;

    /** 笑话列表URL **/
    public static final String OP_JOKE_URL = HOST + "?op=" + OP_JOKE;

    /** 笑话详细URL **/
    public static final String OP_JOKE_INFO_URL = HOST + "?op=" + OP_JOKE_INFO;

    /** 点赞URL **/
    public static final String OP_JOKE_VALUATE_URL = HOST + "?op=" + OP_JOKE_VALUATE;

    /** 评价URL **/
    public static final String OP_JOKE_TALK_LIST_URL = HOST + "?op=" + OP_JOKE_TALK;

    /** 提交评价URL **/
    public static final String OP_JOKE_ADD_TALK_URL = HOST + "?op=" + OP_JOKE_ADD_TALK;

    /** 登陆URL **/
    public static final String OP_LOGIN_URL = HOST + "?op=" + OP_LOGIN;

    /** 获取发布的笑话URL **/
    public static final String OP_JOKE_FUSERS_URL = HOST + "?op=" + OP_JOKE_FUSERS;

    /** 添加发布的笑话URL **/
    public static final String OP_JOKE_ADD_JOKE_URL = HOST + "?op=" + OP_JOKE_ADD_JOKE;

    /** 上传笑话图片的url **/
    public static final String OP_JOKE_ADD_JOKE_IMAGE_URL = "http://114.215.170.91/jmg/upload/uploadImg.html";

    /** 获取头条list URL **/
    public static final String OP_HEADLINES_LIST_URL = HOST + "?op=" + OP_HEADLINES_LIST;

    /** 获取两性list URL **/
    public static final String OP_SEXY_LIST_URL = HOST + "?op=" + OP_SEXY_LIST;

    /** 我的应用列表 URL **/
    public static final String OP_APK_LIST_URL = HOST + "?op=" + OP_APK_LIST;

    public static final String OP_NEWS_URL = HOST + "?op=news";

    public static final String OP_BUSINESS_MESSAGE_URL = HOST + "?op=" + OP_BUSINESS_MESSAGE;

    /**
     * 每次获取的条数
     */
    public static final int OP_JOKE_PAGE_SIZE = 15;

    public static final int OP_HEADLINES_PAGE_SIZE = 15;

    public static final int OP_SEXY_PAGE_SIZE = 15;

    // 网络超时
    public static final int TIMEOUT = 10 * 1000;

    // SD卡存储根目录
    public static final String JOKE_ROOT = "joke";

    // 图片存入位置
    public static final String DOWNLOAD_IMAGE_DIR = Environment.getExternalStorageDirectory().getPath() + "/"
            + JOKE_ROOT + "/images";

    // apk存入位置
    public static final String DOWNLOAD_APK_DIR = Environment.getExternalStorageDirectory().getPath() + "/" + JOKE_ROOT
            + "/apk";

    /** 滑动像素距离切换 **/
    public static final int MOVE_SIZE = (int) (GlobalApplication.widthPixels * 0.4);

    public static final String APP_NETWORK_ACTION = "cc.joke.NETWORK_ACTION";

}
