package cn.thinkjoy.startup.util.common.assist;


import cn.thinkjoy.startup.util.android.log.Log;

/**
 * Time Counter.
 *
 *         2013-12-11下午3:42:28
 */
public class TimeCounter {

    private static final String TAG = TimeCounter.class.getSimpleName();
    private long t;

    public TimeCounter() {
        start();
    }

    /**
     * Count start.
     */
    public long start() {
        t = System.currentTimeMillis();
        return t;
    }

    /**
     * Get duration and restart.
     */
    public long durationRestart() {
        long now = System.currentTimeMillis();
        long d = now - t;
        t = now;
        return d;
    }

    /**
     * Get duration.
     */
    public long duration() {
        return System.currentTimeMillis() - t;
    }

    /**
     * Print duration.
     */
    public void printDuration(String tag) {
        Log.i(TAG, tag + " :  " + duration());
    }

    /**
     * Print duration.
     */
    public void printDurationRestart(String tag) {
        Log.i(TAG, tag + " :  " + durationRestart());
    }
}