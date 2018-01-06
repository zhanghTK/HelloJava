package tk.zhangh.java.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock多次加锁
 * 可以多次加锁，但需要手动释放锁
 * Created by ZhangHao on 2017/3/28.
 */
public class ReenterLock2 implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ReenterLock2 reenterLock2 = new ReenterLock2();
        Thread thread1 = new Thread(reenterLock2);
        Thread thread2 = new Thread(reenterLock2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            lock.lock();
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }
}
