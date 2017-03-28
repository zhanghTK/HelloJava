package tk.zhangh.java.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock与Condition使用示例
 * Created by ZhangHao on 2017/3/28.
 */
public class ReenterLockCondition implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition reenterLockCondition = new ReenterLockCondition();
        Thread thread1 = new Thread(reenterLockCondition);
        thread1.start();
        TimeUnit.SECONDS.sleep(2);
        lock.lock();
        condition.signal();
        lock.unlock();
    }

    @Override
    public void run() {
        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
