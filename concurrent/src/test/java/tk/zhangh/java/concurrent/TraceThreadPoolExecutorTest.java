package tk.zhangh.java.concurrent;

import lombok.AllArgsConstructor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 测试可打印详细错误的线程池
 * Created by ZhangHao on 2017/4/20.
 */
public class TraceThreadPoolExecutorTest {

    public static void main(String[] args) {
        TraceThreadPoolExecutor pools = new TraceThreadPoolExecutor(10, Integer.MAX_VALUE,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        for (int i = 0; i < 5; i++) {
            pools.submit(new DivTask(100, i));
        }
        pools.shutdown();
    }

    @AllArgsConstructor
    static class DivTask implements Runnable {
        int a, b;

        @Override
        public void run() {
            System.out.println(a / b);
        }
    }
}