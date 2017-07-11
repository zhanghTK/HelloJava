package tk.zhangh.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * thread使用wait方法暂停自己
 * Created by ZhangHao on 2017/3/29.
 */
public class WaitThreadSelfTest {
    public static void main(String[] args) throws InterruptedException {
        class TestThread implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(i);
                    // 当执行到第501次时，暂停自己
                    if (i == 500) {
                        try {
                            synchronized (this) {
                                wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Runnable runnable = new TestThread();
        final Thread thread = new Thread(new TestThread());
        thread.start();
        // 主线程等待3秒，让创建的线程执行
        TimeUnit.SECONDS.sleep(3);
        // 线程自己恢复自己
        synchronized (runnable) {
            runnable.notify();
        }
    }
}
