package tk.zhangh.java.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock与Condition使用示例
 * Condition类似与object.wait和object.notify
 * Created by ZhangHao on 2017/3/28.
 */
public class ReenterLockCondition {
    private static AtomicInteger count = new AtomicInteger(0);
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runner(), "t1");
        Thread thread2 = new Thread(new Runner(), "t2");
        thread1.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(5);
    }

    static class Runner implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    condition.signal();
                    System.out.println(this.hashCode() + " add 1:" + count.addAndGet(1));
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
