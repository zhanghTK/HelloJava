package tk.zhangh.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * 演示Suspend和Resume的错误用法
 * Created by ZhangHao on 2017/3/24.
 */
public class BadSuspend {
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread thread1 = new ChangeObjectThread("thread1");
        thread1.start();
        // 确保thread1一定执行
        TimeUnit.MILLISECONDS.sleep(100);
        ChangeObjectThread thread2 = new ChangeObjectThread("thread2");
        thread2.start();
        thread1.resume();
        thread2.resume();
        thread1.join();
        thread2.join();
    }

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("in " + getName());
                Thread.currentThread().suspend();
            }
        }
    }
}
