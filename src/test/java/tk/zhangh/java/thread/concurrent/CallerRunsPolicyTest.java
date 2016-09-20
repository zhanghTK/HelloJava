package tk.zhangh.java.thread.concurrent;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZhangHao on 2016/9/20.
 */
public class CallerRunsPolicyTest {
    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1);
        ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 1, 100,
                TimeUnit.SECONDS,
                workQueue);
        exec.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        System.out.println(new Date());
        for(int i=0;i<5;i++) {
            exec.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println("task run : " + Thread.currentThread().getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println(new Date());
        exec.shutdown();
    }

}
