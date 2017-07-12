package tk.zhangh.java.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 根据时间等待锁
 * Created by ZhangHao on 2017/3/28.
 */
public class ReentrantLockTimer implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockTimer timeLock = new ReentrantLockTimer();
        Thread thread1 = new Thread(timeLock);
        Thread thread2 = new Thread(timeLock);
        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                TimeUnit.SECONDS.sleep(6);
                System.out.println("end");
            } else {
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
