package tk.zhangh.java.concurrent.pool;

import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池扩展 Demo
 * Created by ZhangHao on 2017/3/29.
 */
public class ExThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = new ThreadPoolExecutor(5,
                6, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(t.getName());
                System.out.println("准备执行：" + ((Task) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成：" + ((Task) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        for (int i = 0; i < 5; i++) {
            Task task = new Task("TASK-" + i);
            service.execute(task);
            TimeUnit.MILLISECONDS.sleep(10);
        }
        service.shutdown();
    }

    @AllArgsConstructor
    private static class Task implements Runnable {
        private String name;

        @Override
        public void run() {
            System.out.println("正在执行：Thread ID:" + Thread.currentThread().getId() + ",Task Name=" + name);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
