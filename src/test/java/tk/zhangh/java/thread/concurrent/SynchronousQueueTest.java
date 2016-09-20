package tk.zhangh.java.thread.concurrent;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue Demo
 * 最多只有一个产品的生产者/消费者模型
 *
 * offer():向queue里添加一个element后立即返回
 * put()：向queue里添加一个element以后就一直wait直到有其他thread进来把这个element取走
 *
 * Created by ZhangHao on 2016/9/20.
 */
public class SynchronousQueueTest {
    static volatile SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);
    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; ++i) {
            new Customer().start();
        }
        for (int i = 0; i < 3; ++i) {
            new Product().start();
        }
        TimeUnit.SECONDS.sleep(5);
        System.exit(0);
    }

    static class Customer extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Thread Id:" + getId() + " 消费了一个产品:" + queue.take());
                    System.out.println("------------------------------------------");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Product extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    int rand = random.nextInt(1000);
                    TimeUnit.MILLISECONDS.sleep(rand);
                    System.out.println("Thread Id:" + getId() + "  生产了一个产品：" + rand);
                    System.out.println("Thread Id:" + getId() + " 加入SynchronousQueue");
                    queue.put(rand);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
