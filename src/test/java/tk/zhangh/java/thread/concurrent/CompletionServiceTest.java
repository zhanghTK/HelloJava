package tk.zhangh.java.thread.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CompletionService例子
 * Created by ZhangHao on 2016/9/18.
 */
public class CompletionServiceTest {
    private static final int POOL_SIZE = 5;
    private static final int TASK_SIZE = 20;

    static class MyCallable implements Callable<String> {
        private String name;
        public MyCallable(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            int sleepTime = new Random().nextInt(1000);
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            String result = name;
            System.out.println(name + " finished...");
            return result + " sleep time:" + sleepTime;
        }
    }

    /**
     * 使用队列模拟CompletionService的效果
     * 缺点：不能保证优先获得已完任务的返回值，只是按加入线程池的顺序返回
     */
    public static void testByQueue() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<>();
        for (int i = 0; i < TASK_SIZE; i++) {
            queue.add(pool.submit(new MyCallable("Queue-Thread-" + i)));
        }
        for (int i = 0; i < TASK_SIZE; i++) {
            System.out.println(queue.take().get());
        }

        pool.shutdown();
    }

    /**
     * 使用CompletionService
     * 确保优先获取到已完成的任务的返回值
     * @throws Exception
     */
    public static void testByCompletion() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<String> completionService = new ExecutorCompletionService<>(pool);
        for (int i = 0; i < TASK_SIZE; i++) {
            completionService.submit(new MyCallable("Completion-Thread-" + i));
        }
        for (int i = 0; i < TASK_SIZE; i++) {
            Future<String> future = completionService.take();
            System.out.println(future.get());
        }
        pool.shutdown();
    }

    public static void main(String[] args) throws Exception {
        testByQueue();
        testByCompletion();
    }
}
