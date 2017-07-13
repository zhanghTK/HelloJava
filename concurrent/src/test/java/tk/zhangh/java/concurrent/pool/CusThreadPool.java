package tk.zhangh.java.concurrent.pool;

import java.util.List;
import java.util.Vector;

/**
 * 自定义线程池
 * Created by ZhangHao on 2017/7/13.
 */
public class CusThreadPool {
    private static CusThreadPool instance = null;

    private List<CusWorker> idleThreads;  // 空闲的线程队列

    private int threadCounter;  // 已有的线程总数

    private boolean isShutDown = false;

    private CusThreadPool() {
        this.idleThreads = new Vector<>(5);
        threadCounter = 0;
    }

    public synchronized static CusThreadPool getInstance() {
        if (instance == null) {
            instance = new CusThreadPool();
        }
        return instance;
    }

    public int getCreatedThreadsCount() {
        return threadCounter;
    }

    protected synchronized void repool(CusWorker repoolingThread) {
        if (!isShutDown) {
            idleThreads.add(repoolingThread);
        } else {
            repoolingThread.shutDown();
        }
    }

    public synchronized void shutDown() {
        isShutDown = true;
        for (CusWorker idleThread : idleThreads) {
            idleThread.shutDown();
        }
    }

    public synchronized void start(Runnable target) {
        if (idleThreads.size() > 0) {
            // 有空闲线程
            int lastIndex = idleThreads.size() - 1;
            CusWorker thread = idleThreads.get(lastIndex);
            idleThreads.remove(lastIndex);
            thread.setTarget(target);
        } else {
            // 没有空闲线程
            threadCounter++;
            CusWorker thread = new CusWorker(target, "PThread #" + threadCounter, this);
            thread.start();
        }
    }
}
