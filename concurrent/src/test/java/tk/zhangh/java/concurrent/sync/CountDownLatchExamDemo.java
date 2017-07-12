package tk.zhangh.java.concurrent.sync;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用CountDownLatch模拟单场考试
 * Created by ZhangHao on 17/4/9.
 */
public class CountDownLatchExamDemo {

    private static final int NUM = 10;

    private static final CountDownLatch end = new CountDownLatch(NUM);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(NUM);
        System.out.println("考试开始");
        for (int i = 0; i < NUM; i++) {
            service.submit(new Person("thread-" + i));
        }
        end.await();
        System.out.println("考试结束");
        service.shutdown();
    }

    private static class Person extends Thread {
        Person(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10) * 1000);
                System.out.println(getName() + "交卷");
                end.countDown();
                System.out.println(getName() + "离开");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
