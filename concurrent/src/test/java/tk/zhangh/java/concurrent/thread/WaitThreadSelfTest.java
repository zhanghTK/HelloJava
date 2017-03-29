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
        final Thread thread = new Thread(runnable);
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        synchronized (runnable) {
            runnable.notify();
        }
    }
}
