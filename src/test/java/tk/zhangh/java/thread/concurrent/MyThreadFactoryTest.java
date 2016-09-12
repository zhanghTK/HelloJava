package tk.zhangh.java.thread.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadFactory Demo
 * Created by ZhangHao on 2016/9/12.
 */
public class MyThreadFactoryTest implements ThreadFactory {
    private static Logger logger = LoggerFactory.getLogger(MyThreadFactoryTest.class);
    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        int newIndex = index.incrementAndGet();
        Thread thread = new Thread(r);
        thread.setName("test_thread_no." + newIndex);
        logger.info("create new thread:{}", thread.getName());
        return thread;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5, new MyThreadFactoryTest());
        for (int i = 0; i < 5; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("start execute...");
                }
            });
        }
    }
}
