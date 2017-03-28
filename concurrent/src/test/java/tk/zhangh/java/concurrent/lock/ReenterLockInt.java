package tk.zhangh.java.concurrent.lock;

import tk.zhangh.java.concurrent.DeadlockChecker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可中断的加锁
 * Created by ZhangHao on 2017/3/28.
 */
public class ReenterLockInt implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    private int lock;

    private ReenterLockInt(int lock) {
        this.lock = lock;
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockInt r1 = new ReenterLockInt(1);
        ReenterLockInt r2 = new ReenterLockInt(2);
        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r2);
        thread1.start();
        thread2.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        DeadlockChecker.check();
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();  // 可中断的加锁
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + "：线程退出");
        }
    }
}
