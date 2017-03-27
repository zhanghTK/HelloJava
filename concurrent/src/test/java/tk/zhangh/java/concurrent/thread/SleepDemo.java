package tk.zhangh.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程睡眠Demo
 * 查看线程具体状态
 * 可以看到sleeping-thread处于TIMED_WAITING状态
 * Created by ZhangHao on 2017/3/27.
 */
public class SleepDemo {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setName("sleeping-thread");
        thread.start();
        thread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(thread.isInterrupted());
        System.out.println(thread.isAlive());
    }
}
