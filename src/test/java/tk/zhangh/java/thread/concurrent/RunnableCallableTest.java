package tk.zhangh.java.thread.concurrent;

import java.util.concurrent.*;

/**
 * Runnable和Callable简单Demo
 * Runnable、Callable表示一个任务
 * Future表示一个任务的生命周期
 * RunnableFuture表示一个Runnable任务，以及future任务生命周期
 * FutureTask表示一个Runnable/Callable任务，以及future任务生命周期
 * Created by ZhangHao on 2016/9/18.
 */
public class RunnableCallableTest {
    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception{
        runnableDemo();
        runnableFutureDemo();
        callableFutureDemo();
        futureTaskCallableDemo();
        futureTaskRunnableDemo();
    }

    /**
     * 求斐波那契数
     */
    static int fibc(int num) {
        return num <= 1 ? num : fibc(num-1) + fibc(num-2);
    }

    /**
     * Runnable没有返回值，不能抛出异常
     */
    static void runnableDemo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable demo: " + fibc(20));
            }
        }).start();
    }

    /**
     * Future和Runnable组合使用，无法获得任务结果
     */
    static void runnableFutureDemo() throws Exception {
        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                fibc(20);
            }
        });
        System.out.println("future result from runnable:" + future.get());
    }

    /**
     * Future和Callable组合使用，可以获得任务结果
     */
    static void callableFutureDemo() throws Exception {
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });
        System.out.println("future future from callable:" + future.get());
    }

    /**
     * FutureTask和Callable组合使用，获得任务结果
     * 通常使用ExecuteService来执行，也可以通过Thread包装执行
     */
    static void futureTaskCallableDemo() throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });
        executor.submit(futureTask);
//        new Thread(futureTask).start();
        System.out.println("futureTask result from callable:" + futureTask.get());
    }

    /**
     * FutureTask和Runnable组合使用，不能获得任务结果
     * @throws Exception
     */
    static void futureTaskRunnableDemo() throws Exception {
        FutureTask<Integer> futureTask = new FutureTask(new Runnable() {
            @Override
            public void run() {
                fibc(20);
            }
        },Integer.class);
        executor.submit(futureTask);
        System.out.println("futureTask result from runnable:" + futureTask.get());
    }
}
