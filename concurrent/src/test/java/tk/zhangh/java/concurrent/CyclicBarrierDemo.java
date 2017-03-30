package tk.zhangh.java.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier Demo
 * CyclicBarrier用于一组线程执行到指定位置后，再一起执行
 * 例如：所有运动员准备好后一起出发，所有运动员到达后一起颁奖
 * Created by ZhangHao on 2017/3/29.
 */
public class CyclicBarrierDemo {
    private static int n = 10;

    public static void main(String[] args) {
        Thread[] tasks = new Thread[n];
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n, new StatePrinter(false, n));
        for (int i = 0; i < n; i++) {
            tasks[i] = new Thread(new Task(cyclicBarrier));
            tasks[i].start();
            if (i == 5) {
                tasks[0].interrupt();
            }
        }
    }

    public static class Task implements Runnable {
        private final CyclicBarrier cyclicBarrier;

        Task(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                doWork();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt() % 10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务完成");
        }
    }

    private static class StatePrinter implements Runnable {
        private boolean isFinished;
        private int n;

        StatePrinter(boolean isFinished, int n) {
            this.isFinished = isFinished;
            this.n = n;
        }

        @Override
        public void run() {
            if (isFinished) {
                System.out.println(n + "个任务完成");
            } else {
                System.out.println(n + "个任务将开始执行");
                isFinished = true;
            }
        }
    }
}
