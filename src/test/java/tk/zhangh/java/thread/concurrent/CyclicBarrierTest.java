package tk.zhangh.java.thread.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier Demo
 * 每个任务开始后随即休眠一段时间，等待所有任务都休眠结束后，打印结束
 * CyclicBarrier：
 *   1. 多个线程互相等待，满足条件后继续执行
 *   2. 可重复使用
 *   3. 构造器可传入一个Runnable，用于满足条件后执行的一个操作（由最后一个进入屏障的任务执行）
 * Created by ZhangHao on 2016/9/12.
 */
public class CyclicBarrierTest {
    private static final int THREAD_NUMBER = 5;

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUMBER, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有任务执行完毕");
            }
        });
//        不设置满足条件后的操作
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            executorService.execute(new Task(cyclicBarrier, "任务" + i, random.nextInt(1000)));
        }
    }

    private static class Task implements Runnable {
        private CyclicBarrier barrier;
        private String name;
        private int random;

        public Task(CyclicBarrier barrier, String name, int random) {
            this.barrier = barrier;
            this.name = name;
            this.random = random;
        }

        @Override
        public void run() {
            work();
        }

        private void work() {
            try {
                TimeUnit.MILLISECONDS.sleep(random);
                System.out.println(name + "完成，耗时：" + random + "等待其他任务");
                barrier.await();
                System.out.println(name + "结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
