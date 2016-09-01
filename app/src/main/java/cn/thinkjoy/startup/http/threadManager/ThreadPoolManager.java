package cn.thinkjoy.startup.http.threadManager;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.thinkjoy.startup.util.android.log.Log;


/**
 * Created by rick on 2015/10/19.
 * 线程管理类，所有线程资源的申请都应该在这里
 */
public class ThreadPoolManager {
    // 是否打印线程情况
    private static final boolean OPTIMIZE_DEBUG = false;

    // 线程池的分类
    /**耗时任务**/
    private static final int LONG_TASK_POOL = 0;
    /**短小任务，一般使用此类型**/
    private static final int SHORT_TASK_POOL = 1;
    /**图片任务**/
    private static final int PIC_TASK_POOL = 2;
    private static final int POOL_COUNT = 3;

    private volatile static ThreadPoolManager sInstance = null;

    // 不同线程池的容器
    private ThreadPoolExecutorWarp[] mPoolArray = null;
    private ExecutorService mDebugThreadPool = null;

    public static ThreadPoolManager getInstance() {
        if (sInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadPoolManager();
                }
            }
        }
        return sInstance;
    }

    private ThreadPoolManager() {
        mPoolArray = new ThreadPoolExecutorWarp[POOL_COUNT];
        // 耗时任务
        mPoolArray[LONG_TASK_POOL] = new ThreadPoolExecutorWarp(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());  // 等价于Executors.newCachedThreadPool();
        // 短小任务
        mPoolArray[SHORT_TASK_POOL] = new ThreadPoolExecutorWarp(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        // 图片处理任务
        mPoolArray[PIC_TASK_POOL] = new ThreadPoolExecutorWarp(0, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        // 调优时候查看线程
        if (OPTIMIZE_DEBUG) {
            mDebugThreadPool = Executors.newSingleThreadExecutor();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for(ThreadPoolExecutorWarp pool : mPoolArray) {
                        pool.printInfo();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mDebugThreadPool.execute(this);
                }
            };
            mDebugThreadPool.execute(runnable);
        }
    }

    /**
     * 提交耗时任务
     */
    public synchronized void postLongTask(ThreadPoolTask task) {
        ThreadPoolExecutor pool = mPoolArray[LONG_TASK_POOL];
        if (! pool.isShutdown()) {
            pool.execute(task);
        }
    }

    /**
     * 提交短小的任务到后台处理
     */
    public synchronized void postShortTask(ThreadPoolTask task) {
        ThreadPoolExecutor pool = mPoolArray[SHORT_TASK_POOL];
        if (! pool.isShutdown()) {
            pool.execute(task);
        }
    }

    /**
     * 提交图片的任务到后台处理
     */
    public synchronized void postPicTask(ThreadPoolTask task) {
        ThreadPoolExecutor pool = mPoolArray[PIC_TASK_POOL];
        if (! pool.isShutdown()) {
            pool.execute(task);
        }
    }

    /**
     * 获取处理图片的线程池，暴露出来用于给imageloader之类的开源代码使用
     * @return 图片线程池
     */
    public ThreadPoolExecutor getPicPool() {
        return mPoolArray[PIC_TASK_POOL];
    }

    /**
     * 关闭整个线程池
     */
    public synchronized void exit() {
        for (ThreadPoolExecutor pool : mPoolArray) {
            if (!pool.isShutdown()) {
                pool.shutdownNow();
            }
        }
        mPoolArray = null;
    }

    /**
     * 用于打印线程池现在的情况
     */
    public void printPoolInfo() {
        String msg = String.format(Locale.US, "Long/Short/Pic  %d/%d/%d.  Long largest=%d, Short/Pic wait=%d/%d",
                mPoolArray[LONG_TASK_POOL].getActiveCount(), mPoolArray[SHORT_TASK_POOL].getActiveCount(),  mPoolArray[PIC_TASK_POOL].getActiveCount(),
                mPoolArray[LONG_TASK_POOL].getLargestPoolSize(), mPoolArray[SHORT_TASK_POOL].getQueue().size(),  mPoolArray[PIC_TASK_POOL].getQueue().size());
        Log.d("ThreadPoolManager", msg);
    }
}
