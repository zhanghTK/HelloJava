package tk.zhangh.java.thread.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * wait/sleep区别：
 * 1. Thread.sleep(),object.wait()
 * 2. sleep不释放锁，wait释放锁
 * Created by ZhangHao on 2016/9/13.
 */
public class SleepAndWaitTest {
    private static volatile Object lock = new Object();

    public static void main(String[] args) throws Exception{
        new Thread(new WaitThread()).start();
        TimeUnit.MILLISECONDS.sleep(5000);
        new Thread(new SleepThread()).start();
    }

    private static class WaitThread extends Thread {
        @Override
        public void run(){
            synchronized (lock) {
                System.out.println(getName() + " enter and will waiting...");
                try {
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
            synchronized (lock) {
                System.out.println(getName() + " enter and will sleep");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(getName() + " go die");
                lock.notify();
            }
        }
    }
}
