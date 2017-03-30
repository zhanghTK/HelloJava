package tk.zhangh.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch Demo
 * CountDownLatch用于一组线程执行到指定位置后，开始其他操作
 * 例如：所有准备做好后，火箭点火
 * Created by ZhangHao on 2017/3/29.
 */
public class CountDownLatchDemo implements Runnable {
    private static final CountDownLatch end = new CountDownLatch(10);
    private static final CountDownLatchDemo demo = new CountDownLatchDemo();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(demo);
        }
        end.await();
        System.out.println("Fire!");
        executorService.shutdown();
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10) * 1000);
            System.out.println("check complete");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
