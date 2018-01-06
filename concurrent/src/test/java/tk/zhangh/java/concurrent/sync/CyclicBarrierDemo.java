package tk.zhangh.java.concurrent.sync;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier Demo
 * CyclicBarrier用于一组线程执行到指定位置后，再一起执行
 * 例如：所有运动员准备好后一起出发，所有运动员到达后一起颁奖
 * Created by ZhangHao on 2017/3/29.
 */
public class CyclicBarrierDemo {
    private static int THREAD_NUMS = 10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_NUMS);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUMS, new StatePrinter());
        for (int i = 0; i < THREAD_NUMS; i++) {
            service.submit(new Task(cyclicBarrier));
        }
        TimeUnit.SECONDS.sleep(5);
        service.shutdownNow();
    }

    public static class Task extends Thread {
        private final CyclicBarrier cyclicBarrier;

        Task(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.out.println("等待其他任务，但被中断");
                    break;
                }
                doWork();
            }
        }

        private void doWork() {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt() % 1000);
            } catch (InterruptedException e) {
                System.out.println("正在执行任务，但被中断");
            }
            System.out.println("任务完成");
        }
    }

    private static class StatePrinter implements Runnable {
        private boolean isFirst = true;

        @Override
        public void run() {
            if (isFirst) {
                System.out.println("所有任务将开始执行");
                isFirst = false;
            } else {
                System.out.println("所有任务执行完成，将开始下一轮任务执行");
            }
        }
    }
}
