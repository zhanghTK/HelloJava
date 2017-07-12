package tk.zhangh.java.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock基本用法
 * ReentrantLock对synchronized改改进：
 * 1. lockInterruptibly:获得锁，但优先响应中断
 * 2. tryLock：尝试获得锁，成功返回true，失败立即返false，不等待
 * 3. tryLock(long time, TimeUnit unit)：在给定时间内尝试获得锁
 * 4. unlock：释放锁
 * 5. ReentrantLock(boolean fair)：确保同步线程之间的访问公平
 *
 * Created by ZhangHao on 2017/3/28.
 */
public class ReenterLockDemo implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ReenterLockDemo reenterLockDemo = new ReenterLockDemo();
        Thread thread1 = new Thread(reenterLockDemo);
        Thread thread2 = new Thread(reenterLockDemo);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
    }
}
