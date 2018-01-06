package tk.zhangh.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程睡眠Demo
 * 查看线程具体状态
 * 可以看到sleeping-thread处于TIMED_WAITING状态
 * 处于sleep的线程的是TIMED_WAITING状态的
 * Created by ZhangHao on 2017/3/27.
 */
public class SleepDemo {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                while (true) {
                    System.out.print("");
                }
            }
        });
        thread.setName("sleeping-thread");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println("isInterrupted:" + thread.isInterrupted());
        System.out.println("isAlive:" + thread.isAlive());
    }
}
