package tk.zhangh.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * suspend/resume——挂起和恢复线程
 * 演示Suspend和Resume的错误用法
 * Suspend/Resume的缺陷：
 * 1. 挂起操作不会释放锁，如果加锁发生在挂起之前，没有其他线程可以访问到锁，直到恢复，非常容易造成死锁；
 * 2. 多线程执行的不确定性可能导致，恢复先于挂起执行，（如果不再次恢复）线程无法结束会持续处于RUNNABLE状态
 * Created by ZhangHao on 2017/3/24.
 */
public class BadSuspend {
    private static final Object lock = new Object();

    /**
     * thread1 获得锁，挂起 thread1
     *                                      thread2 等待锁
     *                                      thread2 恢复执行（resume先于suspend）
     * thread1 恢复执行，释放锁
     *                                      thread2 获得锁，挂起thread2
     */
    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread thread1 = new ChangeObjectThread("thread1");
        thread1.start();
        // 确保thread1一定执行
        TimeUnit.MILLISECONDS.sleep(100);
        ChangeObjectThread thread2 = new ChangeObjectThread("thread2");
        thread2.start();
        thread2.resume();
        thread1.resume();
        thread1.join();
        thread2.join();
    }

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("in " + getName());
                Thread.currentThread().suspend();
            }
        }
    }
}
