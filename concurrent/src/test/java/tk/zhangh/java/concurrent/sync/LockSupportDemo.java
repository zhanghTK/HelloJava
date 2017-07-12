package tk.zhangh.java.concurrent.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport提供线程阻塞原语
 * park：暂停线程
 * unpark：继续执行指定的线程
 * 相比suspend/resume不容易引起线程冻结（可以先unpark，再park，不会引用死锁）
 * 内部实现类似于信号量，park申请资源，unpark归还资源
 *
 * 唤醒线程的两种操作：
 * 1. unpark
 * 2. 中断（注意：不抛出中断异常，需要使用Thread.interrupted获得中断）
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
