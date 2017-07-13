package tk.zhangh.java.concurrent.pool;

/**
 * 自定义线程池持有线程
 * Created by ZhangHao on 2017/7/13.
 */
public class CusWorker extends Thread {
    private CusThreadPool pool;

    private Runnable target;

    private boolean isShutDown = false;  // 是否关闭

    private boolean isIdle = false;  // 是否空闲

    public CusWorker(Runnable target, String name, CusThreadPool pool) {
        super(name);
        this.pool = pool;
        this.target = target;
    }

    @Override
    public void run() {
        while (!isShutDown) {
            isIdle = false;
            if (target != null) {
                target.run();
            }
            isIdle = true;
            pool.repool(this);
            // 使用本线程实例作为锁，暂停本宪政
            synchronized (this) {
                try {
                    // 线程空闲，等待新的任务到来
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isIdle = false;
        }
    }

    public synchronized void setTarget(Runnable target) {
        this.target = target;
        // 设置任务后，通知run方法开始执行
        notifyAll();
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
    }

    public synchronized void shutDown() {
        isShutDown = true;
        notifyAll();
    }
}
