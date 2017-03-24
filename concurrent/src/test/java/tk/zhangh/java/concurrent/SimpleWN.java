package tk.zhangh.java.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * wait/notifyAll
 * Created by ZhangHao on 2017/3/25.
 */
public class SimpleWN {
    private static final Object lock = new Object();

    private static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(System.currentTimeMillis() + " T1 start!");
                try {
                    System.out.println(System.currentTimeMillis() + " T1 wait for object");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + " T1 end!");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(System.currentTimeMillis() + " T2 start!");
                System.out.println(System.currentTimeMillis() + " T2 wait for object");
                lock.notifyAll();
                System.out.println(System.currentTimeMillis() + " T2 end!");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new T1();
        Thread thread1_1 = new T1();
        Thread thread2 = new T2();
        thread1.start();
        thread1_1.start();
        thread2.start();
    }
}
