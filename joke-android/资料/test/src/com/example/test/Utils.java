package com.example.test;
import java.util.Random;

public class Utils
{
    
    
    public static final String macChar = "01234567890123456789ABCDEF";

    public static final String imeiChar = "0123456789";

    public static final String[] imsiHeader = new String[] {"46000", "46002", "46001", "46003"};
    
    public static final int[][] display = new int[][] { {240, 320}, {320, 480}, {460, 640}, {480, 800}, {480, 854},
            {540, 960}, {640, 960}, {1280, 720}, {1280, 800}};


    /**
     * ��ȡmac
     *  WifiManager.getMacAddress();
     *     
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String getMacAddress()
    {
        final int length = 6;
        StringBuffer sb = new StringBuffer(generateString(2));
        Random random = new Random();
        for (int i = 0; i < length - 1; i++)
        {
            String str = generateString(2);
            sb.append(":" + str);
        }
        return sb.toString();
    }

    /**
     * ��ȡimei��
     * TelephonyManager.getDeviceId();
     * 
     * @param length
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String getDeviceId()
    {
        final int length = 15;
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            sb.append(imeiChar.charAt(random.nextInt(imeiChar.length())));
        }
        return sb.toString();
    }

    
    /**
     * ��ȡimsi��
     * TelephonyManager.getSubscriberId();
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String getSubscriberId()
    {
        final int length = 15;
        Random random = new Random();
        String header = imsiHeader[random.nextInt(imsiHeader.length)];
        StringBuffer sb = new StringBuffer(header);
        sb.append(generateString(length - header.length()));

        return sb.toString();
    }
    
    /**
     *��ȡ�ֱ���
     * WindowManager.getDefaultDisplay().getMetrics(dm);
     * @see [�ࡢ��#��������#��Ա]
     */
    
    public static int getMetricsWidth(){
        
        Random random = new Random();
        int[] dis = display[random.nextInt(display.length)];
        return dis[0];
    }
    
    public static int getMetricsHeight(){
        
        Random random = new Random();
        int[] dis = display[random.nextInt(display.length)];
        return dis[1];
    }


    /**
     * ����ַ���
     * 
     * @param length
     * @return
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String generateString(int length)
    {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            sb.append(macChar.charAt(random.nextInt(macChar.length())));
        }
        return sb.toString();
    }
}
