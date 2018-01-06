package tk.zhangh.java.concurrent.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量使用
 *
 * 使用线程池访问共享信号量
 * Created by ZhangHao on 2017/3/28.
 */
public class SemaphoreDemo2 implements Runnable {
    private final Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemaphoreDemo2 semaphoreDemo = new SemaphoreDemo2();
        for (int i = 0; i < 20; i++) {
            executorService.submit(semaphoreDemo);
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(2);
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getId() + ": done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(2);
        }
    }
}
