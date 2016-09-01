package cn.thinkjoy.startup.http.threadManager;

/**
 *
 */
public abstract class ThreadPoolTask<T> implements Runnable {
    protected String mName;
    protected T mParameter;

    public abstract void doTask(T parameter);

    public ThreadPoolTask(String name) {
        mName = name;
    }

    public String getName() {
        return (mName == null ? "" : mName);
    }

    public void setParameter(T parameter) {
        mParameter = parameter;
    }

    @Override
    public void run() {
        doTask(mParameter);
    }

    @Override
    public String toString() {
        return getName();
    }
}
