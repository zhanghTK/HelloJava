package tk.zhangh.java.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 * Created by ZhangHao on 2017/3/24.
 */
public class DaemonDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("daemon is alive");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);  // 设置守护线程
        thread.start();
        TimeUnit.SECONDS.sleep(3);
    }
}
