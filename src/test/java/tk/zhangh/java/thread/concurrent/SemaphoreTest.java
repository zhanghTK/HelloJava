package tk.zhangh.java.thread.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore信号量使用Demo
 * Created by ZhangHao on 2016/9/12.
 */
public class SemaphoreTest {
    private static final int WORKER_NUMBER = 10;

    public static void main(String[] args) throws Exception {
        // 初始化3个资源，公平策略
        Semaphore semaphore = new Semaphore(3, true);
        for (int i = 1; i <= WORKER_NUMBER; i++) {
            Thread t = new Thread(new Worker(String.valueOf(i), semaphore));
            t.start();
        }
    }

    static class Worker implements Runnable {
        private String name;
        private Semaphore resources;
        public Worker(String name, Semaphore resources) {
            this.name = name;
            this.resources = resources;
        }
        public void run() {
            System.out.println(name + "申请资源，当前可用资源数：" + resources.availablePermits());
            try {
                // 获取资源，当前无资源可用则阻塞
                resources.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 使用资源
            System.out.println(name + "申请到资源，开始使用资源......");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放资源
            System.out.println(name + "资源使用完毕，释放资源");
            resources.release();
        }
    }
}
