package cn.thinkjoy.startup.http.threadManager;


import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.thinkjoy.startup.util.android.log.Log;


public class ThreadPoolExecutorWarp extends ThreadPoolExecutor {
    private ArrayList<Runnable> mRunningTaskArray = null;
    private byte[] mRunningTaskArrayLock;
    public ThreadPoolExecutorWarp(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        mRunningTaskArray = new ArrayList<Runnable>();
        mRunningTaskArrayLock = new byte[0];
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        synchronized (mRunningTaskArrayLock) {
            mRunningTaskArray.add(r);
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        synchronized (mRunningTaskArrayLock) {
            mRunningTaskArray.remove(r);
        }
    }

    public void printInfo () {
        StringBuffer sb = new StringBuffer();
        synchronized (mRunningTaskArrayLock) {
            for (Runnable runnable : mRunningTaskArray) {
                if (runnable != null) {
                    sb.append(runnable.toString()).append(", ");
                }
            }
        }
        if (sb.length() > 0) {
            Log.d ("ThreadPoolExecutorWarp", "cur thread:" + sb.toString());
        }
    }
}
