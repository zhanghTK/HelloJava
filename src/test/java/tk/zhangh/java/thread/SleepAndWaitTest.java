package tk.zhangh.java.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * wait/sleep区别：
 * 1. Thread.sleep(),object.wait()
 * 2. sleep不释放锁，wait释放锁
 * Created by ZhangHao on 2016/9/13.
 */
public class SleepAndWaitTest {
    private static volatile Object lock = new Object();

    @Test
    public void testSleepAndWait() throws Exception {
        Thread waitThread = new Thread(new WaitThread());
        waitThread.start();
        TimeUnit.MILLISECONDS.sleep(5000);
        Thread sleepThread = new Thread(new SleepThread());
        sleepThread.start();
        waitThread.join();
        sleepThread.join();
    }

    private static class WaitThread extends Thread {
        @Override
        public void run(){
            setName("wait-thread");
            synchronized (lock) {
                System.out.println(getName() + " enter and will waiting");
                try {
                    // 阻塞，释放锁
                    lock.wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(getName() + " go die");
            }
        }
    }

    private static class SleepThread extends Thread {
        @Override
        public void run(){
            setName("sleep-thread");
            synchronized (lock) {
                System.out.println(getName() + " enter and will sleep");
                try {
                    // 不释放锁
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(getName() + " go die");
                lock.notify();
            }
        }
    }

    /**
     * 简单的wait,notify简单例子
     * 输出1,2,1,2,1,2，...
     * @throws Exception
     */
    @Test
    public void testWaitAndNotify() throws Exception {
        class OutputThread implements Runnable {
            private final int num;
            private Object lock;

            public OutputThread(int num, Object lock) {
                this.num = num;
                this.lock = lock;
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (lock) {
                            lock.notifyAll();
                            System.out.println(num);
                            lock.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        final Object lock = new Object();
        Thread thread1 = new Thread(new OutputThread(1, lock));
        Thread thread2 = new Thread(new OutputThread(2, lock));
        thread1.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(5);
    }
}
