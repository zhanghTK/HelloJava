package tk.zhangh.java.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 可以先unpark，在park
 * 唤醒线程unpark，中断（不抛出异常，需要使用Thread.interrupted获得中断）
 * Created by ZhangHao on 2017/3/29.
 */
public class LockSupportDemo {
    private static final Object lock = new Object();
    private static ChangeObjectThread thread1 = new ChangeObjectThread("thread-1");
    private static ChangeObjectThread thread2 = new ChangeObjectThread("thread-2");

    public static void main(String[] args) throws InterruptedException {
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(100);
        thread2.start();
        LockSupport.unpark(thread1);
        LockSupport.unpark(thread2);
        thread1.join();
        thread2.join();
    }

    private static class ChangeObjectThread extends Thread {
        ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("in " + getName());
                LockSupport.park();
            }
        }
    }
}
