package tk.zhangh.java.concurrent.pool;

import java.util.concurrent.*;

/**
 * 线程池拒绝 Demo
 * Created by ZhangHao on 2017/3/29.
 */
public class RejectThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        ExecutorService service =
                new ThreadPoolExecutor(5, 5, 0, TimeUnit.MICROSECONDS,
                        new SynchronousQueue<>(), Executors.defaultThreadFactory(),
                        (r, executor) -> System.out.println(r.toString() + "is discard"));
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            service.submit(task);
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    private static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
