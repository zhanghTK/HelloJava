package tk.zhangh.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * wait/notifyAll：
 * 1. 通过一个（monitor）对象让线程停下来或是动起来
 * 2. 操作之前一定要获得对象锁（monitor的锁）
 * 3. wait执行后会释放持有的锁，直到其他线程notify monitor时重新持有锁
 * Created by ZhangHao on 2017/3/25.
 */
public class SimpleWN {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread thread1 = new T1();
        Thread thread1_1 = new T1();
        Thread thread2 = new T2();
        thread1.start();
        thread1_1.start();
        thread2.start();
    }

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
                // 延迟2秒，2秒后释放锁T1线程才能再次获得锁，被激活
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
