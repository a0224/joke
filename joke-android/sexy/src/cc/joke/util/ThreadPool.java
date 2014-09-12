package cc.joke.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * 
 * @author wanghao
 */
public class ThreadPool
{

    private static final String CLASSTAG = ThreadPool.class.getSimpleName();

    private static final int POOL_SIZE = 10;

    private static ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

    /**
     * 不允许实例化
     */
    private ThreadPool()
    {
    }

    /**
     * 加入线程池
     */
    public static void add(Runnable runnable)
    {
        try
        {
            executorService.submit(runnable);
        }
        catch (Exception e)
        {
        }

    }

    public static void shutdown()
    {
        executorService.shutdownNow();
    }
}
