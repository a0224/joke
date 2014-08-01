
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JavaTest
{

    public static void main(String[] args)
    {

        getJokeList();

//         getNewsList();

        // getSexyList();
    }

    private static void getJokeList()
    {
        try
        {

            String className = "com.joke.Handler";
            String methodName = "getJokeList";
            String dateUrl = "http://ic.snssdk.com/2/essay/v3/recent/?count=5";

            URL url = new URL("file:///E:\\joke1.0\\jar\\JokeHandler\\JokeHandler_fat.jar");
            URLClassLoader loader = new URLClassLoader(new URL[] {url});
            Class cl = loader.loadClass(className);
            Object ins = cl.newInstance();

            Method m = cl.getDeclaredMethod(methodName, String.class, Integer.class);
            m.setAccessible(true);

            String jsonString = (String) m.invoke(ins, dateUrl, 1);

            System.out.println(jsonString);

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

    }

    private static void getNewsList()
    {
        try
        {

            String className = "com.joke.Handler";
            String methodName = "getNewList";
            String dateUrl = "http://api.baiyue.baidu.com/sn/api/focusnews";

            URL url = new URL("file:///E:\\joke1.0\\jar\\JokeHandler\\JokeHandler_fat.jar");

            URLClassLoader loader = new URLClassLoader(new URL[] {url});
            Class cl = loader.loadClass(className);
            Object ins = cl.newInstance();

            Method m = cl.getDeclaredMethod(methodName, String.class, String.class);
            m.setAccessible(true);

            String jsonString = (String) m.invoke(ins, dateUrl, "");

            System.out.println(jsonString);

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

    }

    private static void getSexyList()
    {
        try
        {

            String className = "com.joke.Handler";
            String methodName = "getSexyList";
            String dateUrl = "http://ic.snssdk.com/2/article/v15/stream/?detail=1&image=1&category=bisexual&count=4000&min_behot_time=1401957421&loc_mode=0&lac=6338&cid=64593&iid=293538879&device_id=2438698769&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=344&device_platform=android&device_type=HUAWEI%20G610-U00&os_api=17&os_version=4.2.1&uuid=860623023321506&openudid=b75475664a9929e9";

            URL url = new URL("file:///E:\\joke1.0\\jar\\JokeHandler\\JokeHandler_fat.jar");

            URLClassLoader loader = new URLClassLoader(new URL[] {url});
            Class cl = loader.loadClass(className);
            Object ins = cl.newInstance();

            Method m = cl.getDeclaredMethod(methodName, String.class, String.class);
            m.setAccessible(true);

            String jsonString = (String) m.invoke(ins, dateUrl, "");

            System.out.println(jsonString);

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

    }

}
