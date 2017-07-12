package tk.zhangh.java.concurrent.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger Demo
 * 并发场景下使用AtomicInteger自增
 * Created by ZhangHao on 2017/3/27.
 */
public class AtomicIntegerDemo {
    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Adder());
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assert 100000 == count.get();
        System.out.println(count);
    }

    private static class Adder implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                count.incrementAndGet();
            }
        }
    }
}
