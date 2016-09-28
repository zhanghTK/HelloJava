package tk.zhangh.java.thread.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch Demo
 * CountDownLatch任务等待其他多个任务执行完成之后执行
 * 起始门：controller开始后module再开始
 * 结束门：所有module都结束后controller再结束
 * Created by ZhangHao on 2016/9/28.
 */
public class CountDownLatchTest {
    private static final int SIZE = 10;  // module个数

    public static void main(String[] args) {
        CountDownLatch endLatch = new CountDownLatch(SIZE);  // 起始门
        CountDownLatch startLatch = new CountDownLatch(1);  // 结束门
        Random random = new Random();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Controller controller = new Controller(startLatch, endLatch);
        executorService.execute(controller);
        for (int i = 0; i < SIZE; i++) {
            executorService.execute(new Module(startLatch, endLatch, "模块" + i, random.nextInt(1000)));
        }
        executorService.shutdown();
    }

    /**
     * 子模块任务
     */
    private static class Module implements Runnable {
        private CountDownLatch controllerLatch;
        private CountDownLatch moduleLatch;
        private String name;
        private int random;

        public Module(CountDownLatch startLatch, CountDownLatch endLatch, String name, int random) {
            this.controllerLatch = startLatch;
            this.moduleLatch = endLatch;
            this.name = name;
            this.random = random;
        }

        @Override
        public void run() {
            try {
                controllerLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            work();
            moduleLatch.countDown();
        }

        private void work() {
            try {
                TimeUnit.MILLISECONDS.sleep(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "完成，耗时：" + random);
        }
    }

    /**
     * 控制器任务
     */
    private static class Controller implements Runnable {
        private CountDownLatch startLatch;
        private CountDownLatch endLatch;

        public Controller(CountDownLatch startLatch, CountDownLatch endLatch) {
            this.startLatch = startLatch;
            this.endLatch = endLatch;
        }

        @Override
        public void run() {
            System.out.println("controller:等待子模块执行：");
            startLatch.countDown();
            long start = System.currentTimeMillis();
            try {
                endLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("controller:所有子任务完成,");
            long end = System.currentTimeMillis();
            System.out.println("总共耗时：" + (end - start));
        }
    }
}
