package tk.zhangh.java.thread.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadFactory Demo
 * Created by ZhangHao on 2016/9/12.
 */
public class ThreadFactoryTest  {
    private static class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger index = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            int newIndex = index.incrementAndGet();
            Thread thread = new Thread(r);
            thread.setName("test_thread_no." + newIndex);
            return thread;
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5, new MyThreadFactory());
        for (int i = 0; i < 5; i++) {
            service.submit(new Thread(() -> System.out.println("start execute...")));
        }
    }
}
