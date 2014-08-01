package test;

import java.io.File;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.joke.utils.GetDate;

/**
 * 百度云存储接口
 * 
 * @author haowangh
 * @version [版本号, 2014-6-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Baidu
{
    private static String TAG = "Baidu";

    private static String host = "bcs.duapp.com";

    private static String accessKey = "IosjtEqLu0Qh7GITUgixyicH";

    private static String secretKey = "0sZzt266eVS1u5F9vtVC9IAXqQV6CYGA";

    public static String bucket = "joke-bucket";

    private static Baidu mBaidu;

    BaiduBCS mBaiduBCS;

    private Baidu()
    {

    }

    public static Baidu getInstance()
    {
        if (mBaidu == null)
        {
            mBaidu = new Baidu();
        }

        return mBaidu;
    }

    /**
     * 上传图片
     * 
     * @param file
     * @see [类、类#方法、类#成员]
     */
    public String uploadImage(File file)
    {
        BaiduBCS baiduBCS = initBaiduBCS();

        String object = "/image/" + file.getName();
        System.out.println("Publish --- object:"+object);
        String url = null;
        try
        {
            putObjectByFile(baiduBCS, object, file);
            url = generateUrl(baiduBCS, object);
        }
        catch (Exception e)
        {
            url = null;
            System.out.println( "Publish --- e:"+e);
        }

        return url;
    }

    /**
     * 下载图片
     * 
     * @see [类、类#方法、类#成员]
     */
    public void downloadImage(String url)
    {
        BaiduBCS baiduBCS = initBaiduBCS();

        String fileName = GetDate.getFileNameByUrl(url);

        String object = url;

        File destFile = new File("" + "/" + fileName);

        getObjectWithDestFile(baiduBCS, object, destFile);
    }

    private BaiduBCS initBaiduBCS()
    {
        if (mBaiduBCS == null)
        {
            BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
            mBaiduBCS = new BaiduBCS(credentials, host);
            mBaiduBCS.setDefaultEncoding("UTF-8");
        }

        return mBaiduBCS;
    }

    public static String generateUrl(BaiduBCS baiduBCS, String object)
    {
        GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(HttpMethodName.GET, bucket, object);
        generateUrlRequest.setBcsSignCondition(new BCSSignCondition());

        return baiduBCS.generateUrl(generateUrlRequest);
    }

    /**
     * 上传
     * 
     * @param baiduBCS
     * @param object
     * @param file
     * @see [类、类#方法、类#成员]
     */
    private void putObjectByFile(BaiduBCS baiduBCS, String object, File file)
    {

        PutObjectRequest request = new PutObjectRequest(bucket, object, file);
        ObjectMetadata metadata = new ObjectMetadata();
        request.setMetadata(metadata);
        BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
        ObjectMetadata objectMetadata = response.getResult();
        
        // 设置权限
        baiduBCS.putObjectPolicy(bucket, object, X_BS_ACL.PublicReadWrite);
    }

    /**
     * 下载
     * 
     * @param baiduBCS
     * @param object
     * @param destFile
     * @see [类、类#方法、类#成员]
     */
    private static void getObjectWithDestFile(BaiduBCS baiduBCS, String object, File destFile)
    {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);

        baiduBCS.getObject(getObjectRequest, destFile);
    }

}
